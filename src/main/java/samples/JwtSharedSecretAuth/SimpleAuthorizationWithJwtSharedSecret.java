package samples.JwtSharedSecretAuth;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentsApi;
import Data.JwtSharedSecretConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

/**
 * Simple Authorization using JWT authentication with Shared Secret (HS256).
 *
 * <p>This sample demonstrates a drop-in replacement for HTTP Signature authentication.
 * It uses the <b>same</b> {@code merchantKeyId} and {@code merchantsecretKey} credentials
 * you already use for HTTP Signature, but authenticates via JWT instead.</p>
 *
 * <h2>Migration from HTTP Signature</h2>
 * <p>HTTP Signature authentication is being deprecated. To migrate:</p>
 * <ol>
 *   <li>Change {@code authenticationType} from {@code http_signature} to {@code jwt}</li>
 *   <li>Add {@code jwtKeyType = SHARED_SECRET}</li>
 *   <li>Keep your existing {@code merchantKeyId} and {@code merchantsecretKey} as-is</li>
 * </ol>
 *
 * <p>See {@link Data.JwtSharedSecretConfiguration#getMerchantDetails()} for the
 * full configuration.</p>
 */
public class SimpleAuthorizationWithJwtSharedSecret {
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

    public static PtsV2PaymentsPost201Response run() {

        CreatePaymentRequest requestObj = new CreatePaymentRequest();

        Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
        clientReferenceInformation.code("TC50171_3");
        requestObj.clientReferenceInformation(clientReferenceInformation);

        Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
        processingInformation.capture(false);
        requestObj.processingInformation(processingInformation);

        Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
        Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
        paymentInformationCard.number("4111111111111111");
        paymentInformationCard.expirationMonth("12");
        paymentInformationCard.expirationYear("2031");
        paymentInformation.card(paymentInformationCard);
        requestObj.paymentInformation(paymentInformation);

        Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
        Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
        orderInformationAmountDetails.totalAmount("102.21");
        orderInformationAmountDetails.currency("USD");
        orderInformation.amountDetails(orderInformationAmountDetails);

        Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
        orderInformationBillTo.firstName("John");
        orderInformationBillTo.lastName("Doe");
        orderInformationBillTo.address1("1 Market St");
        orderInformationBillTo.locality("san francisco");
        orderInformationBillTo.administrativeArea("CA");
        orderInformationBillTo.postalCode("94105");
        orderInformationBillTo.country("US");
        orderInformationBillTo.email("test@cybs.com");
        orderInformationBillTo.phoneNumber("4158880000");
        orderInformation.billTo(orderInformationBillTo);
        requestObj.orderInformation(orderInformation);

        PtsV2PaymentsPost201Response result = null;
        try {
            /* Load JWT + Shared Secret configuration */
            merchantProp = JwtSharedSecretConfiguration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            PaymentsApi apiInstance = new PaymentsApi(apiClient);
            result = apiInstance.createPayment(requestObj);

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
            WriteLogAudit(500);
        }

        return result;
    }
}
