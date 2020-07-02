package samples.FlexMicroform;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api.*;

import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import samples.FlexMicroform.GenerateKey;
import utilities.flex.tokenverification.FlexToken;
import utilities.flex.tokenverification.TokenVerificationUtility;
import utilities.flex.security.Base64;

public class FlexTokenizeCard {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static FlexV1TokensPost200Response run() {
	
		FlexV1KeysPost200Response keyResponse = GenerateKey.run();
		TokenizeRequest requestObj = new TokenizeRequest();
		requestObj.keyId(keyResponse.getKeyId());

		Flexv1tokensCardInfo cardInfo = new Flexv1tokensCardInfo();
		cardInfo.cardNumber("4111111111111111");
		cardInfo.cardExpirationMonth("12");
		cardInfo.cardExpirationYear("2031");
		cardInfo.cardType("001");
		requestObj.cardInfo(cardInfo);

		FlexV1TokensPost200Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			TokenizationApi apiInstance = new TokenizationApi(apiClient);
			result = apiInstance.tokenize(requestObj);

			byte[] publicBytes = Base64.decode(keyResponse.getDer().getPublicKey());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);


			FlexToken flexTokenResponseBody = new FlexToken();
			flexTokenResponseBody.setKeyId(result.getKeyId());
			flexTokenResponseBody.setToken(result.getToken());
			flexTokenResponseBody.setMaskedPan(result.getMaskedPan());
			flexTokenResponseBody.setCardType(result.getCardType());
			flexTokenResponseBody.setTimestamp(result.getTimestamp());
			flexTokenResponseBody.setSignedFields(result.getSignedFields());
			flexTokenResponseBody.setSignature(result.getSignature());
			flexTokenResponseBody.setDiscoverableServices(result.getDiscoverableServices());

			ObjectMapper oMapper = new ObjectMapper();
			Map tokenMap = oMapper.convertValue(flexTokenResponseBody, Map.class);

			TokenVerificationUtility tokenVerifier = new TokenVerificationUtility();
			boolean isTokenVerified = tokenVerifier.verifyToken(pubKey, tokenMap);

			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			System.out.println("Token Verification : " + isTokenVerified);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
