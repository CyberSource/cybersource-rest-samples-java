package samples.flex.coreServices;

import Api.KeyGenerationApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.GeneratePublicKeyRequest;
import Model.InlineResponse200;

public class GenerateKey {
	private static String status=null;
	private static String responseCode;
	public static InlineResponse200 response=null;

	static GeneratePublicKeyRequest request;

	private static GeneratePublicKeyRequest getRequest() {
		request = new GeneratePublicKeyRequest();
		request.encryptionType("RsaOaep256");
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	
	public static InlineResponse200 process() throws Exception {

		try {
			request = getRequest();

			KeyGenerationApi keyGenerationApi = new KeyGenerationApi();
			response=keyGenerationApi.generatePublicKey(request);

			responseCode=ApiClient.responseCode;
			status=ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getKeyId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}


}
