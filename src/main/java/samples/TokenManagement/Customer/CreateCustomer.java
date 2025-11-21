package samples.TokenManagement.Customer;

import java.lang.invoke.MethodHandles;
import java.util.*;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class CreateCustomer {
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
	public static PostCustomerRequest run() {
	
		PostCustomerRequest requestObj = new PostCustomerRequest();

		Tmsv2tokenizeTokenInformationCustomerBuyerInformation buyerInformation = new Tmsv2tokenizeTokenInformationCustomerBuyerInformation();
		buyerInformation.merchantCustomerID("Your customer identifier");
		buyerInformation.email("test@cybs.com");
		requestObj.buyerInformation(buyerInformation);

		Tmsv2tokenizeTokenInformationCustomerClientReferenceInformation clientReferenceInformation = new Tmsv2tokenizeTokenInformationCustomerClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);


		List <Tmsv2tokenizeTokenInformationCustomerMerchantDefinedInformation> merchantDefinedInformation =  new ArrayList <Tmsv2tokenizeTokenInformationCustomerMerchantDefinedInformation>();
		Tmsv2tokenizeTokenInformationCustomerMerchantDefinedInformation merchantDefinedInformation1 = new Tmsv2tokenizeTokenInformationCustomerMerchantDefinedInformation();
		merchantDefinedInformation1.name("data1");
		merchantDefinedInformation1.value("Your customer data");
		merchantDefinedInformation.add(merchantDefinedInformation1);

		requestObj.merchantDefinedInformation(merchantDefinedInformation);

		PostCustomerRequest result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CustomerApi apiInstance = new CustomerApi(apiClient);
			result = apiInstance.postCustomer(requestObj, null);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
