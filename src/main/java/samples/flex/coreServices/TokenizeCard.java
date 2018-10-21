package samples.flex.coreServices;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.cybersource.flex.sdk.model.FlexToken;
import com.cybersource.flex.sdk.repackaged.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api.TokenizationApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse200;
import Model.InlineResponse2001;
import Model.Paymentsflexv1tokensCardInfo;
import Model.TokenizeRequest;
import samples.flex.noEncryptionKeyGeneration.KeyGenerationNoEnc;
import samples.flex.tokenization.VerifyToken;

public class TokenizeCard {
	private static String status=null;
	private static String responseCode;
	public static InlineResponse2001 response =null;
	
	public static InlineResponse200 keyResponse;
	
	
	@SuppressWarnings("rawtypes")
	
	static Map<?, ?> tokenMap=new HashMap();
	
	static TokenizeRequest request;

	private static TokenizeRequest getRequest() throws Exception {
		request = new TokenizeRequest();
		keyResponse=KeyGenerationNoEnc.process();
		request.keyId(keyResponse.getKeyId());
		
		Paymentsflexv1tokensCardInfo cardInfo=new Paymentsflexv1tokensCardInfo();
		cardInfo.cardNumber("5555555555554444");
		cardInfo.cardExpirationMonth("03");
		cardInfo.cardExpirationYear("2031");
		cardInfo.cardType("002");
		request.cardInfo(cardInfo);
		
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static void process() throws Exception {

		try {
			request = getRequest();

			TokenizationApi tokenizationApi = new TokenizationApi();
			response=tokenizationApi.tokenize(request);
			
			byte[] publicBytes = Base64.decode(keyResponse.getDer().getPublicKey());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			
			
			FlexToken flexTokenResponseBody=new FlexToken();
			flexTokenResponseBody.setKeyId(response.getKeyId());
			flexTokenResponseBody.setToken(response.getToken());
			flexTokenResponseBody.setMaskedPan(response.getMaskedPan());
			flexTokenResponseBody.setCardType(response.getCardType());
			flexTokenResponseBody.setTimestamp(response.getTimestamp());
			flexTokenResponseBody.setSignedFields(response.getSignedFields());
			flexTokenResponseBody.setSignature(response.getSignature());
			flexTokenResponseBody.setDiscoverableServices(response.getDiscoverableServices());
			
			ObjectMapper oMapper = new ObjectMapper();
			tokenMap = oMapper.convertValue(flexTokenResponseBody, Map.class);
			
			VerifyToken verifyToken=new VerifyToken();
			verifyToken.verify(pubKey, tokenMap);
			
			responseCode=ApiClient.responseCode;
			status=ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getKeyId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
