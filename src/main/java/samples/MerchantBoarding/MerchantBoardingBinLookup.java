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

public class MerchantBoardingBinLookup {


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
        selectedProducts.payments(payments);

        RiskProducts risk = new RiskProducts();
        selectedProducts.risk(risk);

        CommerceSolutionsProducts commerceSolutions = new CommerceSolutionsProducts();
        CommerceSolutionsProductsBinLookup binLookup = new CommerceSolutionsProductsBinLookup();
//        PaymentsProductsPayerAuthenticationSubscriptionInformation subscriptionInformation = new PaymentsProductsPayerAuthenticationSubscriptionInformation();

//        subscriptionInformation.enabled(true);
//        binLookup.subscriptionInformation(subscriptionInformation);
        CommerceSolutionsProductsBinLookupConfigurationInformation configurationInformation = new CommerceSolutionsProductsBinLookupConfigurationInformation();

        CommerceSolutionsProductsBinLookupConfigurationInformationConfigurations configurations = new CommerceSolutionsProductsBinLookupConfigurationInformationConfigurations();

        configurations.isPayoutOptionsEnabled(false);
        configurations.isAccountPrefixEnabled(true);

        configurationInformation.configurations(configurations);
        binLookup.configurationInformation(configurationInformation);

        commerceSolutions.binLookup(binLookup);
        selectedProducts.commerceSolutions(commerceSolutions);

        ValueAddedServicesProducts valueAddedServices = new ValueAddedServicesProducts();
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
