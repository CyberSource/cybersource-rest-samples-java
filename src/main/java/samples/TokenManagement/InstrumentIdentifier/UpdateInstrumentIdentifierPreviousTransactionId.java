package samples.TokenManagement.InstrumentIdentifier;

import java.*;
import java.lang.invoke.MethodHandles;
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

public class UpdateInstrumentIdentifierPreviousTransactionId {
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
	public static TmsEmbeddedInstrumentIdentifier run() {
	
		String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
		String instrumentIdentifierTokenId = "7010000000016241111";
		
		PatchInstrumentIdentifierRequest requestObj = new PatchInstrumentIdentifierRequest();

		TmsEmbeddedInstrumentIdentifierProcessingInformation processingInformation = new TmsEmbeddedInstrumentIdentifierProcessingInformation();
		TmsAuthorizationOptions processingInformationAuthorizationOptions = new TmsAuthorizationOptions();
		TmsAuthorizationOptionsInitiator processingInformationAuthorizationOptionsInitiator = new TmsAuthorizationOptionsInitiator();
		TmsAuthorizationOptionsInitiatorMerchantInitiatedTransaction processingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction = new TmsAuthorizationOptionsInitiatorMerchantInitiatedTransaction();
		processingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction.previousTransactionId("123456789012345");
		processingInformationAuthorizationOptionsInitiator.merchantInitiatedTransaction(processingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction);

		processingInformationAuthorizationOptions.initiator(processingInformationAuthorizationOptionsInitiator);

		processingInformation.authorizationOptions(processingInformationAuthorizationOptions);

		requestObj.processingInformation(processingInformation);

		TmsEmbeddedInstrumentIdentifier result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
			result = apiInstance.patchInstrumentIdentifier(instrumentIdentifierTokenId, requestObj, profileid, null);

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
