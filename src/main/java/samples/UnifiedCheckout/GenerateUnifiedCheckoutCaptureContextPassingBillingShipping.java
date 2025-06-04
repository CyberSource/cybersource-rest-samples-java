package samples.UnifiedCheckout;

import Api.UnifiedCheckoutCaptureContextApi;
import Data.Configuration;
import Invokers.ApiClient;
import Model.GenerateUnifiedCheckoutCaptureContextRequest;
import Model.Upv1capturecontextsCaptureMandate;
import Model.Upv1capturecontextsOrderInformation;
import Model.Upv1capturecontextsOrderInformationAmountDetails;
import com.cybersource.authsdk.core.MerchantConfig;
import Model.Upv1capturecontextsOrderInformationBillTo;
import Model.Upv1capturecontextsOrderInformationBillToCompany;
import Model.Upv1capturecontextsOrderInformationShipTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GenerateUnifiedCheckoutCaptureContextPassingBillingShipping {
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
        allowedPaymentTypes.add("APPLEPAY");
        allowedPaymentTypes.add("CHECK");
        allowedPaymentTypes.add("CLICKTOPAY");
        allowedPaymentTypes.add("GOOGLEPAY");
        allowedPaymentTypes.add("PANENTRY");
        allowedPaymentTypes.add("PAZE");
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

        Upv1capturecontextsOrderInformationBillTo orderInformationBillTo = new Upv1capturecontextsOrderInformationBillTo();
        orderInformationBillTo.address1("277 Park Avenue");
        orderInformationBillTo.address2("50th Floor");
        orderInformationBillTo.address3("Desk NY-50110");
        orderInformationBillTo.address4("address4");
        orderInformationBillTo.administrativeArea("NY");
        orderInformationBillTo.buildingNumber("buildingNumber");
        orderInformationBillTo.country("US");
        orderInformationBillTo.district("district");
        orderInformationBillTo.locality("New York");
        orderInformationBillTo.postalCode("10172");
        Upv1capturecontextsOrderInformationBillToCompany orderInformationBillToCompany = new Upv1capturecontextsOrderInformationBillToCompany();
        orderInformationBillToCompany.name("Visa Inc");
        orderInformationBillToCompany.address1("900 Metro Center Blvd");
        orderInformationBillToCompany.address2("address2");
        orderInformationBillToCompany.address3("address3");
        orderInformationBillToCompany.address4("address4");
        orderInformationBillToCompany.administrativeArea("CA");
        orderInformationBillToCompany.buildingNumber("1");
        orderInformationBillToCompany.country("US");
        orderInformationBillToCompany.district("district");
        orderInformationBillToCompany.locality("Foster City");
        orderInformationBillToCompany.postalCode("94404");
        orderInformationBillTo.company(orderInformationBillToCompany);

        orderInformationBillTo.email("john.doe@visa.com");
        orderInformationBillTo.firstName("John");
        orderInformationBillTo.lastName("Doe");
        orderInformationBillTo.middleName("F");
        orderInformationBillTo.nameSuffix("Jr");
        orderInformationBillTo.title("Mr");
        orderInformationBillTo.phoneNumber("1234567890");
        orderInformationBillTo.phoneType("phoneType");
        orderInformation.billTo(orderInformationBillTo);

        Upv1capturecontextsOrderInformationShipTo orderInformationShipTo = new Upv1capturecontextsOrderInformationShipTo();
        orderInformationShipTo.address1("CyberSource");
        orderInformationShipTo.address2("Victoria House");
        orderInformationShipTo.address3("15-17 Gloucester Street");
        orderInformationShipTo.address4("string");
        orderInformationShipTo.administrativeArea("CA");
        orderInformationShipTo.buildingNumber("string");
        orderInformationShipTo.country("GB");
        orderInformationShipTo.district("string");
        orderInformationShipTo.locality("Belfast");
        orderInformationShipTo.postalCode("BT1 4LS");
        orderInformationShipTo.firstName("Joe");
        orderInformationShipTo.lastName("Soap");
        orderInformation.shipTo(orderInformationShipTo);

        requestObj.orderInformation(orderInformation);

        Upv1capturecontextsCompleteMandate completeMandate = new Upv1capturecontextsCompleteMandate();
        completeMandate.setType("CAPTURE");
        completeMandate.setDecisionManager(false);
        requestObj.setCompleteMandate(completeMandate);

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
