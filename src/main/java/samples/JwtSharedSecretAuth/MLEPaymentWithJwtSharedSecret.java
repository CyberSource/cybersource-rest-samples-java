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
 * Simple Authorization using JWT with Shared Secret + MLE (Message Level Encryption).
 *
 * <p>This sample demonstrates the primary benefit of migrating from HTTP Signature
 * to JWT with Shared Secret: <b>MLE support</b>. MLE encrypts the request payload
 * at the application level before it is sent over the network, providing an
 * additional layer of security beyond TLS.</p>
 *
 * <h2>Key Difference from HTTP Signature</h2>
 * <p>HTTP Signature does not support MLE. By switching to JWT with Shared Secret,
 * you gain MLE capability using the <b>same credentials</b> you already have.</p>
 *
 * <h2>MLE Certificate</h2>
 * <p>When using {@code jwtKeyType=SHARED_SECRET}, the MLE public certificate must
 * be provided via the {@code mleForRequestPublicCertPath} property. Download it from
 * the CyberSource Business Center:</p>
 * <ul>
 *   <li>Test: <a href="https://businesscentertest.cybersource.com/ebc2">businesscentertest.cybersource.com/ebc2</a></li>
 *   <li>Production: <a href="https://businesscenter.cybersource.com/ebc2">businesscenter.cybersource.com/ebc2</a></li>
 * </ul>
 *
 * <p>See {@link Data.JwtSharedSecretConfiguration#getMerchantDetailsWithMLE()} for the
 * full configuration.</p>
 */
public class MLEPaymentWithJwtSharedSecret {
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
            /* Load JWT + Shared Secret + MLE configuration */
            merchantProp = JwtSharedSecretConfiguration.getMerchantDetailsWithMLE();
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
