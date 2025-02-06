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

public class MerchantBoardingTSYS {


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
        common.merchantCategoryCode("5999");
        common.processLevel3Data("ignored");
        organizationInformation.type("MERCHANT");
        common.enablePartialAuth(false);
        common.amexVendorCode("2233");

        CardProcessingConfigCommonMerchantDescriptorInformation merchantDescriptorInformation=new CardProcessingConfigCommonMerchantDescriptorInformation();

        merchantDescriptorInformation.city("cpertino");
        merchantDescriptorInformation.country("USA");
        merchantDescriptorInformation.name("kumar");
        merchantDescriptorInformation.state("CA");
        merchantDescriptorInformation.phone("888555333");
        merchantDescriptorInformation.zip("94043");
        merchantDescriptorInformation.street("steet1");

        common.merchantDescriptorInformation(merchantDescriptorInformation);

        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj5=new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer=new CardProcessingConfigCommonAcquirer();

        obj5.acquirer(acquirer);

        Map<String, CardProcessingConfigCommonCurrencies1> currencies=new HashMap<>();


        CardProcessingConfigCommonCurrencies1 obj6=new CardProcessingConfigCommonCurrencies1();
        obj6.enabled(true);
        obj6.enabledCardPresent(true);
        obj6.enabledCardNotPresent(true);
        obj6.terminalId("1234");
        obj6.serviceEnablementNumber("");

        currencies.put("CAD",obj6);

        obj5.currencies(currencies);

        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes=new HashMap<>();
        CardProcessingConfigCommonPaymentTypes obj7=new CardProcessingConfigCommonPaymentTypes();
        obj7.enabled(true);

        paymentTypes.put("MASTERCARD",obj7);
        paymentTypes.put("VISA",obj7);

        obj5.paymentTypes(paymentTypes);

        obj5.bankNumber("234576");
        obj5.chainNumber("223344");
        obj5.batchGroup("vital_1130");
        obj5.enhancedData("disabled");
        obj5.industryCode("D");
        obj5.merchantBinNumber("765576");
        obj5.merchantId("834215123456");
        obj5.merchantLocationNumber("00001");
        obj5.storeID("2563");
        obj5.vitalNumber("71234567");
        obj5.quasiCash(false);
        obj5.sendAmexLevel2Data(null);
        obj5.softDescriptorType("1 - trans_ref_no");
        obj5.travelAgencyCode("2356");
        obj5.travelAgencyName("Agent");

        processors.put("tsys",obj5);

        common.processors(processors);

        configurations.common(common);

        CardProcessingConfigFeatures features2=new CardProcessingConfigFeatures();

        CardProcessingConfigFeaturesCardNotPresent cardNotPresent=new CardProcessingConfigFeaturesCardNotPresent();

        cardNotPresent.visaStraightThroughProcessingOnly(false);
        cardNotPresent.amexTransactionAdviceAddendum1(null);

        features2.cardNotPresent(cardNotPresent);


        configurations.features(features2);
        configurationInformation.configurations(configurations);
        UUID templateId = UUID.fromString("818048AD-2860-4D2D-BC39-2447654628A1");
        configurationInformation.templateId(templateId);

        cardProcessing.configurationInformation(configurationInformation);
        payments.cardProcessing(cardProcessing);

        PaymentsProductsVirtualTerminal virtualTerminal=new PaymentsProductsVirtualTerminal();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation5=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation5.enabled(true);
        virtualTerminal.subscriptionInformation(subscriptionInformation5);

        PaymentsProductsVirtualTerminalConfigurationInformation configurationInformation5=new PaymentsProductsVirtualTerminalConfigurationInformation();
        UUID templateId2 = UUID.fromString("9FA1BB94-5119-48D3-B2E5-A81FD3C657B5");
        configurationInformation5.templateId(templateId2);
        virtualTerminal.configurationInformation(configurationInformation5);

        payments.virtualTerminal(virtualTerminal);

        PaymentsProductsTax customerInvoicing=new PaymentsProductsTax();

        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation6=new PaymentsProductsPayerAuthenticationSubscriptionInformation();

        subscriptionInformation6.enabled(true);
        customerInvoicing.subscriptionInformation(subscriptionInformation6);
        payments.customerInvoicing(customerInvoicing);

        selectedProducts.payments(payments);

        RiskProducts risk=new RiskProducts();

        selectedProducts.risk(risk);

        CommerceSolutionsProducts commerceSolutions=new CommerceSolutionsProducts();

        CommerceSolutionsProductsTokenManagement tokenManagement=new CommerceSolutionsProductsTokenManagement();

        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation7=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation7.enabled(true);
        tokenManagement.subscriptionInformation(subscriptionInformation7);

        CommerceSolutionsProductsTokenManagementConfigurationInformation configurationInformation7=new CommerceSolutionsProductsTokenManagementConfigurationInformation();

        UUID templateId3 = UUID.fromString("D62BEE20-DCFD-4AA2-8723-BA3725958ABA");
        configurationInformation7.templateId(templateId3);
        tokenManagement.configurationInformation(configurationInformation7);

        commerceSolutions.tokenManagement(tokenManagement);
        selectedProducts.commerceSolutions(commerceSolutions);

        ValueAddedServicesProducts valueAddedServices=new ValueAddedServicesProducts();

        PaymentsProductsTax transactionSearch=new PaymentsProductsTax();

        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation9=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation9.enabled(true);
        transactionSearch.subscriptionInformation(subscriptionInformation9);
        valueAddedServices.transactionSearch(transactionSearch);

        PaymentsProductsTax reporting=new PaymentsProductsTax();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation3=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation3.enabled(true);
        reporting.subscriptionInformation(subscriptionInformation3);
        valueAddedServices.reporting(reporting);

        selectedProducts.valueAddedServices(valueAddedServices);

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
