package samples.flex.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.KeyGenerationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.FlexV1KeysPost200Response ;
import Model.GeneratePublicKeyRequest;

public class GenerateKey {
	private static String status=null;
	private static String responseCode;
	public static FlexV1KeysPost200Response  response=null;
	private static Properties merchantProp;

	static GeneratePublicKeyRequest request;

	private static GeneratePublicKeyRequest getRequest() {
		request = new GeneratePublicKeyRequest();
		request.encryptionType("RsaOaep256");
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	
	public static FlexV1KeysPost200Response  process() throws Exception {

		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;	
			
			KeyGenerationApi keyGenerationApi = new KeyGenerationApi(apiClient);
			response = keyGenerationApi.generatePublicKey(request, null);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}


}
