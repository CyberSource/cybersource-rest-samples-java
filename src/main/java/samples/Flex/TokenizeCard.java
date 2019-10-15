package samples.Flex;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class TokenizeCard {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
*/
	public static FlexV1TokensPost200Response run() {
	
		TokenizeRequest requestObj = new TokenizeRequest();

		requestObj.keyId("08z9hCmn4pRpdNhPJBEYR3Mc2DGLWq5j");
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

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
