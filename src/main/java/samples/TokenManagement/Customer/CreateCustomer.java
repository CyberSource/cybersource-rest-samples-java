package samples.TokenManagement.Customer;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
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
	public static TmsV2CustomersResponse run() {
	
		PostCustomerRequest requestObj = new PostCustomerRequest();

		Tmsv2customersBuyerInformation buyerInformation = new Tmsv2customersBuyerInformation();
		buyerInformation.merchantCustomerID("Your customer identifier");
		buyerInformation.email("test@cybs.com");
		requestObj.buyerInformation(buyerInformation);

		Tmsv2customersClientReferenceInformation clientReferenceInformation = new Tmsv2customersClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);


		List <Tmsv2customersMerchantDefinedInformation> merchantDefinedInformation =  new ArrayList <Tmsv2customersMerchantDefinedInformation>();
		Tmsv2customersMerchantDefinedInformation merchantDefinedInformation1 = new Tmsv2customersMerchantDefinedInformation();
		merchantDefinedInformation1.name("data1");
		merchantDefinedInformation1.value("Your customer data");
		merchantDefinedInformation.add(merchantDefinedInformation1);

		requestObj.merchantDefinedInformation(merchantDefinedInformation);

		TmsV2CustomersResponse result = null;
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
