package samples.flex.noEncryptionKeyGeneration;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.KeyGenerationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.FlexV1KeysPost200Response;
import Model.GeneratePublicKeyRequest;

public class KeyGenerationNoEnc {
	private  FlexV1KeysPost200Response response;
	private  Properties merchantProp;

	private  GeneratePublicKeyRequest request;

	private  GeneratePublicKeyRequest getRequest() {
		request = new GeneratePublicKeyRequest();
		request.encryptionType("None");
		return request;

	}

	public static void main(String args[]) throws Exception {
		KeyGenerationNoEnc keyGenerationNoEnc=new KeyGenerationNoEnc();
		keyGenerationNoEnc.process();
	}

	public  FlexV1KeysPost200Response process() throws Exception {
		String className=KeyGenerationNoEnc.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient=null;
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			KeyGenerationApi keyGenerationApi = new KeyGenerationApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = keyGenerationApi.generatePublicKey(request);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code" +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
		return response;
	}

}
