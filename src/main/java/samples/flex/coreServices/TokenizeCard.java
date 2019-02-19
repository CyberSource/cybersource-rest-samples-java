package samples.flex.coreServices;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.flex.sdk.model.FlexToken;
import com.cybersource.flex.sdk.repackaged.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api.FlexTokenApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.FlexV1KeysPost200Response;
import Model.FlexV1TokensPost200Response;
import Model.Flexv1tokensCardInfo;
import Model.TokenizeRequest;
import samples.flex.noEncryptionKeyGeneration.KeyGenerationNoEnc;
import samples.flex.tokenization.VerifyToken;

public class TokenizeCard {
	private FlexV1TokensPost200Response response;
	
	private FlexV1KeysPost200Response keyResponse;
	
	private static Properties merchantProp;
	
	
	@SuppressWarnings("rawtypes")
	
	private  Map<?, ?> tokenMap = new HashMap();
	
	private TokenizeRequest request;

	private  TokenizeRequest getRequest() throws Exception {
		request = new TokenizeRequest();
		KeyGenerationNoEnc keyGenerationNoEnc=new KeyGenerationNoEnc();
		keyResponse = keyGenerationNoEnc.process();
		if(keyResponse!=null){
		request.keyId(keyResponse.getKeyId());
		}
		Flexv1tokensCardInfo cardInfo = new Flexv1tokensCardInfo();
		cardInfo.cardNumber("5555555555554444");
		cardInfo.cardExpirationMonth("03");
		cardInfo.cardExpirationYear("2031");
		cardInfo.cardType("002");
		request.cardInfo(cardInfo);
		
		return request;

	}

	public static void main(String args[]) throws Exception {
		TokenizeCard tokenizeCard=new TokenizeCard();
		tokenizeCard.process();
	}

	public  void process() throws Exception {
		String className=TokenizeCard.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient=null;
		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			FlexTokenApi tokenizationApi = new FlexTokenApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = tokenizationApi.tokenize(request);
			
			byte[] publicBytes = Base64.decode(keyResponse.getDer().getPublicKey());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			FlexToken flexTokenResponseBody = new FlexToken();
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
			
			VerifyToken verifyToken = new VerifyToken();
			verifyToken.verify(pubKey, tokenMap);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code" +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader()+ "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody()+ "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode()+ "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader()+ "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className+ "\n");
		}
	}


}
