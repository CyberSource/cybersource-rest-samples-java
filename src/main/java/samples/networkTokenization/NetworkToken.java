package samples.networkTokenization;

import Data.Configuration;
import Model.Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier;
import com.cybersource.authsdk.core.MerchantConfig;

import java.util.Properties;
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
            Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier = CreateInstrumentIdentifierCardEnrollForNetworkToken.run();

            //Step-II
            String encodedJWEResponse = PaymentCredentialsFromNetworkToken.run(tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifier.getId());

            //Step-III
            String decodedResponse = JWEUtility.decryptJWEResponse(encodedJWEResponse, merchantConfig);
            System.out.println("Decoded Response");
            System.out.println(decodedResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
