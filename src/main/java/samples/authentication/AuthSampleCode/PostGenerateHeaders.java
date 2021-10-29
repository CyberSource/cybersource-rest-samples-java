package samples.authentication.AuthSampleCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cybersource.authsdk.core.Authorization;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.payloaddigest.PayloadDigest;
import com.cybersource.authsdk.util.GlobalLabelParameters;
import samples.authentication.harness.MerchantProperties;

/**
 * This class generates the Headers that are present in the token was sent the
 * server for transaction, for POST operation.
 *
 */
public class PostGenerateHeaders {
	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private String authenticationType = null;
	private Authorization auth;
	private PayloadDigest digest;
	private Logger logger = null;
	private String tempSig;
	
	/**
	 * DATE GMT [Non - Editable]
	 * 
	 */
	private String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
	
	/**
	 * REQUEST - TYPE [Non-Editable]
	 * 
	 */
	private String requestType = "POST";

	private String requestJson = "{\r\n" + 
			"  \"clientReferenceInformation\": {\r\n" + 
			"    \"code\": \"TC50171_3\"\r\n" + 
			"  },\r\n" + 
			"  \"processingInformation\": {\r\n" + 
			"    \"commerceIndicator\": \"internet\"\r\n" + 
			"  },\r\n" + 
			"  \"aggregatorInformation\": {\r\n" + 
			"    \"subMerchant\": {\r\n" + 
			"      \"cardAcceptorID\": \"1234567890\",\r\n" + 
			"      \"country\": \"US\",\r\n" + 
			"      \"phoneNumber\": \"650-432-0000\",\r\n" + 
			"      \"address1\": \"900 Metro Center\",\r\n" + 
			"      \"postalCode\": \"94404-2775\",\r\n" + 
			"      \"locality\": \"Foster City\",\r\n" + 
			"      \"name\": \"Visa Inc\",\r\n" + 
			"      \"administrativeArea\": \"CA\",\r\n" + 
			"      \"region\": \"PEN\",\r\n" + 
			"      \"email\": \"test@cybs.com\"\r\n" + 
			"    },\r\n" + 
			"    \"name\": \"V-Internatio\",\r\n" + 
			"    \"aggregatorID\": \"123456789\"\r\n" + 
			"  },\r\n" + 
			"  \"orderInformation\": {\r\n" + 
			"    \"billTo\": {\r\n" + 
			"      \"country\": \"US\",\r\n" + 
			"      \"lastName\": \"VDP\",\r\n" + 
			"      \"address2\": \"Address 2\",\r\n" + 
			"      \"address1\": \"201 S. Division St.\",\r\n" + 
			"      \"postalCode\": \"48104-2201\",\r\n" + 
			"      \"locality\": \"Ann Arbor\",\r\n" + 
			"      \"administrativeArea\": \"MI\",\r\n" + 
			"      \"firstName\": \"RTS\",\r\n" + 
			"      \"phoneNumber\": \"999999999\",\r\n" + 
			"      \"district\": \"MI\",\r\n" + 
			"      \"buildingNumber\": \"123\",\r\n" + 
			"      \"company\": \"Visa\",\r\n" + 
			"      \"email\": \"test@cybs.com\"\r\n" + 
			"    },\r\n" + 
			"    \"amountDetails\": {\r\n" + 
			"      \"totalAmount\": \"102.21\",\r\n" + 
			"      \"currency\": \"USD\"\r\n" + 
			"    }\r\n" + 
			"  },\r\n" + 
			"  \"paymentInformation\": {\r\n" + 
			"    \"card\": {\r\n" + 
			"      \"expirationYear\": \"2031\",\r\n" + 
			"      \"number\": \"5555555555554444\",\r\n" + 
			"      \"securityCode\": \"123\",\r\n" + 
			"      \"expirationMonth\": \"12\",\r\n" + 
			"      \"type\": \"002\"\r\n" + 
			"    }\r\n" + 
			"  }\r\n" + 
			"}";

	/**
	 * 
	 * @throws Exception
	 *             : Error while generating headers.
	 */
	public PostGenerateHeaders() throws Exception {
		merchantProp = MerchantProperties.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		this.logger = LogManager.getLogger(getClass());
		System.out.println("Authentication Type : " +  merchantConfig.getAuthenticationType().trim());
		this.logger.info(GlobalLabelParameters.BEGIN_TRANSACTION + "\n******************* Generate POST Headers *******************");

		merchantConfig.setRequestType(requestType);
		merchantConfig.setRequestData(requestJson);
		
		digest = new PayloadDigest(this.merchantConfig);
		boolean isMerchant = merchantConfig.validateMerchantDetails();
		
		auth = new Authorization();
		digest = new PayloadDigest(merchantConfig);
		if (isMerchant) {
			generatePostHeaders();
		}

		this.logger.info(GlobalLabelParameters.END_TRANSACTION + "\n");
	}

	/**
	 * 
	 * @param merchantConfig
	 *            : this object contains all the cybs.properites details.
	 * @throws Exception
	 */
	public PostGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		this.merchantConfig = merchantConfig;
		auth = new Authorization();
		logger = LogManager.getLogger(getClass());
		digest = new PayloadDigest(this.merchantConfig);
		generatePostHeaders();
	}

	/**
	 * This method generates, displays and log the detailed header truncation
	 * for POST operation.
	 * 
	 * @throws Exception
	 */
	private void generatePostHeaders() throws Exception {
		authenticationType = merchantConfig.getAuthenticationType().trim();
		
		this.logger.info(GlobalLabelParameters.AUTENTICATION_TYPE + authenticationType);
		this.logger.info(GlobalLabelParameters.REQUEST_TYPE + " : " + requestType);
		this.logger.info(GlobalLabelParameters.DATE + " : " + date);
		this.logger.info(GlobalLabelParameters.V_C_MERCHANTID + " : " + merchantConfig.getMerchantID().trim());
		this.logger.info(GlobalLabelParameters.CONTENT_TYPE + " : " + GlobalLabelParameters.APPLICATION_JSON);
		this.logger.info(GlobalLabelParameters.HOST + " : " + merchantConfig.getRequestHost());
		
		System.out.println(GlobalLabelParameters.CONTENT_TYPE + "        : " + GlobalLabelParameters.APPLICATION_JSON);
		System.out.println("MerchantID          : " + merchantConfig.getMerchantID().trim());
		System.out.println("Date                : " + date);
		System.out.println("Request Type        : " + requestType);
		System.out.println("HOST                : " + merchantConfig.getRequestHost());
		
		if (authenticationType.equalsIgnoreCase(GlobalLabelParameters.HTTP)) {
			System.out.println(GlobalLabelParameters.USERAGENT + "          : " + GlobalLabelParameters.USER_AGENT_VALUE);
			this.logger.info(GlobalLabelParameters.USERAGENT + " : " + GlobalLabelParameters.USER_AGENT_VALUE);
			
			System.out.println("Digest              : " + digest.getDigest());
			
			tempSig = auth.getToken(merchantConfig);
			System.out.println("Signature           : " + tempSig.toString());
			
		} else {
			auth.setJWTRequestBody(requestJson);
			tempSig = auth.getToken(merchantConfig);
			System.out.println("Authorization, Bearer " + tempSig.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		new PostGenerateHeaders();
	}
}
