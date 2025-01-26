package samples.IntermediateHost.Payments.Payments;
import Api.InstrumentIdentifierApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.PostInstrumentIdentifierRequest;
import Model.TmsEmbeddedInstrumentIdentifierBankAccount;
import Model.TmsEmbeddedInstrumentIdentifierCard;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.Random;

public class MultiThread {
    private static String responseCode = null;
    private static String status = null;
    private static MerchantConfig merchantConfig;
    private static ApiClient apiClient;
    static int counter =0;





    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String[] args) throws Exception{
        Properties merchantProp = Configuration.getMerchantDetails();
        merchantConfig = new MerchantConfig(merchantProp);
        apiClient = new ApiClient();
        apiClient.merchantConfig = merchantConfig;

        int numberOfRuns =1;

        for (int i = 0; i < numberOfRuns; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String result1 = runMethod1();
                    System.out.println("Result from Method 1: " + Integer.toString(++counter) +" " +result1);
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String result2 = runMethod2();
                    System.out.println("Result from Method 2: "+ Integer.toString(++counter) +" " +result2);
                }
            });

            thread1.start();
            thread2.start();


        }
    }

    public static String runMethod1() {
        String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
        PostInstrumentIdentifierRequest requestObj = new PostInstrumentIdentifierRequest();

        TmsEmbeddedInstrumentIdentifierBankAccount bankAccount = new TmsEmbeddedInstrumentIdentifierBankAccount();
        bankAccount.number("4100");
        String rnum= "07192328" + String.valueOf(new Random().nextInt(10));
        bankAccount.routingNumber(rnum);
        requestObj.bankAccount(bankAccount);

        PostInstrumentIdentifierRequest result = null;
        try {
//            ApiClient apiClient = new ApiClient();
//            apiClient.merchantConfig = merchantConfig;

            InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
            result = apiInstance.postInstrumentIdentifier(requestObj, profileid,false);

            String responseCode = apiClient.responseCode;
            boolean status = rnum.equals(result.getBankAccount().getRoutingNumber());
//            System.out.println("ResponseCode :" + responseCode);
//            System.out.println("ResponseMessage :" + status);
//            System.out.println(result);
//            WriteLogAudit(Integer.parseInt(responseCode));
            return String.valueOf(status);

        } catch (ApiException e) {
            e.printStackTrace();
//            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String runMethod2(){
        String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
        PostInstrumentIdentifierRequest requestObj = new PostInstrumentIdentifierRequest();

        TmsEmbeddedInstrumentIdentifierCard card = new TmsEmbeddedInstrumentIdentifierCard();
        String rnum= "411111111111" + String.valueOf(1000 + new Random().nextInt(9000));
        card.number(rnum);
        requestObj.card(card);

        ApiResponse<PostInstrumentIdentifierRequest> result = null;
        try {
//            ApiClient apiClient = new ApiClient();
//            apiClient.merchantConfig = merchantConfig;

            InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
            result = apiInstance.postInstrumentIdentifierWithHttpInfo(requestObj, profileid,false);
            
            PostInstrumentIdentifierRequest result1 = result.getData();
            
            
            String lastFourDigitsStr1 = rnum.substring(rnum.length() - 4);
            String lastFourDigitsStr2 = result1.getCard().getNumber().substring(result1.getCard().getNumber().length() - 4);

            String responseCode = apiClient.responseCode;
            boolean status = lastFourDigitsStr1.equals(lastFourDigitsStr2);
            
            //oauth - not thread safe in case same instance of merchantConfig and api client
            //apiClient.responseCode , apiClient.status
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + status);
//            WriteLogAudit(Integer.parseInt(responseCode));
            return String.valueOf(status);
        } catch (ApiException e) {
            e.printStackTrace();
//            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
