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

import Api.TokenizationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.FlexV1KeysPost200Response;
import Model.FlexV1TokensPost200Response;
import Model.Flexv1tokensCardInfo;
import Model.TokenizeRequest;
import samples.flex.noEncryptionKeyGeneration.KeyGenerationNoEnc;
import utilities.flex.tokenverification.TokenVerificationUtility;

public class TokenizeCard {
	private static String status = null;
	private static String responseCode;
	public static FlexV1TokensPost200Response response  = null;
	
	public static FlexV1KeysPost200Response keyResponse;
	private static Properties merchantProp;
	
	
	@SuppressWarnings("rawtypes")
	
	static Map<?, ?> tokenMap = new HashMap();
	
	static TokenizeRequest request;

	private static TokenizeRequest getRequest() throws Exception {
		request = new TokenizeRequest();
		keyResponse = KeyGenerationNoEnc.process();
		request.keyId(keyResponse.getKeyId());
		
		Flexv1tokensCardInfo cardInfo = new Flexv1tokensCardInfo();
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

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;	
			
			TokenizationApi tokenizationApi = new TokenizationApi(apiClient);
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
			
			TokenVerificationUtility tokenVerifier = new TokenVerificationUtility();
			boolean isTokenVerified = tokenVerifier.verifyToken(pubKey, tokenMap);
			
			responseCode = apiClient.responseCode;
			status = apiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println("ResponseBody :"+apiClient.respBody);
			System.out.println("Token Verification : " + isTokenVerified);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
