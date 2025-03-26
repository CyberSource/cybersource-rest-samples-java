package samples.AccountUpdater;
import com.nimbusds.jose.JWSSigner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import org.apache.commons.lang3.RandomStringUtils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class StandaloneAuraCardCsvUploadApp {

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }
    /* <summary>
     * This Java application demonstrates how to upload a file using a multipart/form-data request with JWT authentication for the card upload CSV endpoint in AURA.
     * CyberSource Business Center - https://ebc2test.cybersource.com/ebc2/
     * Instructions on generating your own .P12 from CyberSource Business Center - https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/authentication/createCertSharedKey.html
     * Also, To understand details about CyberSource JWT headers, please check Authentication guide - https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/authentication/GenerateHeader/jwtTokenAuthentication.html
     * This sample standalone demonstrates a full end to end working of uploading a file with a multipart/form-data request and building a JWT token for API authentication.
     * </summary>
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length < 4) {
            System.out.println("Usage: <merchantId> <csv_file_path> <P12_FILE_PATH> <P12_PASSWORD> <URL>");
            return;
        }
        String merchantId = args[0];
        String csv_file_path = args[1];
        String P12_FILE_PATH = args[2];
        String P12_PASSWORD = args[3];
        String URL = args[4] + "/accountupdater/v1/batches/upload-csv-card-numbers";
        run(merchantId, csv_file_path,P12_FILE_PATH,P12_PASSWORD,URL);
    }

    public static void run(String merchantId, String file_path, String P12_FILE_PATH, String P12_PASSWORD, String URL) throws IOException, NoSuchAlgorithmException {
        String fileName = Paths.get(file_path).getFileName().toString();
        String randomNumeric = RandomStringUtils.randomNumeric(24);
        String fullFile = "--" + randomNumeric + "\n";
        fullFile += "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\n"
                + "Content-Type: text/csv" + "\n" + "\n";
        String token = null;
        try (Scanner scanner = new Scanner(new File(file_path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fullFile += line + "\n";
            }
            fullFile += "--" + randomNumeric + "--";

            FileInputStream fis = new FileInputStream(P12_FILE_PATH);
            KeyStore keystore = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
            keystore.load(fis, P12_PASSWORD.toCharArray());
            String alias = null;
            Enumeration<String> enumKeyStore = keystore.aliases();
            ArrayList<String> array = new ArrayList<String>();

            while (enumKeyStore.hasMoreElements()) {
                alias = enumKeyStore.nextElement();
                array.add(alias);
                if (alias.contains(merchantId)) {
                    break;
                }
            }


            KeyStore.PrivateKeyEntry privateKeyTest;
            // Get the certificate
            alias = keyAliasValidator(array, merchantId);
            privateKeyTest = (KeyStore.PrivateKeyEntry) keystore.getEntry(alias,
                    new KeyStore.PasswordProtection(merchantId.toCharArray()));
            Certificate certificate = keystore.getCertificate(alias);

            // Cast to X509Certificate to access more details
            X509Certificate x509Certificate = (X509Certificate) certificate;

            // Extract SERIALNUMBER and CN
            String subjectDN = x509Certificate.getSubjectDN().getName();
            String serialNumber = "";
            String cn = "";

            String[] dnComponents = subjectDN.split(",");
            for (String component : dnComponents) {
                if (component.startsWith("SERIALNUMBER=")) {
                    serialNumber = component.substring("SERIALNUMBER=".length());
                } else if (component.startsWith("CN=")) {
                    cn = component.substring("CN=".length());
                }
            }
            
            fis.close();

            JWSSigner signer = new RSASSASigner(privateKeyTest.getPrivateKey());
            String digest = generateDigest(fullFile);
            try {
                JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .customParam("v-c-merchant-id", cn)
                        .keyID(serialNumber)
                        .build();
                Instant now = Instant.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .withZone(ZoneOffset.UTC);
                String iat = formatter.format(now);
                // Prepare JWT with claims set
                JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                        .claim("digest", digest)
                        .claim("digestAlgorithm", "SHA-256")
                        .claim("iat", iat)
                        .build();

                SignedJWT signedJWT = new SignedJWT(
                        header,
                        claimsSet);
                signedJWT.sign(signer);

                token = signedJWT.serialize();

                System.out.println("Generated JWT Token: " + token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        OkHttpClient client = new OkHttpClient();
        String requestBody = fullFile;

        RequestBody body = RequestBody.create(
                requestBody, MediaType.get("multipart/form-data; charset=utf-8; boundary=" + randomNumeric));

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "multipart/form-data; charset=utf-8; boundary=" + randomNumeric)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("v-c-correlation-id", "")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("POST request worked");
            System.out.println("Response Body :: " + response.body().string());
        } else {
            System.out.println("POST request did not work " + response.body().string());
        }
        WriteLogAudit(response.code());

    }

    /*
     <summary>
     * This method generates a SHA-256 hash of the given payload and returns the Base64-encoded string representation of the hash.
     * It uses the MessageDigest class to compute the hash and the Base64 class to encode the hash.
     * This takes the converted csv file payload as the input and returns the base64 encoded hash to use as the digest
     * </summary>
     */
    public static String generateDigest(String payload) throws NoSuchAlgorithmException {
        // Create a MessageDigest instance for SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Calculate the hash
        byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));

        // Encode the hash using Base64
        return Base64.getEncoder().encodeToString(hash);
    }
    private static String keyAliasValidator(ArrayList<String> array, String merchantID) {
        int size = array.size();
        String tempKeyAlias, merchantKeyAlias, result;
        StringTokenizer str;
        for (int i = 0; i < size; i++) {
            merchantKeyAlias = array.get(i).toString();
            str = new StringTokenizer(merchantKeyAlias, ",");
            while (str.hasMoreTokens()) {
                tempKeyAlias = str.nextToken();
                if (tempKeyAlias.contains("CN")) {
                    str = new StringTokenizer(tempKeyAlias, "=");
                    while (str.hasMoreElements()) {
                        result = str.nextToken();
                        if (result.equalsIgnoreCase(merchantID)) {
                            return merchantKeyAlias;
                        }
                    }
                }
            }
        }

        return null;
    }
}


