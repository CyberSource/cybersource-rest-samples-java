package samples.MerchantBoarding;

import Api.MerchantBoardingApi;
import Data.MerchantBoardingConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class MerchantBoardingCUP {

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


    public static InlineResponse2014 run() {

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
        Map<String, CardProcessingConfigCommonProcessors> processors = new HashMap<>();

        CardProcessingConfigCommonProcessors obj2 = new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer = new CardProcessingConfigCommonAcquirer();

        acquirer.countryCode("344_hongkong");
        acquirer.institutionId("22344");
        obj2.acquirer(acquirer);

        Map<String, CardProcessingConfigCommonCurrencies1> currencies = new HashMap<>();

        CardProcessingConfigCommonCurrencies1 obj3 = new CardProcessingConfigCommonCurrencies1();
        obj3.enabled(true);
        obj3.enabledCardPresent(false);
        obj3.enabledCardNotPresent(true);
        obj3.merchantId("112233");
        obj3.terminalId("11224455");
        obj3.serviceEnablementNumber("");
        currencies.put("HKD",obj3);
        currencies.put("AUD",obj3);
        currencies.put("USD",obj3);

        obj2.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes = new HashMap<>();

        CardProcessingConfigCommonPaymentTypes obj4 = new CardProcessingConfigCommonPaymentTypes();
        obj4.enabled(true);
        paymentTypes.put("CUP",obj4);
        obj2.paymentTypes(paymentTypes);

        processors.put("CUP",obj2);
        common.processors(processors);
        configurations.common(common);
        configurationInformation.configurations(configurations);

        configurationInformation.templateId("1D8BC41A-F04E-4133-87C8-D89D1806106F");
        cardProcessing.configurationInformation(configurationInformation);
        payments.cardProcessing(cardProcessing);

        PaymentsProductsVirtualTerminal virtualTerminal = new PaymentsProductsVirtualTerminal();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation2 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation2.enabled(true);
//        virtualTerminal.subscriptionInformation(subscriptionInformation2);

        PaymentsProductsVirtualTerminalConfigurationInformation configurationInformation2 = new PaymentsProductsVirtualTerminalConfigurationInformation();

        configurationInformation2.templateId("9FA1BB94-5119-48D3-B2E5-A81FD3C657B5");

        virtualTerminal.configurationInformation(configurationInformation2);
        payments.virtualTerminal(virtualTerminal);

        PaymentsProductsTax customerInvoicing = new PaymentsProductsTax();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation3 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//
//        subscriptionInformation3.enabled(true);
//        customerInvoicing.subscriptionInformation(subscriptionInformation3);
        payments.customerInvoicing(customerInvoicing);
        selectedProducts.payments(payments);

        RiskProducts risk = new RiskProducts();
        selectedProducts.risk(risk);
        CommerceSolutionsProducts commerceSolutions = new CommerceSolutionsProducts();

        CommerceSolutionsProductsTokenManagement tokenManagement = new CommerceSolutionsProductsTokenManagement();

//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation4 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//        subscriptionInformation4.enabled(true);
//        tokenManagement.subscriptionInformation(subscriptionInformation4);

        CommerceSolutionsProductsTokenManagementConfigurationInformation configurationInformation3 = new CommerceSolutionsProductsTokenManagementConfigurationInformation();

        configurationInformation3.templateId("9FA1BB94-5119-48D3-B2E5-A81FD3C657B5");
        tokenManagement.configurationInformation(configurationInformation3);
        commerceSolutions.tokenManagement(tokenManagement);

        selectedProducts.commerceSolutions(commerceSolutions);

        ValueAddedServicesProducts valueAddedServices = new ValueAddedServicesProducts();

        PaymentsProductsTax transactionSearch = new PaymentsProductsTax();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation5 = new PaymentsProductsPayerAuthenticationSubscriptionInformation();
//        subscriptionInformation5.enabled(true);
//        transactionSearch.subscriptionInformation(subscriptionInformation5);
        valueAddedServices.transactionSearch(transactionSearch);

        PaymentsProductsTax reporting = new PaymentsProductsTax();
//        reporting.subscriptionInformation(subscriptionInformation5);
        valueAddedServices.reporting(reporting);
        selectedProducts.valueAddedServices(valueAddedServices);
        productInformation.selectedProducts(selectedProducts);
        reqObj.productInformation(productInformation);


        InlineResponse2014 result = null;

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
