package samples.authentication.AuthSampleCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.cybersource.authsdk.core.Authorization;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.log.Log4j;
import com.cybersource.authsdk.payloaddigest.PayloadDigest;
import com.cybersource.authsdk.util.GlobalLabelParameters;
import com.cybersource.authsdk.util.PropertiesUtil;
import com.cybersource.authsdk.util.Utility;

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
	/**
	 * JSON PATH
	 * [Editable]
	 */

	private String requestJsonPath = "src/main/resources/authRequest.json";

	/**
	 * 
	 * @throws Exception
	 *             : Error while generating headers.
	 */
	public PostGenerateHeaders() throws Exception {
		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		this.logger = Log4j.getInstance(merchantConfig);
		Utility.logEnable = true;
		System.out.println(" Authentication Type : " +  merchantConfig.getAuthenticationType().trim());
		Utility.log(this.logger,
				GlobalLabelParameters.BEGIN_TRANSCATION , " Generate POST Headers *******************", Level.INFO);
		merchantConfig.setRequestType(requestType);
		merchantConfig.setRequestJsonPath(requestJsonPath);
		digest = new PayloadDigest(this.merchantConfig);
		boolean isMerchant = merchantConfig.validateMerchantDetails(logger);
		Utility.logEnable = merchantConfig.getEnableLog();
		auth = new Authorization();
		digest = new PayloadDigest(merchantConfig);
		if (isMerchant) {
			generatePostHeaders();
		}
		Utility.log(this.logger, GlobalLabelParameters.END_TRANSCATION,"", Level.INFO);
		Utility.log(this.logger, " ","", Level.OFF);
	}

	/**
	 * 
	 * @param merchantConfig
	 *            : this object contains all the cybs.properites details.
	 * @throws Exception
	 */
	public PostGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		this.merchantConfig = merchantConfig;
		Utility.logEnable = false;
		auth = new Authorization();
		logger = Log4j.getInstance(merchantConfig);
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

		Utility.log(this.logger, GlobalLabelParameters.AUTENTICATION_TYPE , authenticationType, Level.INFO);
		Utility.log(this.logger, GlobalLabelParameters.REQUEST_TYPE .concat(" : ") , requestType, Level.INFO);
		Utility.log(logger, GlobalLabelParameters.DATE .concat(" : ") , date, Level.INFO);
		Utility.log(logger,
				GlobalLabelParameters.V_C_MERCHANTID .concat(" : ") , merchantConfig.getMerchantID().trim(), Level.INFO);
		Utility.log(logger,
				GlobalLabelParameters.CONTENT_TYPE .concat(" : "), GlobalLabelParameters.APPLICATION_JSON, Level.INFO);
		System.out.println(
				" " + GlobalLabelParameters.CONTENT_TYPE + "           : " + GlobalLabelParameters.APPLICATION_JSON);
		Utility.log(logger, GlobalLabelParameters.HOST .concat(" : ") , merchantConfig.getRequestHost(),
				Level.INFO);
		
		System.out.println(" MerchantID          : " + merchantConfig.getMerchantID().trim());
		System.out.println(" Date                : " + date);
		System.out.println(" Request Type        : " + requestType);
		System.out.println(" HOST                : " + merchantConfig.getRequestHost());
		
		if (authenticationType.equalsIgnoreCase(GlobalLabelParameters.HTTP)) {
			System.out.println(
					" " + GlobalLabelParameters.USERAGENT + "          : " + GlobalLabelParameters.USER_AGENT_VALUE);
			Utility.log(logger,
					GlobalLabelParameters.USERAGENT .concat(" : ") , GlobalLabelParameters.USER_AGENT_VALUE, Level.INFO);
			
			System.out.println(" Digest              : " + digest.getDigest());
			/* Signature Header */
			tempSig = auth.getToken(merchantConfig);
			System.out.println(" Signature           : " + tempSig.toString());
			
		} else {
			/* JWT */
			
			String jwtRequestBody = PropertiesUtil.getJsonInput(merchantConfig.getRequestJsonPath());
			auth.setJWTRequestBody(jwtRequestBody);
			auth.setLogger(this.logger);
			tempSig = auth.getToken(merchantConfig);
			System.out.println("Authorization, Bearer " + tempSig.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		new PostGenerateHeaders();
	}
}
