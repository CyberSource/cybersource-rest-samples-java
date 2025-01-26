package samples.MerchantBoarding;

import Api.MerchantBoardingApi;
import Data.MerchantBoardingConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class MerchantBoardingFDIGlobal {


    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        // Accept required parameters from args[] and pass to run.
        run();

    }


    public static InlineResponse2012 run() {

        PostRegistrationBody reqObj=new PostRegistrationBody();

        Boardingv1registrationsOrganizationInformation organizationInformation=new Boardingv1registrationsOrganizationInformation();
        organizationInformation.parentOrganizationId("apitester00");
        organizationInformation.type("MERCHANT");
        organizationInformation.configurable(true);

        Boardingv1registrationsOrganizationInformationBusinessInformation businessInformation=new Boardingv1registrationsOrganizationInformationBusinessInformation();
        businessInformation.name("StuartWickedFastEatz");
        Boardingv1registrationsOrganizationInformationBusinessInformationAddress address=new Boardingv1registrationsOrganizationInformationBusinessInformationAddress();
        address.country("US");
        address.address1("123456 SandMarket");
        address.locality("ORMOND BEACH");
        address.administrativeArea("FL");
        address.postalCode("32176");
        businessInformation.address(address);
        businessInformation.websiteUrl("https://www.StuartWickedEats.com");
        businessInformation.phoneNumber("6574567813");

        Boardingv1registrationsOrganizationInformationBusinessInformationBusinessContact businessContact=new Boardingv1registrationsOrganizationInformationBusinessInformationBusinessContact();
        businessContact.firstName("Stuart");
        businessContact.lastName("Stuart");
        businessContact.phoneNumber("6574567813");
        businessContact.email("svc_email_bt@corpdev.visa.com");
        businessInformation.businessContact(businessContact);
        businessInformation.merchantCategoryCode("5999");
        organizationInformation.businessInformation(businessInformation);

        reqObj.organizationInformation(organizationInformation);

        Boardingv1registrationsProductInformation productInformation=new Boardingv1registrationsProductInformation();
        Boardingv1registrationsProductInformationSelectedProducts selectedProducts=new Boardingv1registrationsProductInformationSelectedProducts();

        PaymentsProducts payments=new PaymentsProducts();
        PaymentsProductsCardProcessing cardProcessing=new PaymentsProductsCardProcessing();
        PaymentsProductsCardProcessingSubscriptionInformation subscriptionInformation=new PaymentsProductsCardProcessingSubscriptionInformation();

        subscriptionInformation.enabled(true);
        Map<String, PaymentsProductsCardProcessingSubscriptionInformationFeatures> features=new HashMap<>();

        PaymentsProductsCardProcessingSubscriptionInformationFeatures obj1=new PaymentsProductsCardProcessingSubscriptionInformationFeatures();
        obj1.enabled(true);
        features.put("cardNotPresent",obj1);
        features.put("cardPresent",obj1);
        subscriptionInformation.features(features);
        cardProcessing.subscriptionInformation(subscriptionInformation);

        PaymentsProductsCardProcessingConfigurationInformation configurationInformation=new PaymentsProductsCardProcessingConfigurationInformation();

        CardProcessingConfig configurations=new CardProcessingConfig();
        CardProcessingConfigCommon common=new CardProcessingConfigCommon();
        common.merchantCategoryCode("0742");
        common.defaultAuthTypeCode("PRE");
        common.processLevel3Data("ignored");
        common.masterCardAssignedId("123456789");
        common.enablePartialAuth(true);

        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj5=new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer=new CardProcessingConfigCommonAcquirer();

        obj5.acquirer(acquirer);

        Map<String, CardProcessingConfigCommonCurrencies1> currencies=new HashMap<>();


        CardProcessingConfigCommonCurrencies1 obj6=new CardProcessingConfigCommonCurrencies1();
        obj6.enabled(true);
        obj6.enabledCardPresent(false);
        obj6.enabledCardNotPresent(true);
        obj6.merchantId("123456789mer");
        obj6.terminalId("12345ter");
        obj6.serviceEnablementNumber("");
        currencies.put("CHF",obj6);
        currencies.put("HRK",obj6);
        currencies.put("ERN",obj6);
        currencies.put("USD",obj6);

        obj5.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes=new HashMap<>();
        CardProcessingConfigCommonPaymentTypes obj7=new CardProcessingConfigCommonPaymentTypes();
        obj7.enabled(true);
        paymentTypes.put("MASTERCARD",obj7);
        paymentTypes.put("DISCOVER",obj7);
        paymentTypes.put("JCB",obj7);
        paymentTypes.put("VISA",obj7);
        paymentTypes.put("AMERICAN_EXPRESS",obj7);
        paymentTypes.put("DINERS_CLUB",obj7);
        paymentTypes.put("CUP",obj7);
        Map<String, CardProcessingConfigCommonCurrencies> currencies2=new HashMap<>();
        CardProcessingConfigCommonCurrencies ob1=new CardProcessingConfigCommonCurrencies();
        ob1.enabled(true);
        ob1.terminalId("pint123");
        ob1.merchantId("pinm123");
        ob1.serviceEnablementNumber(null);

        currencies2.put("USD",ob1);
        obj7.currencies(currencies2);
        paymentTypes.put("PIN_DEBIT",obj7);

        obj5.paymentTypes(paymentTypes);
        obj5.batchGroup("fdiglobal_vme_default");
        obj5.enhancedData("disabled");
        obj5.enablePosNetworkSwitching(true);
        obj5.enableTransactionReferenceNumber(true);

        processors.put("fdiglobal",obj5);

        common.processors(processors);
        configurations.common(common);

        CardProcessingConfigFeatures features2=new CardProcessingConfigFeatures();

        CardProcessingConfigFeaturesCardNotPresent cardNotPresent=new CardProcessingConfigFeaturesCardNotPresent();

        Map<String, CardProcessingConfigFeaturesCardNotPresentProcessors> processors3=new HashMap<>();
        CardProcessingConfigFeaturesCardNotPresentProcessors obj9=new CardProcessingConfigFeaturesCardNotPresentProcessors();

        obj9.relaxAddressVerificationSystem(true);
        obj9.relaxAddressVerificationSystemAllowExpiredCard(true);
        obj9.relaxAddressVerificationSystemAllowZipWithoutCountry(true);

        processors3.put("fdiglobal",obj9);
        cardNotPresent.processors(processors3);

        cardNotPresent.visaStraightThroughProcessingOnly(true);
        cardNotPresent.amexTransactionAdviceAddendum1("amex12345");
        cardNotPresent.ignoreAddressVerificationSystem(true);
        features2.cardNotPresent(cardNotPresent);

        configurations.features(features2);
        configurationInformation.configurations(configurations);
        UUID templateId = UUID.fromString("685A1FC9-3CEC-454C-9D8A-19205529CE45");
        configurationInformation.templateId(templateId);

        cardProcessing.configurationInformation(configurationInformation);
        payments.cardProcessing(cardProcessing);
        selectedProducts.payments(payments);

        productInformation.selectedProducts(selectedProducts);
        reqObj.productInformation(productInformation);


        InlineResponse2012 result=null;

        try {
            //Boarding API support only JWT Auth Type
            merchantProp = MerchantBoardingConfiguration.getMerchantConfigForBoardingAPI();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            MerchantBoardingApi apiInstance = new MerchantBoardingApi(apiClient);
            result = apiInstance.postRegistration(reqObj, null);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
            System.out.println("ResponseCode :" + responseCode);
            System.out.println("ResponseMessage :" + status);
            System.out.println(result);
            WriteLogAudit(Integer.parseInt(responseCode));
        } catch (ApiException e) {
            e.printStackTrace();
            WriteLogAudit(e.getCode());
            System.out.println("Msg.."+e.getMessage()+"  Respbody.."+e.getResponseBody()+"  cause.."+e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
        }



        return result;
    }
}
