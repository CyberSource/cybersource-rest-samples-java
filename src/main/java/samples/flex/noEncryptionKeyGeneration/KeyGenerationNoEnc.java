package samples.flex.noEncryptionKeyGeneration;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.KeyGenerationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.GeneratePublicKeyRequest;
import Model.FlexV1KeysPost200Response;

public class KeyGenerationNoEnc {
	public static FlexV1KeysPost200Response response = null;
	public static String keyId = null;
	private static Properties merchantProp;

	static GeneratePublicKeyRequest request;

	private static GeneratePublicKeyRequest getRequest() {
		request = new GeneratePublicKeyRequest();
		request.encryptionType("None");
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static FlexV1KeysPost200Response process() throws Exception {

		try {
			request = getRequest();
			
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);
			
			KeyGenerationApi keyGenerationApi = new KeyGenerationApi();
			response = keyGenerationApi.generatePublicKey(request);

			System.out.println(ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
