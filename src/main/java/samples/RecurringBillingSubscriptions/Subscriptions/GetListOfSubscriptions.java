package samples.RecurringBillingSubscriptions.Subscriptions;

import Api.SubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2006;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class GetListOfSubscriptions {
    private static String responseCode = null;
    private static String responseStatus = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        run();
    }

    public static InlineResponse2006 run() {
        int offset = 0;
        int limit = 100;
        String code = null;
        String status = null;

        InlineResponse2006 response = null;
        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            SubscriptionsApi apiInstance = new SubscriptionsApi(apiClient);
            response = apiInstance.getAllSubscriptions(offset, limit, code, status);

            responseCode = apiClient.responseCode;
            responseStatus = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + responseStatus);
            WriteLogAudit(Integer.parseInt(responseCode));
        } catch (ApiException e) {
            e.printStackTrace();
            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
