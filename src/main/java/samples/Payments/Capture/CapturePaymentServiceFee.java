package samples.Payments.Capture;

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

public class CapturePaymentServiceFee {
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

	public static PtsV2PaymentsCapturesPost201Response run() {
		String id = "";
		CapturePaymentRequest requestObj = new CapturePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidcapturesOrderInformation orderInformation = new Ptsv2paymentsidcapturesOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("2325.00");
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.serviceFeeAmount("30.0");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsidcapturesMerchantInformation merchantInformation = new Ptsv2paymentsidcapturesMerchantInformation();
		Ptsv2paymentsMerchantInformationServiceFeeDescriptor merchantInformationServiceFeeDescriptor = new Ptsv2paymentsMerchantInformationServiceFeeDescriptor();
		merchantInformationServiceFeeDescriptor.name("Vacations Service Fee");
		merchantInformationServiceFeeDescriptor.contact("8009999999");
		merchantInformationServiceFeeDescriptor.state("CA");
		merchantInformation.serviceFeeDescriptor(merchantInformationServiceFeeDescriptor);

		requestObj.merchantInformation(merchantInformation);

		PtsV2PaymentsCapturesPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CaptureApi apiInstance = new CaptureApi(apiClient);
			result = apiInstance.capturePayment(requestObj, id);

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