package samples.Invoicing.Invoices;

import Api.InvoicesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InvoicingV2InvoicesPost201Response;
import com.cybersource.authsdk.core.ConfigException;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class CancelInvoice {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        run();
    }

    public static InvoicingV2InvoicesPost201Response run() {
        String invoiceId = CreateDraftInvoice.run().getId();

        InvoicingV2InvoicesPost201Response result = null;

        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            InvoicesApi apiInstance = new InvoicesApi(apiClient);
            result = apiInstance.performCancelAction(invoiceId);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + status);
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
