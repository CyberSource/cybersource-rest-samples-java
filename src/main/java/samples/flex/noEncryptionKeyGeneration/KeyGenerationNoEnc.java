package samples.flex.noEncryptionKeyGeneration;

import Api.KeyGenerationApi;
import Invokers.ApiException;
import Model.GeneratePublicKeyRequest;
import Model.InlineResponse200;

public class KeyGenerationNoEnc {
	public static InlineResponse200 response = null;
	public static String keyId = null;

	static GeneratePublicKeyRequest request;

	private static GeneratePublicKeyRequest getRequest() {
		request = new GeneratePublicKeyRequest();
		request.encryptionType("None");
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static InlineResponse200 process() throws Exception {

		try {
			request = getRequest();

			KeyGenerationApi keyGenerationApi = new KeyGenerationApi();
			response = keyGenerationApi.generatePublicKey(request);

			System.out.println(response.getKeyId());
			System.out.println(response.toString());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
