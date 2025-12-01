package samples.AccountUpdater;

import Api.BatchesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Accountupdaterv1batchesIncluded;
import Model.Accountupdaterv1batchesIncludedTokens;
import Model.Body;
import Model.InlineResponse202;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AmexRegistrationInstrumentIdentifierTokenBatch {
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

	public static InlineResponse202 run() {
	
		Body requestObj = new Body();

		requestObj.type("amexRegistration");
		Accountupdaterv1batchesIncluded included = new Accountupdaterv1batchesIncluded();

		List <Accountupdaterv1batchesIncludedTokens> tokens = new ArrayList <Accountupdaterv1batchesIncludedTokens>();
		Accountupdaterv1batchesIncludedTokens tokens1 = new Accountupdaterv1batchesIncludedTokens();
		tokens1.id("7030000000000260224");
		tokens1.expirationMonth("12");
		tokens1.expirationYear("2020");
		tokens.add(tokens1);

		Accountupdaterv1batchesIncludedTokens tokens2 = new Accountupdaterv1batchesIncludedTokens();
		tokens2.id("7030000000000231118");
		tokens2.expirationMonth("12");
		tokens2.expirationYear("2020");
		tokens.add(tokens2);

		included.tokens(tokens);

		requestObj.included(included);

		requestObj.merchantReference("TC50171_3");
		requestObj.notificationEmail("test@cybs.com");
		InlineResponse202 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			BatchesApi apiInstance = new BatchesApi(apiClient);
			result = apiInstance.postBatch(requestObj);

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
