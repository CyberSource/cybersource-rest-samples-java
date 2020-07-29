package samples.ValueAddedService;

import java.*;
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

public class TaxRefundRequest {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static VasV2PaymentsPost201Response run() {
	
		TaxRequest requestObj = new TaxRequest();

		Vasv2taxClientReferenceInformation clientReferenceInformation = new Vasv2taxClientReferenceInformation();
		clientReferenceInformation.code("TAX_TC001");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Vasv2taxTaxInformation taxInformation = new Vasv2taxTaxInformation();
		taxInformation.showTaxPerLineItem("Yes");
		taxInformation.refundIndicator(true);
		requestObj.taxInformation(taxInformation);

		Vasv2taxOrderInformation orderInformation = new Vasv2taxOrderInformation();
		RiskV1DecisionsPost201ResponseOrderInformationAmountDetails orderInformationAmountDetails = new RiskV1DecisionsPost201ResponseOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Vasv2taxOrderInformationBillTo orderInformationBillTo = new Vasv2taxOrderInformationBillTo();
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.locality("San Francisco");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94105");
		orderInformationBillTo.country("US");
		orderInformation.billTo(orderInformationBillTo);

		Vasv2taxOrderInformationShippingDetails orderInformationShippingDetails = new Vasv2taxOrderInformationShippingDetails();
		orderInformationShippingDetails.shipFromLocality("Cambridge Bay");
		orderInformationShippingDetails.shipFromCountry("CA");
		orderInformationShippingDetails.shipFromPostalCode("A0G 1T0");
		orderInformationShippingDetails.shipFromAdministrativeArea("NL");
		orderInformation.shippingDetails(orderInformationShippingDetails);

		Vasv2taxOrderInformationShipTo orderInformationShipTo = new Vasv2taxOrderInformationShipTo();
		orderInformationShipTo.country("US");
		orderInformationShipTo.administrativeArea("FL");
		orderInformationShipTo.locality("Panama City");
		orderInformationShipTo.postalCode("32401");
		orderInformationShipTo.address1("123 Russel St.");
		orderInformation.shipTo(orderInformationShipTo);


		List <Vasv2taxOrderInformationLineItems> lineItems =  new ArrayList <Vasv2taxOrderInformationLineItems>();
		Vasv2taxOrderInformationLineItems lineItems1 = new Vasv2taxOrderInformationLineItems();
		lineItems1.productSKU("07-12-00657");
		lineItems1.productCode("50161815");
		lineItems1.quantity(1);
		lineItems1.productName("Chewing Gum");
		lineItems1.unitPrice("1200");
		lineItems.add(lineItems1);

		Vasv2taxOrderInformationLineItems lineItems2 = new Vasv2taxOrderInformationLineItems();
		lineItems2.productSKU("07-12-00659");
		lineItems2.productCode("50181905");
		lineItems2.quantity(1);
		lineItems2.productName("Sugar Cookies");
		lineItems2.unitPrice("1240");
		lineItems.add(lineItems2);

		Vasv2taxOrderInformationLineItems lineItems3 = new Vasv2taxOrderInformationLineItems();
		lineItems3.productSKU("07-12-00658");
		lineItems3.productCode("5020.11");
		lineItems3.quantity(1);
		lineItems3.productName("Carbonated Water");
		lineItems3.unitPrice("9001");
		lineItems.add(lineItems3);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		Vasv2taxMerchantInformation merchantInformation = new Vasv2taxMerchantInformation();
		merchantInformation.vatRegistrationNumber("abcdef");
		requestObj.merchantInformation(merchantInformation);

		VasV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			TaxesApi apiInstance = new TaxesApi(apiClient);
			result = apiInstance.calculateTax(requestObj);

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
}
