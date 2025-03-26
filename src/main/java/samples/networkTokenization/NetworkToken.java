package samples.networkTokenization;

import Data.Configuration;
import Model.PostInstrumentIdentifierRequest;
import Model.TmsEmbeddedInstrumentIdentifier;
import com.cybersource.authsdk.core.MerchantConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import utilities.JWEResponse.JWEUtility;

public class NetworkToken {

    private static Properties merchantProp;

    public static void main(String args[]) throws Exception {
        run();
    }
    public static void run() {
        try {
            merchantProp = Configuration.getMerchantDetails();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);

            //Step-I
            PostInstrumentIdentifierRequest tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier = CreateInstrumentIdentifierCardEnrollForNetworkToken.run();

            //Step-II
            String encodedJWEResponse = PaymentCredentialsFromNetworkToken.run(tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier.getId());

            //Step-III
            // The following method JWEUtility.decryptJWEResponse(String, MerchantConfig) has been deprecated.
            // String decodedResponse = JWEUtility.decryptJWEResponse(encodedJWEResponse, merchantConfig);

            // Using the new method JWEUtility.decryptJWEResponse(PrivateKey, String) instead
            PrivateKey privateKey = fetchPrivateKeyFromFile(merchantConfig.getPemFileDirectory());

            if (privateKey != null) {
                String decodedResponse = JWEUtility.decryptJWEResponse(privateKey, encodedJWEResponse);
                System.out.println("Decoded Response");
                System.out.println(decodedResponse);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject responseJson = gson.fromJson(decodedResponse, JsonObject.class);
                System.out.println(gson.toJson(responseJson));
            } else {
                System.out.println("Private key not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes in the file path of a PEM file and extracts the private key from it.
     * The private key can be of the form PKCS#1 or PKCS#8.
     * @param pemFilepath The file path to the network tokenization private key PEM file
     * @return the private key from inside the PEM file
     */
    private static PrivateKey fetchPrivateKeyFromFile(String pemFilepath) {
        PemReader pemReader;
        PemObject pemObject = null;
        try {
            pemReader = new PemReader(new FileReader(pemFilepath));
            pemObject = pemReader.readPemObject();
        } catch (FileNotFoundException exception) {
            System.out.printf("No such file '%s' found.%n", pemFilepath);
        } catch (IOException exception) {
            System.out.printf("Cannot open file '%s'.%n", pemFilepath);
        }

        byte[] pkcs8Encoded;
        try { // This checks for a PKCS#1 Private Key
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, ASN1Sequence.getInstance(pemObject.getContent()));
            pkcs8Encoded = privateKeyInfo.getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Encoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (IOException exception) {
            System.out.println("IOException: Could not able to load the PEM Object from PEM file");
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("NoSuchAlgorithmException: Unable to find the required algorithm");
        } catch (InvalidKeySpecException exception) {
            System.out.println("InvalidKeySpecException: Unable to generate the PKCS#1 private key");
        }

        try { // This checks for a PKCS#8 Private Key
            pkcs8Encoded = pemObject.getContent();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Encoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("NoSuchAlgorithmException: Unable to find the required algorithm");
        } catch (InvalidKeySpecException exception) {
            System.out.println("InvalidKeySpecException: Unable to generate the PKCS#8 private key");
        }

        return null;
    }
}