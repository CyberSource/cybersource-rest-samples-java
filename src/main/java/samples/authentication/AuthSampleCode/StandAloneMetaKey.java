package samples.authentication.AuthSampleCode;

import Api.PaymentsApi;
import Invokers.ApiClient;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.util.Properties;

public class StandAloneMetaKey {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void main(String args[]) throws Exception {
        SimplePaymentsUsingMetaKey();
    }

    public static Properties getMerchantDetails() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "http_signature");
        props.setProperty("merchantID", "");
        props.setProperty("runEnvironment", "apitest.cybersource.com");

        // MetaKey Parameters
        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "true");
        // HTTP Parameters
        props.setProperty("merchantKeyId", "");
        props.setProperty("merchantsecretKey", "");

        return props;

    }

    public static PtsV2PaymentsPost201Response SimplePaymentsUsingMetaKey() {

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
            merchantProp = getMerchantDetails();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
