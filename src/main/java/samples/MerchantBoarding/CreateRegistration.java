package samples.MerchantBoarding;

import Api.CustomerApi;
import Api.MerchantBoardingApi;
import Data.MerchantBoardingConfiguration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class CreateRegistration {

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
        PaymentsProductsPayerAuthentication payerAuthentication=new PaymentsProductsPayerAuthentication();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation.enabled(true);
        payerAuthentication.subscriptionInformation(subscriptionInformation);

        PaymentsProductsPayerAuthenticationConfigurationInformation configurationInformation=new PaymentsProductsPayerAuthenticationConfigurationInformation();
        PayerAuthConfig configurations=new PayerAuthConfig();
        PayerAuthConfigCardTypes cardTypes=new PayerAuthConfigCardTypes();
        PayerAuthConfigCardTypesVerifiedByVisa verifiedByVisa=new PayerAuthConfigCardTypesVerifiedByVisa();
        List<PayerAuthConfigCardTypesVerifiedByVisaCurrencies> currencies=new ArrayList<>();
        PayerAuthConfigCardTypesVerifiedByVisaCurrencies currency1=new PayerAuthConfigCardTypesVerifiedByVisaCurrencies();
        List<String> currencyCodes=new ArrayList<>();
        currencyCodes.add("ALL");
        currency1.currencyCodes(currencyCodes);
        currency1.acquirerId("469216");
        currency1.processorMerchantId("678855");

        currencies.add(currency1);
        verifiedByVisa.currencies(currencies);
        cardTypes.verifiedByVisa(verifiedByVisa);
        configurations.cardTypes(cardTypes);
       configurationInformation.configurations(configurations);
        payerAuthentication.configurationInformation(configurationInformation);
        payments.payerAuthentication(payerAuthentication);

        PaymentsProductsCardProcessing cardProcessing=new PaymentsProductsCardProcessing();
        PaymentsProductsCardProcessingSubscriptionInformation subscriptionInformation2=new PaymentsProductsCardProcessingSubscriptionInformation();
        subscriptionInformation2.enabled(true);
        Map<String, PaymentsProductsCardProcessingSubscriptionInformationFeatures> features=new HashMap<>();
        PaymentsProductsCardProcessingSubscriptionInformationFeatures obj=new PaymentsProductsCardProcessingSubscriptionInformationFeatures();
        obj.enabled(true);
        features.put("cardNotPresent",obj);
        subscriptionInformation2.features(features);
        cardProcessing.subscriptionInformation(subscriptionInformation2);

        PaymentsProductsCardProcessingConfigurationInformation configurationInformation2=new PaymentsProductsCardProcessingConfigurationInformation();

        CardProcessingConfig configurations2=new CardProcessingConfig();
        CardProcessingConfigCommon common=new CardProcessingConfigCommon();
        common.merchantCategoryCode("1234");
        CardProcessingConfigCommonMerchantDescriptorInformation merchantDescriptorInformation=new CardProcessingConfigCommonMerchantDescriptorInformation();

        merchantDescriptorInformation.name("r4ef");
        merchantDescriptorInformation.city("Bellevue");
        merchantDescriptorInformation.country("US");
        merchantDescriptorInformation.phone("4255547845");
        merchantDescriptorInformation.state("WA");
        merchantDescriptorInformation.street("StreetName");
        merchantDescriptorInformation.zip("98007");
        common.merchantDescriptorInformation(merchantDescriptorInformation);


        Map<String, CardProcessingConfigCommonProcessors> processors=new HashMap<>();
        CardProcessingConfigCommonProcessors obj2=new CardProcessingConfigCommonProcessors();
        obj2.merchantId("123456789101");
        obj2.terminalId("1231");
        obj2.industryCode(CardProcessingConfigCommonProcessors.IndustryCodeEnum.D);
        obj2.vitalNumber("71234567");
        obj2.merchantBinNumber("123456");
        obj2.merchantLocationNumber("00001");
        obj2.storeID("1234");
        obj2.settlementCurrency("USD");
        processors.put("tsys",obj2);
        common.processors(processors);
        configurations2.common(common);

        CardProcessingConfigFeatures features2=new CardProcessingConfigFeatures();
        CardProcessingConfigFeaturesCardNotPresent cardNotPresent=new CardProcessingConfigFeaturesCardNotPresent();

        cardNotPresent.visaStraightThroughProcessingOnly(true);
        features2.cardNotPresent(cardNotPresent);
        configurations2.features(features2);
        configurationInformation2.configurations(configurations2);
        cardProcessing.configurationInformation(configurationInformation2);
        payments.cardProcessing(cardProcessing);

        PaymentsProductsVirtualTerminal virtualTerminal=new PaymentsProductsVirtualTerminal();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation3=new PaymentsProductsPayerAuthenticationSubscriptionInformation();

        subscriptionInformation3.enabled(true);
        virtualTerminal.subscriptionInformation(subscriptionInformation3);
        payments.virtualTerminal(virtualTerminal);

        PaymentsProductsTax customerInvoicing=new PaymentsProductsTax();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation4=new PaymentsProductsPayerAuthenticationSubscriptionInformation();

        subscriptionInformation4.enabled(true);
        customerInvoicing.subscriptionInformation(subscriptionInformation4);
        payments.customerInvoicing(customerInvoicing);

        PaymentsProductsPayouts payouts=new PaymentsProductsPayouts();
        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation5=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
       subscriptionInformation5.enabled(true);
        payouts.subscriptionInformation(subscriptionInformation5);
        payments.payouts(payouts);

        selectedProducts.payments(payments);

        CommerceSolutionsProducts commerceSolutions=new CommerceSolutionsProducts();
        CommerceSolutionsProductsTokenManagement tokenManagement=new CommerceSolutionsProductsTokenManagement();

        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation6=new PaymentsProductsPayerAuthenticationSubscriptionInformation();

        subscriptionInformation6.enabled(true);
        tokenManagement.subscriptionInformation(subscriptionInformation6);
        commerceSolutions.tokenManagement(tokenManagement);
        selectedProducts.commerceSolutions(commerceSolutions);

        RiskProducts risk=new RiskProducts();
        RiskProductsFraudManagementEssentials fraudManagementEssentials=new RiskProductsFraudManagementEssentials();

        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation7=new PaymentsProductsPayerAuthenticationSubscriptionInformation();
        subscriptionInformation7.enabled(true);
        fraudManagementEssentials.subscriptionInformation(subscriptionInformation7);

        RiskProductsFraudManagementEssentialsConfigurationInformation configurationInformation5=new RiskProductsFraudManagementEssentialsConfigurationInformation();

        UUID templateId = UUID.fromString("E4EDB280-9DAC-4698-9EB9-9434D40FF60C");
        configurationInformation5.templateId(templateId);
        fraudManagementEssentials.configurationInformation(configurationInformation5);
        risk.fraudManagementEssentials(fraudManagementEssentials);

        selectedProducts.risk(risk);

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
