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

public class CreatePaymentOrderRequest {
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

	public static PtsV2PaymentsOrderPost201Response run() {
		String id="";
	
		OrderPaymentRequest requestObj = new OrderPaymentRequest();

		PtsV2IncrementalAuthorizationPatch201ResponseClientReferenceInformation clientReferenceInformation = new PtsV2IncrementalAuthorizationPatch201ResponseClientReferenceInformation();
		clientReferenceInformation.code("TC0824-06");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentreferencesidintentsProcessingInformation processingInformation = new Ptsv2paymentreferencesidintentsProcessingInformation();

		List <String> actionList = new ArrayList <String>();
		actionList.add("AP_ORDER");
		processingInformation.actionList(actionList);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentreferencesidintentsPaymentInformation paymentInformation = new Ptsv2paymentreferencesidintentsPaymentInformation();
		Ptsv2paymentsidreversalsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsidreversalsPaymentInformationPaymentType();
		Ptsv2paymentsidreversalsPaymentInformationPaymentTypeMethod paymentInformationPaymentTypeMethod = new Ptsv2paymentsidreversalsPaymentInformationPaymentTypeMethod();
		paymentInformationPaymentTypeMethod.name("PAYPAL");
		paymentInformationPaymentType.method(paymentInformationPaymentTypeMethod);

		paymentInformation.paymentType(paymentInformationPaymentType);

		Ptsv2paymentreferencesidintentsPaymentInformationEWallet paymentInformationEWallet = new Ptsv2paymentreferencesidintentsPaymentInformationEWallet();
		paymentInformationEWallet.accountId("XX00XX00XX");
		paymentInformation.eWallet(paymentInformationEWallet);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentreferencesidintentsOrderInformation orderInformation = new Ptsv2paymentreferencesidintentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetailsOrder orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetailsOrder();
		orderInformationAmountDetails.totalAmount("100");
		orderInformationAmountDetails.currency("GBP");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		PtsV2PaymentsOrderPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createOrderRequest(requestObj, id);

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