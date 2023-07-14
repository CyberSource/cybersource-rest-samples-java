package samples.Payments.Credit;

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

public class EBTMerchandiseReturnCreditVoucherFromSNAP {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static PtsV2CreditsPost201Response run() {
	
		CreateCreditRequest requestObj = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("Merchandise Return / Credit Voucher from SNAP");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2creditsProcessingInformation processingInformation = new Ptsv2creditsProcessingInformation();
		processingInformation.commerceIndicator("retail");
		Ptsv2creditsProcessingInformationPurchaseOptions processingInformationPurchaseOptions = new Ptsv2creditsProcessingInformationPurchaseOptions();
		processingInformationPurchaseOptions.isElectronicBenefitsTransfer(true);
		processingInformation.purchaseOptions(processingInformationPurchaseOptions);

		Ptsv2creditsProcessingInformationElectronicBenefitsTransfer processingInformationElectronicBenefitsTransfer = new Ptsv2creditsProcessingInformationElectronicBenefitsTransfer();
		processingInformationElectronicBenefitsTransfer.category("FOOD");
		processingInformation.electronicBenefitsTransfer(processingInformationElectronicBenefitsTransfer);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		Ptsv2paymentsidrefundsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsidrefundsPaymentInformationCard();
		paymentInformationCard.type("001");
		paymentInformation.card(paymentInformationCard);

		Ptsv2paymentsidrefundsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsidrefundsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CARD");
		paymentInformationPaymentType.subTypeName("DEBIT");
		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("204.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsidrefundsMerchantInformation merchantInformation = new Ptsv2paymentsidrefundsMerchantInformation();
		merchantInformation.categoryCode(5411);
		requestObj.merchantInformation(merchantInformation);

		Ptsv2paymentsPointOfSaleInformation pointOfSaleInformation = new Ptsv2paymentsPointOfSaleInformation();
		pointOfSaleInformation.entryMode("swiped");
		pointOfSaleInformation.terminalCapability(4);
		pointOfSaleInformation.trackData("%B4111111111111111^JONES/JONES ^3112101976110000868000000?;4111111111111111=16121019761186800000?");
		pointOfSaleInformation.pinBlockEncodingFormat(1);
		pointOfSaleInformation.encryptedPin("52F20658C04DB351");
		pointOfSaleInformation.encryptedKeySerialNumber("FFFF1B1D140000000005");
		requestObj.pointOfSaleInformation(pointOfSaleInformation);

		PtsV2CreditsPost201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreditApi apiInstance = new CreditApi(apiClient);
			result = apiInstance.createCredit(requestObj);

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