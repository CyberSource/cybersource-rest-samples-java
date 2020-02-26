package samples.riskManagement.coreServices;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;

public class VerboseRequestWithAllFields {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static RiskV1AddressVerificationsPost201Response run() {
	
		VerifyCustomerAddressRequest requestObj = new VerifyCustomerAddressRequest();

		Riskv1addressverificationsClientReferenceInformation clientReferenceInformation = new Riskv1addressverificationsClientReferenceInformation();
		clientReferenceInformation.code("addressEg");
		clientReferenceInformation.comments("dav-All fields");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1addressverificationsOrderInformation orderInformation = new Riskv1addressverificationsOrderInformation();
		Riskv1addressverificationsOrderInformationBillTo orderInformationBillTo = new Riskv1addressverificationsOrderInformationBillTo();
		orderInformationBillTo.address1("12301 research st");
		orderInformationBillTo.address2("1");
		orderInformationBillTo.address3("2");
		orderInformationBillTo.address4("3");
		orderInformationBillTo.administrativeArea("TX");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("Austin");
		orderInformationBillTo.postalCode("78759");
		orderInformation.billTo(orderInformationBillTo);

		Riskv1addressverificationsOrderInformationShipTo orderInformationShipTo = new Riskv1addressverificationsOrderInformationShipTo();
		orderInformationShipTo.address1("1715 oaks apt # 7");
		orderInformationShipTo.address2(" ");
		orderInformationShipTo.address3("");
		orderInformationShipTo.address4("");
		orderInformationShipTo.administrativeArea("WI");
		orderInformationShipTo.country("US");
		orderInformationShipTo.locality("SUPERIOR");
		orderInformationShipTo.postalCode("29681");
		orderInformation.shipTo(orderInformationShipTo);


		List <Riskv1addressverificationsOrderInformationLineItems> lineItems =  new ArrayList <Riskv1addressverificationsOrderInformationLineItems>();
		Riskv1addressverificationsOrderInformationLineItems lineItems1 = new Riskv1addressverificationsOrderInformationLineItems();
		lineItems1.unitPrice("120.50");
		lineItems1.quantity(3);
		lineItems1.productSKU("9966223");
		lineItems1.productName("headset");
		lineItems1.productCode("electronic");
		lineItems.add(lineItems1);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		Riskv1addressverificationsBuyerInformation buyerInformation = new Riskv1addressverificationsBuyerInformation();
		buyerInformation.merchantCustomerId("ABCD");
		requestObj.buyerInformation(buyerInformation);

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
