package samples.Invoicing.Invoices;

import Api.InvoicesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InvoicingV2InvoicesAllGet200Response;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class GetListOfInvoices {
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

    public static InvoicingV2InvoicesAllGet200Response run() {
        int offset = 0;
        int limit = 10;
        String status = null;

        InvoicingV2InvoicesAllGet200Response result = null;

        try
        {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            InvoicesApi apiInstance = new InvoicesApi(apiClient);
            result = apiInstance.getAllInvoices(offset, limit, status);

            responseCode = apiClient.responseCode;
            responseStatus = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + responseStatus);
            System.out.println(result);
            WriteLogAudit(Integer.parseInt(responseCode));
        } catch (ApiException e) {
            e.printStackTrace();
            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
