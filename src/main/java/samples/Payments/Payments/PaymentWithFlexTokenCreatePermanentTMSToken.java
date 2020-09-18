package samples.Payments.Payments;

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

public class PaymentWithFlexTokenCreatePermanentTMSToken {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();

		List <String> actionList = new ArrayList <String>();
		actionList.add("TOKEN_CREATE");
		processingInformation.actionList(actionList);


		List <String> actionTokenTypes = new ArrayList <String>();
		actionTokenTypes.add("customer");
		actionTokenTypes.add("paymentInstrument");
		actionTokenTypes.add("shippingAddress");
		processingInformation.actionTokenTypes(actionTokenTypes);

		processingInformation.capture(false);
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("102.21");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94105");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformation.billTo(orderInformationBillTo);

		Ptsv2paymentsOrderInformationShipTo orderInformationShipTo = new Ptsv2paymentsOrderInformationShipTo();
		orderInformationShipTo.firstName("John");
		orderInformationShipTo.lastName("Doe");
		orderInformationShipTo.address1("1 Market St");
		orderInformationShipTo.locality("san francisco");
		orderInformationShipTo.administrativeArea("CA");
		orderInformationShipTo.postalCode("94105");
		orderInformationShipTo.country("US");
		orderInformation.shipTo(orderInformationShipTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsTokenInformation tokenInformation = new Ptsv2paymentsTokenInformation();
		tokenInformation.transientTokenJwt("eyJraWQiOiIwODVLd3ZiZHVqZW1DZDN3UnNxVFg3RG5nZzlZVk04NiIsImFsZyI6IlJTMjU2In0.eyJkYXRhIjp7Im51bWJlciI6IjQxMTExMVhYWFhYWDExMTEiLCJ0eXBlIjoiMDAxIn0sImlzcyI6IkZsZXgvMDgiLCJleHAiOjE1OTU2MjAxNTQsInR5cGUiOiJtZi0wLjExLjAiLCJpYXQiOjE1OTU2MTkyNTQsImp0aSI6IjFFMTlWWVlBUEFEUllPSzREUUM1NFhRN1hUVTlYN01YSzBCNTc5UFhCUU1HUUExVU1MOFI1RjFCM0IzQTU4MkIifQ.NKSM8zuT9TQC2OIUxIFJQk4HKeHhj_RGWmEqOQhBi0TIynt_kCIup1UVtAlhPzUfPWLwRrUVXnA9dyjLt_Q-pFZnvZ2lVANbiOq_R0us88MkM_mqaELuInCwxFeFZKA4gl8XmDFARgX1aJttC19Le6NYOhK2gpMrV4i0yz-IkbScsk0_vCH3raayNacFU2Wy9xei6H_V0yw2GeOs7kF6wdtMvBNw_uoLXd77LGE3LmV7z1TpJcG1SXy2s0bwYhEvkQGnrq6FfY8w7-UkDBWT1GhU3ZVP4y7h0l1WEX2xqf_ze25ZiYJQfWrEWPBHXRubOpAuaf4rfeZei0mRwPU-sQ");
		requestObj.tokenInformation(tokenInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

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
