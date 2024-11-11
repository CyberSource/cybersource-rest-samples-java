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

public class MerchantBoardingEFTPOS {


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
        organizationInformation.type(Boardingv1registrationsOrganizationInformation.TypeEnum.MERCHANT);
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
        obj1.enabled(false);
        features.put("cardPresent",obj1);
        subscriptionInformation.features(features);
        cardProcessing.subscriptionInformation(subscriptionInformation);

        PaymentsProductsCardProcessingConfigurationInformation configurationInformation=new PaymentsProductsCardProcessingConfigurationInformation();

        CardProcessingConfig configurations=new CardProcessingConfig();
        CardProcessingConfigCommon common=new CardProcessingConfigCommon();
        common.merchantCategoryCode("5999");
        common.preferCobadgedSecondaryBrand(true);


        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj5=new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer=new CardProcessingConfigCommonAcquirer();
        acquirer.countryCode("344_hongkong");
        acquirer.institutionId("22344");

        obj5.acquirer(acquirer);

        Map<String, CardProcessingConfigCommonCurrencies1> currencies=new HashMap<>();


        CardProcessingConfigCommonCurrencies1 obj6=new CardProcessingConfigCommonCurrencies1();
        obj6.enabled(true);
        obj6.merchantId("12345612344");
        obj6.terminalId("12121212");
        currencies.put("AUD",obj6);
        obj5.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes=new HashMap<>();
        CardProcessingConfigCommonPaymentTypes obj7=new CardProcessingConfigCommonPaymentTypes();
        obj7.enabled(true);
        paymentTypes.put("EFTPOS",obj7);

        obj5.paymentTypes(paymentTypes);

        obj5.enableCVVResponseIndicator(true);
        obj5.enableLeastCostRouting(true);
        obj5.merchantTier("000");

        processors.put("EFTPOS",obj5);

        common.processors(processors);
        configurations.common(common);

        CardProcessingConfigFeatures features2=new CardProcessingConfigFeatures();

        configurations.features(features2);
        configurationInformation.configurations(configurations);
        UUID templateId = UUID.fromString("1F9B7F6E-F0DB-44C8-BF8E-5013E34C0F87");
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
