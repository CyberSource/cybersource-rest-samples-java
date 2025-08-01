package samples.MerchantBoarding;

import Api.MerchantBoardingApi;
import Data.MerchantBoardingConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class MerchantBoardingBarclays {


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


    public static InlineResponse2013 run() {

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

        common.merchantCategoryCode("5999");
        organizationInformation.type("MERCHANT");

        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj2=new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer=new CardProcessingConfigCommonAcquirer();

        obj2.acquirer(acquirer);
        Map<String, CardProcessingConfigCommonCurrencies1> currencies=new HashMap<>();
        CardProcessingConfigCommonCurrencies1 obj3=new CardProcessingConfigCommonCurrencies1();
        obj3.enabled(true);
        obj3.enabledCardPresent(false);
        obj3.enabledCardNotPresent(true);
        obj3.merchantId("1234");
        obj3.serviceEnablementNumber("");
        List<String> terminalIds=new ArrayList<>();

        terminalIds.add("12351245");
        obj3.terminalIds(terminalIds);


        currencies.put("AED",obj3);
        currencies.put("USD",obj3);

        obj2.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes=new HashMap<>();

        CardProcessingConfigCommonPaymentTypes obj4=new CardProcessingConfigCommonPaymentTypes();
        obj4.enabled(true);
        paymentTypes.put("MASTERCARD",obj4);
        paymentTypes.put("VISA",obj4);

        obj2.paymentTypes(paymentTypes);

        obj2.batchGroup("barclays2_16");
        obj2.quasiCash(false);
        obj2.enhancedData("disabled");
        obj2.merchantId("124555");
        obj2.enableMultiCurrencyProcessing("false");

        processors.put("barclays2",obj2);

        common.processors(processors);
        configurations.common(common);
        CardProcessingConfigFeatures features3=new CardProcessingConfigFeatures();

        CardProcessingConfigFeaturesCardNotPresent cardNotPresent=new CardProcessingConfigFeaturesCardNotPresent();

        Map<String, CardProcessingConfigFeaturesCardNotPresentProcessors> processors4=new HashMap<>();
        CardProcessingConfigFeaturesCardNotPresentProcessors obj6=new CardProcessingConfigFeaturesCardNotPresentProcessors();

        CardProcessingConfigFeaturesCardNotPresentPayouts payouts=new CardProcessingConfigFeaturesCardNotPresentPayouts();

        payouts.merchantId("1233");
        payouts.terminalId("1244");
        obj6.payouts(payouts);
        processors4.put("barclays2",obj6);
        cardNotPresent.processors(processors4);
        features3.cardNotPresent(cardNotPresent);

        configurations.features(features3);

        configurationInformation.configurations(configurations);

        UUID templateId = UUID.fromString("0A413572-1995-483C-9F48-FCBE4D0B2E86");
        configurationInformation.templateId(templateId);
        cardProcessing.configurationInformation(configurationInformation);

        payments.cardProcessing(cardProcessing);

        PaymentsProductsVirtualTerminal virtualTerminal=new PaymentsProductsVirtualTerminal();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation2=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//        subscriptionInformation2.enabled(true);
//        virtualTerminal.subscriptionInformation(subscriptionInformation2);

        PaymentsProductsVirtualTerminalConfigurationInformation configurationInformation2=new PaymentsProductsVirtualTerminalConfigurationInformation();

        UUID templateId3 = UUID.fromString("E4EDB280-9DAC-4698-9EB9-9434D40FF60C");
        configurationInformation2.templateId(templateId3);
        virtualTerminal.configurationInformation(configurationInformation2);
        payments.virtualTerminal(virtualTerminal);

        PaymentsProductsTax customerInvoicing=new PaymentsProductsTax();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation3=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation3.enabled(true);
//        customerInvoicing.subscriptionInformation(subscriptionInformation3);

        payments.customerInvoicing(customerInvoicing);

        selectedProducts.payments(payments);

        RiskProducts risk2=new RiskProducts();
        selectedProducts.risk(risk2);

        CommerceSolutionsProducts commerceSolutions=new CommerceSolutionsProducts();
        CommerceSolutionsProductsTokenManagement tokenManagement=new CommerceSolutionsProductsTokenManagement();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation5=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation5.enabled(true);
//        tokenManagement.subscriptionInformation(subscriptionInformation5);

        CommerceSolutionsProductsTokenManagementConfigurationInformation configurationInformation5=new CommerceSolutionsProductsTokenManagementConfigurationInformation();

        UUID templateId4 = UUID.fromString("D62BEE20-DCFD-4AA2-8723-BA3725958ABA");
        configurationInformation5.templateId(templateId4);
        tokenManagement.configurationInformation(configurationInformation5);
        commerceSolutions.tokenManagement(tokenManagement);
        selectedProducts.commerceSolutions(commerceSolutions);

        ValueAddedServicesProducts valueAddedServices=new ValueAddedServicesProducts();

        PaymentsProductsTax transactionSearch=new PaymentsProductsTax();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation6=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation6.enabled(true);
//        transactionSearch.subscriptionInformation(subscriptionInformation6);
//        valueAddedServices.transactionSearch(transactionSearch);

        PaymentsProductsTax reporting=new PaymentsProductsTax();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation7=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//        subscriptionInformation7.enabled(true);
//        reporting.subscriptionInformation(subscriptionInformation7);
        valueAddedServices.reporting(reporting);
        selectedProducts.valueAddedServices(valueAddedServices);
        productInformation.selectedProducts(selectedProducts);
        reqObj.productInformation(productInformation);


        InlineResponse2013 result=null;

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
