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

public class PaymentWithFlexToken {
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

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("102.21");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("RTS");
		orderInformationBillTo.lastName("VDP");
		orderInformationBillTo.address1("201 S. Division St.");
		orderInformationBillTo.locality("Ann Arbor");
		orderInformationBillTo.administrativeArea("MI");
		orderInformationBillTo.postalCode("48104-2201");
		orderInformationBillTo.country("US");
		orderInformationBillTo.district("MI");
		orderInformationBillTo.buildingNumber("123");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.phoneNumber("999999999");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsTokenInformation tokenInformation = new Ptsv2paymentsTokenInformation();
		tokenInformation.transientTokenJwt("eyJraWQiOiIwOGNtNFp2emU5UVpqb00zZ2NlVlpaRGVPb0xma242ZiIsImFsZyI6IlJTMjU2In0.eyJkYXRhIjp7Im51bWJlciI6IjQxMTExMVhYWFhYWDExMTEiLCJ0eXBlIjoiMDAxIn0sImlzcyI6IkZsZXgvMDgiLCJleHAiOjE1OTU5MjAwNTksInR5cGUiOiJtZi0wLjExLjAiLCJpYXQiOjE1OTU5MTkxNTksImp0aSI6IjFFM1pRVVpKWlRLVVZJNkNNSVdSOFRUT0pDU0pVNTFUSTlHSFhaSkE3MTQwWlZVTFVGWDY1RjFGQ0VCQkIwMUIifQ.ZY5ZRntWhr0HXBm6sBB0JsXK0Nwt92gos74V9HCDOgHcgBFgGNA-SVDG4o2pSXruMtlBkLKAN_xmRdx0wsFUyz_AKasLNbGBiNZiltgN1UJpRju54h2A91NzQhZdWTz69mpfLkD8bxiCxCUTvjgsrqDxVijm5ebmUlacVzbAOICZlLPR21IJv6pAUdKucW62-aH42hIqYaBwJJulDUjWAGCsBTxQF_j13s1aHtRWFYN9Ks5smAfiojIUqweT3zvjrylFJk_uoPw9v40ODp-8TiUjtY9Oz_XRGLdZgOEolA2zaB8itpVouK8-8ystCrQGakA8qbxHjcFIaoeiKapxDQ");
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
