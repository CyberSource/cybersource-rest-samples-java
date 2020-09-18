package samples.FlexMicroform;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.KeyGenerationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Model.FlexV1KeysPost200Response;
import Model.GeneratePublicKeyRequest;

public class GenerateKeyLegacyTokenFormat {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static FlexV1KeysPost200Response run() {
	
		GeneratePublicKeyRequest requestObj = new GeneratePublicKeyRequest();

		requestObj.encryptionType("None");
		requestObj.targetOrigin("https://www.test.com");
		String format = "legacy";

		FlexV1KeysPost200Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			KeyGenerationApi apiInstance = new KeyGenerationApi(apiClient);
			result = apiInstance.generatePublicKey(format, requestObj);

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
