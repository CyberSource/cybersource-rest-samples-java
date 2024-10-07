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

public class MerchantBoardingVPC {

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
        features.put("cardPresent",obj1);
        subscriptionInformation.features(features);
        cardProcessing.subscriptionInformation(subscriptionInformation);


        PaymentsProductsCardProcessingConfigurationInformation configurationInformation=new PaymentsProductsCardProcessingConfigurationInformation();

        CardProcessingConfig configurations=new CardProcessingConfig();
        CardProcessingConfigCommon common=new CardProcessingConfigCommon();
        common.merchantCategoryCode("1799");
        common.defaultAuthTypeCode(CardProcessingConfigCommon.DefaultAuthTypeCodeEnum.FINAL);
        common.masterCardAssignedId(null);
        common.sicCode(null);
        common.enablePartialAuth(false);

        common.enableInterchangeOptimization(false);
        common.enableSplitShipment(false);
        common.visaDelegatedAuthenticationId("123457");

        //TBC
        common.domesticMerchantId(false);
        //
        common.creditCardRefundLimitPercent("2");
        common.businessCenterCreditCardRefundLimitPercent("3");
        common.allowCapturesGreaterThanAuthorizations(false);
        common.enableDuplicateMerchantReferenceNumberBlocking(false);


        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj5=new CardProcessingConfigCommonProcessors();
        CardProcessingConfigCommonAcquirer acquirer=new CardProcessingConfigCommonAcquirer();

        acquirer.countryCode("840_usa");
        acquirer.fileDestinationBin("444500");
        acquirer.interbankCardAssociationId("3684");
        acquirer.institutionId("444571");
        acquirer.discoverInstitutionId(null);


        obj5.acquirer(acquirer);


        Map<String, CardProcessingConfigCommonPaymentTypes> paymentTypes=new HashMap<>();
        CardProcessingConfigCommonPaymentTypes obj7=new CardProcessingConfigCommonPaymentTypes();
        obj7.enabled(true);

        Map<String, CardProcessingConfigCommonCurrencies> currencies=new HashMap<>();
        CardProcessingConfigCommonCurrencies obj2=new CardProcessingConfigCommonCurrencies();
        obj2.enabled(true);
        obj2.enabledCardPresent(false);
        obj2.enabledCardNotPresent(true);
        obj2.terminalId("113366");
        obj2.merchantId("113355");
        obj2.serviceEnablementNumber(null);

        currencies.put("CAD",obj2);
        currencies.put("USD",obj2);


        obj7.currencies(currencies);

        paymentTypes.put("VISA",obj7);

        obj5.paymentTypes(paymentTypes);

        obj5.acquirerMerchantId("123456");
        obj5.allowMultipleBills(false);
        obj5.batchGroup("vdcvantiv_est_00");
        obj5.businessApplicationId("AA");
        obj5.enableAutoAuthReversalAfterVoid(true);
        obj5.enableExpresspayPanTranslation(null);
        obj5.merchantVerificationValue("123456");
        obj5.quasiCash(false);
        obj5.enableTransactionReferenceNumber(true);

        processors.put("VPC",obj5);

        common.processors(processors);

        configurations.common(common);

        CardProcessingConfigFeatures features2=new CardProcessingConfigFeatures();

        CardProcessingConfigFeaturesCardNotPresent cardNotPresent=new CardProcessingConfigFeaturesCardNotPresent();

        Map<String, CardProcessingConfigFeaturesCardNotPresentProcessors> processors3=new HashMap<>();
        CardProcessingConfigFeaturesCardNotPresentProcessors obj9=new CardProcessingConfigFeaturesCardNotPresentProcessors();

        obj9.enableEmsTransactionRiskScore(null);
        obj9.relaxAddressVerificationSystem(true);
        obj9.relaxAddressVerificationSystemAllowExpiredCard(true);
        obj9.relaxAddressVerificationSystemAllowZipWithoutCountry(true);

        processors3.put("VPC",obj9);
        cardNotPresent.processors(processors3);

        cardNotPresent.visaStraightThroughProcessingOnly(false);
        cardNotPresent.ignoreAddressVerificationSystem(true);

        features2.cardNotPresent(cardNotPresent);

        CardProcessingConfigFeaturesCardPresent cardPresent=new CardProcessingConfigFeaturesCardPresent();

        Map<String, CardProcessingConfigFeaturesCardPresentProcessors> processors2=new HashMap<>();
        CardProcessingConfigFeaturesCardPresentProcessors obj4=new CardProcessingConfigFeaturesCardPresentProcessors();


        obj4.defaultPointOfSaleTerminalId("223355");
        obj4.defaultPointOfSaleTerminalId("223344");

        processors2.put("VPC",obj4);

        cardPresent.processors(processors2);

        cardPresent.enableTerminalIdLookup(false);
        features2.cardPresent(cardPresent);

        configurations.features(features2);
        configurationInformation.configurations(configurations);

        UUID templateId = UUID.fromString("D671CE88-2F09-469C-A1B4-52C47812F792");
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
