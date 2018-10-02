package flex.tokenization;

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
import flex.noEncryptionKeyGeneration.KeyGenerationNoEnc;

public class TokenizeCard {
	private String responseCode = null;
	private String responseMsg = null;
	public static InlineResponse2001 response =null;
	
	KeyGenerationNoEnc enc=new KeyGenerationNoEnc();
	public InlineResponse200 keyResponse=enc.process();
	
	/*KeyGeneration keyGeneration=new KeyGeneration();
	public InlineResponse200 keyResponse=keyGeneration.process();*/
	
	@SuppressWarnings("rawtypes")
	Map<?, ?> tokenMap=new HashMap();
	
	TokenizeRequest request;

	private TokenizeRequest getRequest() {
		request = new TokenizeRequest();
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
		new TokenizeCard();
	}

	public TokenizeCard() throws Exception {
		process();
	}

	private void process() throws Exception {

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
			
			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
