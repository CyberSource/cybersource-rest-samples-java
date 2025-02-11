package samples.UnifiedCheckout;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;
import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;

public class GenerateCaptureContextForClickToPayDropInUI {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void main(String args[]) throws Exception {
        run();
    }

    public static void run() {

        GenerateUnifiedCheckoutCaptureContextRequest requestObj = new GenerateUnifiedCheckoutCaptureContextRequest();

        requestObj.clientVersion("0.23");

        List <String> targetOrigins = new ArrayList <String>();
        targetOrigins.add("https://yourCheckoutPage.com");
        requestObj.targetOrigins(targetOrigins);


        List <String> allowedCardNetworks = new ArrayList <String>();
        allowedCardNetworks.add("VISA");
        allowedCardNetworks.add("MASTERCARD");
        allowedCardNetworks.add("AMEX");
        allowedCardNetworks.add("CARNET");
        allowedCardNetworks.add("CARTESBANCAIRES");
        allowedCardNetworks.add("CUP");
        allowedCardNetworks.add("DINERSCLUB");
        allowedCardNetworks.add("DISCOVER");
        allowedCardNetworks.add("EFTPOS");
        allowedCardNetworks.add("ELO");
        allowedCardNetworks.add("JCB");
        allowedCardNetworks.add("JCREW");
        allowedCardNetworks.add("MADA");
        allowedCardNetworks.add("MAESTRO");
        allowedCardNetworks.add("MEEZA");
        requestObj.allowedCardNetworks(allowedCardNetworks);


        List <String> allowedPaymentTypes = new ArrayList <String>();
        allowedPaymentTypes.add("CLICKTOPAY");
        requestObj.allowedPaymentTypes(allowedPaymentTypes);

        requestObj.country("US");
        requestObj.locale("en_US");
        Upv1capturecontextsCaptureMandate captureMandate = new Upv1capturecontextsCaptureMandate();
        captureMandate.billingType("FULL");
        captureMandate.requestEmail(true);
        captureMandate.requestPhone(true);
        captureMandate.requestShipping(true);

        List <String> shipToCountries = new ArrayList <String>();
        shipToCountries.add("US");
        shipToCountries.add("GB");
        captureMandate.shipToCountries(shipToCountries);

        captureMandate.showAcceptedNetworkIcons(true);
        requestObj.captureMandate(captureMandate);

        Upv1capturecontextsOrderInformation orderInformation = new Upv1capturecontextsOrderInformation();
        Upv1capturecontextsOrderInformationAmountDetails orderInformationAmountDetails = new Upv1capturecontextsOrderInformationAmountDetails();
        orderInformationAmountDetails.totalAmount("21.00");
        orderInformationAmountDetails.currency("USD");
        orderInformation.amountDetails(orderInformationAmountDetails);

        requestObj.orderInformation(orderInformation);

        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            UnifiedCheckoutCaptureContextApi apiInstance = new UnifiedCheckoutCaptureContextApi(apiClient);
            String response = apiInstance.generateUnifiedCheckoutCaptureContext(requestObj);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + status);
            System.out.println("Response Body :" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
