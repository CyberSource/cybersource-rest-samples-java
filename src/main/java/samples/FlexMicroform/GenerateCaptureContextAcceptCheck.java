package samples.FlexMicroform;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;
import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;
import com.google.gson.JsonObject;
import utilities.capturecontext.utility.CaptureContextParsingUtility;

public class GenerateCaptureContextAcceptCheck {
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

        List<String> allowedPaymentTypes = new ArrayList<String>();
        allowedPaymentTypes.add("CHECK");
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
