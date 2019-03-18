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
import com.cybersource.authsdk.util.GlobalLabelParameters;
import com.cybersource.authsdk.util.PropertiesUtil;
import com.cybersource.authsdk.util.Utility;
/**
 * This class generates the Headers that are present in the token was sent the server for transaction,
 * Delete operations.
 *  *
 */
public class DeleteGenerateHeaders {

	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private String authenticationType = null;
	private Authorization auth;
	private static String requestType = "DELETE";
	private String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
	Logger logger;

	private String tempSig;

	/**
	 * 
	 * @param getID
	 *            : Unique getID
	 * @throws Exception
	 *             :
	 */
	public DeleteGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		merchantConfig.setEnableLog(false);
		logger = Log4j.getInstance(merchantConfig);
		merchantConfig.validateMerchantDetails(logger);
		auth = new Authorization();
		generateDeleteHeaders(merchantConfig);
	}

	public DeleteGenerateHeaders() throws Exception {
		auth = new Authorization();
		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		merchantConfig.setRequestType(requestType);
		
		logger = Log4j.getInstance(merchantConfig);
		Utility.log(this.logger,
				GlobalLabelParameters.BEGIN_TRANSCATION , " Delete Generate Headers *******************", Level.INFO);
		merchantConfig.validateMerchantDetails(logger);
		System.out.println(" Authentication Type : " +  merchantConfig.getAuthenticationType().trim());
		boolean isMerchant = merchantConfig.validateMerchantDetails(logger);
		if(isMerchant){
		generateDeleteHeaders(merchantConfig);
		}
		Utility.log(this.logger, GlobalLabelParameters.END_TRANSCATION,"", Level.INFO);
		Utility.log(this.logger, " ","", Level.OFF);
	}
	/**
	 * This method generates the detailed header detail of the transaction and displays and log them. 
	 * @throws Exception
	 */
	private void generateDeleteHeaders(MerchantConfig merchantConfig) throws Exception {
		authenticationType = merchantConfig.getAuthenticationType().trim();
		Utility.log(this.logger, GlobalLabelParameters.AUTENTICATION_TYPE ,authenticationType, Level.INFO);
		Utility.log(this.logger, GlobalLabelParameters.REQUEST_TYPE .concat(" : ") , requestType, Level.INFO);
		Utility.log(logger, GlobalLabelParameters.DATE .concat(" : ") , date, Level.INFO);
		Utility.log(logger, GlobalLabelParameters.HOST .concat(" : ") , merchantConfig.getRequestHost(),
				Level.INFO);
		Utility.log(logger,
				GlobalLabelParameters.V_C_MERCHANTID .concat(" : ") , merchantConfig.getMerchantID().trim(), Level.INFO);
		
		System.out.println(" Request Type        : " + requestType);
		System.out.println(" Date                : " + date);
		System.out.println(" MerchantID          : " + merchantConfig.getMerchantID().trim());
		System.out.println(" HOST                : " + merchantConfig.getRequestHost());
		
		if (authenticationType.equalsIgnoreCase(GlobalLabelParameters.HTTP)) {
			System.out.println(
					" " + GlobalLabelParameters.USERAGENT + "          : " + GlobalLabelParameters.USER_AGENT_VALUE);
			Utility.log(logger,
					GlobalLabelParameters.USERAGENT .concat(" : ") , GlobalLabelParameters.USER_AGENT_VALUE, Level.INFO);
			/* Signature Header */
			tempSig = auth.getToken(merchantConfig);
			System.out.println(" Signature           : " + tempSig.toString());
			
		} else {
			/* JWT */
			String jwtRequestBody = null;
			auth.setJWTRequestBody(jwtRequestBody);
			auth.setLogger(this.logger);
			tempSig = auth.getToken(merchantConfig);
			System.out.println(" Authorization, Bearer " + tempSig.toString());
		}
		System.out.println(
				" " + GlobalLabelParameters.CONTENT_TYPE + "        : " + GlobalLabelParameters.APPLICATION_JSON);
		Utility.log(logger,
				GlobalLabelParameters.CONTENT_TYPE .concat(" : "), GlobalLabelParameters.APPLICATION_JSON, Level.INFO);
	}

	/**
	 * 
	 * @param args : Contains getId entry.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		new DeleteGenerateHeaders();
	}
}
