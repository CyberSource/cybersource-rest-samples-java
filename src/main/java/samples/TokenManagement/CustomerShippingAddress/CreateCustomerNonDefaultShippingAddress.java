package samples.TokenManagement.CustomerShippingAddress;

import java.lang.invoke.MethodHandles;
import java.util.*;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class CreateCustomerNonDefaultShippingAddress {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}
	public static PostCustomerShippingAddressRequest run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
	
		PostCustomerShippingAddressRequest requestObj = new PostCustomerShippingAddressRequest();

		requestObj._default(false);
		Tmsv2customersEmbeddedDefaultShippingAddressShipTo shipTo = new Tmsv2customersEmbeddedDefaultShippingAddressShipTo();
		shipTo.firstName("John");
		shipTo.lastName("Doe");
		shipTo.company("CyberSource");
		shipTo.address1("1 Market St");
		shipTo.locality("San Francisco");
		shipTo.administrativeArea("CA");
		shipTo.postalCode("94105");
		shipTo.country("US");
		shipTo.email("test@cybs.com");
		shipTo.phoneNumber("4158880000");
		requestObj.shipTo(shipTo);

		PostCustomerShippingAddressRequest result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CustomerShippingAddressApi apiInstance = new CustomerShippingAddressApi(apiClient);
			result = apiInstance.postCustomerShippingAddress(customerTokenId, requestObj, null);

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
