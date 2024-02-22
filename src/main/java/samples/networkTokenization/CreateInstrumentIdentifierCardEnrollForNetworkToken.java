package samples.networkTokenization;

import Api.InstrumentIdentifierApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PostInstrumentIdentifierRequest;
import Model.TmsEmbeddedInstrumentIdentifier;
import Model.TmsEmbeddedInstrumentIdentifierCard;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class CreateInstrumentIdentifierCardEnrollForNetworkToken {
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

    public static TmsEmbeddedInstrumentIdentifier run() {
        String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
        PostInstrumentIdentifierRequest requestObj = new PostInstrumentIdentifierRequest();

        requestObj.type("enrollable card");
        TmsEmbeddedInstrumentIdentifierCard card = new TmsEmbeddedInstrumentIdentifierCard();
        card.number("5204245750003216");
        card.expirationMonth("12");
        card.expirationYear("2025");
        requestObj.card(card);

        TmsEmbeddedInstrumentIdentifier result = null;
        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
            result = apiInstance.postInstrumentIdentifier(requestObj, profileid);

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
