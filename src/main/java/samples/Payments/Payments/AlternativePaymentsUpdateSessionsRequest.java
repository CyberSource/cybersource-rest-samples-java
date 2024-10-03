package samples.Payments.Payments;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.*;

public class AlternativePaymentsUpdateSessionsRequest {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String[] args) {
		run();
	}

	public static PtsV2PaymentsPost201Response2 run() {
		String id="";
	
		CreateSessionRequest requestObj = new CreateSessionRequest();

		Ptsv2refreshpaymentstatusidClientReferenceInformation clientReferenceInformation = new Ptsv2refreshpaymentstatusidClientReferenceInformation();
		clientReferenceInformation.code("TC0824-06");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentreferencesProcessingInformation processingInformation = new Ptsv2paymentreferencesProcessingInformation();

		List <String> actionList = new ArrayList <String>();
		actionList.add("AP_SESSIONS");
		processingInformation.actionList(actionList);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentreferencesPaymentInformation paymentInformation = new Ptsv2paymentreferencesPaymentInformation();
		Ptsv2paymentsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("invoice");
		Ptsv2paymentsPaymentInformationPaymentTypeMethod paymentInformationPaymentTypeMethod = new Ptsv2paymentsPaymentInformationPaymentTypeMethod();
		paymentInformationPaymentTypeMethod.name("KLARNA");
		paymentInformationPaymentType.method(paymentInformationPaymentTypeMethod);

		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentreferencesOrderInformation orderInformation = new Ptsv2paymentreferencesOrderInformation();
		Ptsv2paymentreferencesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentreferencesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("1999.99");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		PtsV2PaymentsPost201Response2 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.updateSessionReq(requestObj, id);

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