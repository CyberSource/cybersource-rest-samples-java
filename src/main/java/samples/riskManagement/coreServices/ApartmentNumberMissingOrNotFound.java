package samples.riskManagement.coreServices;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;

public class ApartmentNumberMissingOrNotFound {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static RiskV1AddressVerificationsPost201Response run() {
	
		VerifyCustomerAddressRequest requestObj = new VerifyCustomerAddressRequest();

		Riskv1addressverificationsClientReferenceInformation clientReferenceInformation = new Riskv1addressverificationsClientReferenceInformation();
		clientReferenceInformation.code("addressEg");
		clientReferenceInformation.comments("dav-error response check");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1addressverificationsOrderInformation orderInformation = new Riskv1addressverificationsOrderInformation();
		Riskv1addressverificationsOrderInformationBillTo orderInformationBillTo = new Riskv1addressverificationsOrderInformationBillTo();
		orderInformationBillTo.address1("6th 4th ave");
		orderInformationBillTo.address2("");
		orderInformationBillTo.administrativeArea("NY");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("rensslaer");
		orderInformationBillTo.postalCode("12144");
		orderInformation.billTo(orderInformationBillTo);


		List <Riskv1addressverificationsOrderInformationLineItems> lineItems =  new ArrayList <Riskv1addressverificationsOrderInformationLineItems>();
		Riskv1addressverificationsOrderInformationLineItems lineItems1 = new Riskv1addressverificationsOrderInformationLineItems();
		lineItems1.unitPrice("120.50");
		lineItems1.quantity(3);
		lineItems1.productSKU("996633");
		lineItems1.productName("qwerty");
		lineItems1.productCode("handling");
		lineItems.add(lineItems1);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		RiskV1AddressVerificationsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			VerificationApi apiInstance = new VerificationApi(apiClient);
			result = apiInstance.verifyCustomerAddress(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String args[]) throws Exception{
		
			run();
	}
}
