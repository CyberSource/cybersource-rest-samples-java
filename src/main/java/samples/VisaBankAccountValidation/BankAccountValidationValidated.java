package samples.VisaBankAccountValidation;

import Api.BankAccountValidationApi;
import Data.ConfigurationForBankAccountValidation;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

// The Bank Account Validation API requires encrypted requests as a mandatory requirement. The SDK by default sends encrypted requests for APIs where Request MLE (Message Level Encryption) is mandatory.
//MLE is supported only on the JWT authentication type
// For additional configuration options related to MLE, refer to the documentation at https://github.com/CyberSource/cybersource-rest-client-java/blob/master/MLE.md
// For MLE related sample codes look in the folder samples/MLEFeature
public class BankAccountValidationValidated {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;
    public static boolean userCapture = false;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        run();
    }

    public static InlineResponse20014 run() {

        AccountValidationsRequest accountValidationRequestObj = new AccountValidationsRequest();

        Bavsv1accountvalidationsClientReferenceInformation clientReferenceInformation = new Bavsv1accountvalidationsClientReferenceInformation();
        clientReferenceInformation.code("TC50171_100");
        accountValidationRequestObj.clientReferenceInformation(clientReferenceInformation);

        Bavsv1accountvalidationsProcessingInformation processingInformation = new Bavsv1accountvalidationsProcessingInformation();
        processingInformation.validationLevel(1);
        accountValidationRequestObj.processingInformation(processingInformation);

        Bavsv1accountvalidationsPaymentInformationBankAccount paymentInformationBankAccount = new Bavsv1accountvalidationsPaymentInformationBankAccount();
        paymentInformationBankAccount.number("99970");
        Bavsv1accountvalidationsPaymentInformationBank paymentInformationBank = new Bavsv1accountvalidationsPaymentInformationBank();
        paymentInformationBank.routingNumber("041210163");
        paymentInformationBank.account(paymentInformationBankAccount);
        Bavsv1accountvalidationsPaymentInformation paymentInformation = new Bavsv1accountvalidationsPaymentInformation();
        paymentInformation.bank(paymentInformationBank);
        accountValidationRequestObj.paymentInformation(paymentInformation);

        InlineResponse20014 result = null;
        try {
            merchantProp = ConfigurationForBankAccountValidation.getMerchantDetailsForBankAccountValidation();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;
            BankAccountValidationApi apiInstance = new BankAccountValidationApi(apiClient);
            result = apiInstance.bankAccountValidationRequest(accountValidationRequestObj);

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
