package samples.FlexMicroform;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;
import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;
import com.google.gson.JsonObject;
import utilities.capturecontext.utility.CaptureContextParsingUtility;

public class GenerateCaptureContextAcceptCard {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void main(String args[]) throws Exception {
        run();
    }

    public static void run() {

        GenerateCaptureContextRequest requestObj = new GenerateCaptureContextRequest();

        requestObj.clientVersion("v2");

        List <String> targetOrigins = new ArrayList <String>();
        targetOrigins.add("https://www.test.com");
        requestObj.targetOrigins(targetOrigins);

        List <String> allowedCardNetworks = new ArrayList <String>();
        allowedCardNetworks.add("VISA");
        allowedCardNetworks.add("MASTERCARD");
        allowedCardNetworks.add("AMEX");
        allowedCardNetworks.add("CARNET");
        allowedCardNetworks.add("CARTESBANCAIRES");
        allowedCardNetworks.add("CUP");
        allowedCardNetworks.add("DINERSCLUB");
        allowedCardNetworks.add("DISCOVER");
        allowedCardNetworks.add("EFTPOS");
        allowedCardNetworks.add("ELO");
        allowedCardNetworks.add("JCB");
        allowedCardNetworks.add("JCREW");
        allowedCardNetworks.add("MADA");
        allowedCardNetworks.add("MAESTRO");
        allowedCardNetworks.add("MEEZA");
        requestObj.allowedCardNetworks(allowedCardNetworks);

        List<String> allowedPaymentTypes = new ArrayList<String>();
        allowedPaymentTypes.add("CARD");
        requestObj.allowedPaymentTypes(allowedPaymentTypes);

        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            MicroformIntegrationApi apiInstance = new MicroformIntegrationApi(apiClient);
            String response = apiInstance.generateCaptureContext(requestObj);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + status);
            System.out.println("Response Body :" + response);
            System.out.println("\n\n");

            try {
                JsonObject payload = CaptureContextParsingUtility.parseCaptureContextResponse(response, merchantConfig, true);
                System.out.println(payload.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}