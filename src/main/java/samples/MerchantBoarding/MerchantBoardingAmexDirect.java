package samples.MerchantBoarding;

import Api.MerchantBoardingApi;
import Data.MerchantBoardingConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class MerchantBoardingAmexDirect {


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

        PostRegistrationBody reqObj = new PostRegistrationBody();

        Boardingv1registrationsOrganizationInformation organizationInformation = new Boardingv1registrationsOrganizationInformation();
        organizationInformation.parentOrganizationId("apitester00");
        organizationInformation.type("MERCHANT");
        organizationInformation.configurable(true);

        Boardingv1registrationsOrganizationInformationBusinessInformation businessInformation = new Boardingv1registrationsOrganizationInformationBusinessInformation();
        businessInformation.name("StuartWickedFastEatz");
        Boardingv1registrationsOrganizationInformationBusinessInformationAddress address = new Boardingv1registrationsOrganizationInformationBusinessInformationAddress();
        address.country("US");
        address.address1("123456 SandMarket");
        address.locality("ORMOND BEACH");
        address.administrativeArea("FL");
        address.postalCode("32176");
        businessInformation.address(address);
        businessInformation.websiteUrl("https://www.StuartWickedEats.com");
        businessInformation.phoneNumber("6574567813");

        Boardingv1registrationsOrganizationInformationBusinessInformationBusinessContact businessContact = new Boardingv1registrationsOrganizationInformationBusinessInformationBusinessContact();
        businessContact.firstName("Stuart");
        businessContact.lastName("Stuart");
        businessContact.phoneNumber("6574567813");
        businessContact.email("svc_email_bt@corpdev.visa.com");
        businessInformation.businessContact(businessContact);
        businessInformation.merchantCategoryCode("5999");
        organizationInformation.businessInformation(businessInformation);

        reqObj.organizationInformation(organizationInformation);


        Boardingv1registrationsProductInformation productInformation = new Boardingv1registrationsProductInformation();
        Boardingv1registrationsProductInformationSelectedProducts selectedProducts = new Boardingv1registrationsProductInformationSelectedProducts();

        PaymentsProducts payments = new PaymentsProducts();
        PaymentsProductsCardProcessing cardProcessing = new PaymentsProductsCardProcessing();
        PaymentsProductsCardProcessingSubscriptionInformation subscriptionInformation = new PaymentsProductsCardProcessingSubscriptionInformation();

        subscriptionInformation.enabled(true);
        Map<String, PaymentsProductsCardProcessingSubscriptionInformationFeatures> features = new HashMap<>();

        PaymentsProductsCardProcessingSubscriptionInformationFeatures obj1 = new PaymentsProductsCardProcessingSubscriptionInformationFeatures();
        obj1.enabled(true);
        features.put("cardNotPresent",obj1);
        features.put("cardPresent",obj1);
        subscriptionInformation.features(features);
        cardProcessing.subscriptionInformation(subscriptionInformation);

        PaymentsProductsCardProcessingConfigurationInformation configurationInformation = new PaymentsProductsCardProcessingConfigurationInformation();

        CardProcessingConfig configurations = new CardProcessingConfig();
        CardProcessingConfigCommon common = new CardProcessingConfigCommon();
        common.merchantCategoryCode("1799");
        CardProcessingConfigCommonMerchantDescriptorInformation merchantDescriptorInformation = new CardProcessingConfigCommonMerchantDescriptorInformation();
        merchantDescriptorInformation.city("Cupertino");
        merchantDescriptorInformation.country("USA");
        merchantDescriptorInformation.name("Mer name");
        merchantDescriptorInformation.phone("8885554444");
        merchantDescriptorInformation.zip("94043");
        merchantDescriptorInformation.state("CA");
        merchantDescriptorInformation.street("mer street");
        merchantDescriptorInformation.url("www.test.com");

        common.merchantDescriptorInformation(merchantDescriptorInformation);

        common.subMerchantId("123457");
        common.subMerchantBusinessName("bus name");

        Map<String, CardProcessingConfigCommonProcessors> processors = new HashMap<>();
        CardProcessingConfigCommonProcessors obj2 = new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer = new CardProcessingConfigCommonAcquirer();

        obj2.acquirer(acquirer);
        Map<String, CardProcessingConfigCommonCurrencies1> currencies = new HashMap<>();
        CardProcessingConfigCommonCurrencies1 obj3 = new CardProcessingConfigCommonCurrencies1();
        obj3.enabled(true);
        obj3.enabledCardPresent(false);
        obj3.enabledCardPresent(true);
        obj3.terminalId("");
        obj3.serviceEnablementNumber("1234567890");
        currencies.put("AED",obj3);
        currencies.put("FJD",obj3);
        currencies.put("USD",obj3);

        obj2.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes = new HashMap<>();
        CardProcessingConfigCommonPaymentTypes obj4 = new CardProcessingConfigCommonPaymentTypes();
        obj4.enabled(true);
        paymentTypes.put("AMERICAN_EXPRESS",obj4);

        obj2.paymentTypes(paymentTypes);
        obj2.allowMultipleBills(false);
        obj2.avsFormat("basic");
        obj2.batchGroup("amexdirect_vme_default");
        obj2.enableAutoAuthReversalAfterVoid(false);
        obj2.enhancedData("disabled");
        obj2.enableLevel2(false);
        obj2.amexTransactionAdviceAddendum1("amex123");
        processors.put("acquirer",obj2);

        common.processors(processors);
        configurations.common(common);

        CardProcessingConfigFeatures features2 = new CardProcessingConfigFeatures();
        CardProcessingConfigFeaturesCardNotPresent cardNotPresent = new CardProcessingConfigFeaturesCardNotPresent();

        Map<String, CardProcessingConfigFeaturesCardNotPresentProcessors> processors3 = new HashMap<>();
        CardProcessingConfigFeaturesCardNotPresentProcessors obj5 = new CardProcessingConfigFeaturesCardNotPresentProcessors();
        obj5.relaxAddressVerificationSystem(true);
        obj5.relaxAddressVerificationSystemAllowExpiredCard(true);
        obj5.relaxAddressVerificationSystemAllowZipWithoutCountry(false);
        processors3.put("amexdirect",obj5);

        cardNotPresent.processors(processors3);
        features2.cardNotPresent(cardNotPresent);
        configurations.features(features2);
        configurationInformation.configurations(configurations);
        configurationInformation.templateId("2B80A3C7-5A39-4CC3-9882-AC4A828D3646");
        cardProcessing.configurationInformation(configurationInformation);
        payments.cardProcessing(cardProcessing);

        PaymentsProductsVirtualTerminal virtualTerminal = new PaymentsProductsVirtualTerminal();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation2 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation2.enabled(true);
//        virtualTerminal.subscriptionInformation(subscriptionInformation2);

        PaymentsProductsVirtualTerminalConfigurationInformation configurationInformation3 = new PaymentsProductsVirtualTerminalConfigurationInformation();

        configurationInformation3.templateId("9FA1BB94-5119-48D3-B2E5-A81FD3C657B5");
        virtualTerminal.configurationInformation(configurationInformation3);
        payments.virtualTerminal(virtualTerminal);

        PaymentsProductsTax customerInvoicing = new PaymentsProductsTax();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation6 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation6.enabled(true);
//        customerInvoicing.subscriptionInformation(subscriptionInformation6);
        payments.customerInvoicing(customerInvoicing);
        selectedProducts.payments(payments);

        RiskProducts risk = new RiskProducts();
        selectedProducts.risk(risk);

        CommerceSolutionsProducts commerceSolutions = new CommerceSolutionsProducts();
        CommerceSolutionsProductsTokenManagement tokenManagement = new CommerceSolutionsProductsTokenManagement();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation7 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation7.enabled(true);
//        tokenManagement.subscriptionInformation(subscriptionInformation7);
        CommerceSolutionsProductsTokenManagementConfigurationInformation configurationInformation4 = new CommerceSolutionsProductsTokenManagementConfigurationInformation();

        configurationInformation4.templateId("D62BEE20-DCFD-4AA2-8723-BA3725958ABA");
        tokenManagement.configurationInformation(configurationInformation4);
        commerceSolutions.tokenManagement(tokenManagement);
        selectedProducts.commerceSolutions(commerceSolutions);

        ValueAddedServicesProducts valueAddedServices = new ValueAddedServicesProducts();
        PaymentsProductsTax transactionSearch = new PaymentsProductsTax();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation8 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation8.enabled(true);
//        transactionSearch.subscriptionInformation(subscriptionInformation8);

        valueAddedServices.transactionSearch(transactionSearch);
        PaymentsProductsTax reporting = new PaymentsProductsTax();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation9 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation9.enabled(true);
//        reporting.subscriptionInformation(subscriptionInformation9);
        valueAddedServices.reporting(reporting);

        selectedProducts.valueAddedServices(valueAddedServices);
        productInformation.selectedProducts(selectedProducts);
        reqObj.productInformation(productInformation);

        InlineResponse2013 result = null;

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
