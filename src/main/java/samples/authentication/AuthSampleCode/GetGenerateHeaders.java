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
 * GET operations.
 *  *
 */
public class GetGenerateHeaders {

	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private String authenticationType = null;
	private Authorization auth;
	private Logger logger;
	private String tempSig;
	/**
	 * DATE
	 * [Non-Editable]
	 */
	private String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
	/**
	 * REQUEST - TYPE
	 * [Non-Editable]
	 * 
	 */
	private String requestType = "GET";
	/**
	 * 
	 * UNQUIE GetID
	 * [Editable]
	 */
	private String getID = "5265502011846829204101";
	/**
	 * 
	 * @param getID
	 *            : Unique getID
	 * @throws Exception
	 *             :
	 */
	public GetGenerateHeaders(String requestTarget) throws Exception {
		setGenerateHeaders();
		Utility.logEnable = false;
		this.getID = Utility.retrieveGetIDFromRequestTarget(requestTarget);
		generateGetHeaders();
	}
	
	/**
	 * 
	 * @param merchantConfig
	 *            : this object contains all the cybs.properites details.
	 * @throws Exception
	 */
	public GetGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		this.merchantConfig = merchantConfig;
		Utility.logEnable = false;
		merchantConfig.setGetID(getID);
		merchantConfig.setRequestType(requestType);
		this.logger = Log4j.getInstance(merchantConfig);
		auth = new Authorization();
		generateGetHeaders();
		Utility.log(this.logger, GlobalLabelParameters.END_TRANSCATION,"", Level.INFO);
		Utility.log(this.logger, " ","", Level.OFF);

	}

	public GetGenerateHeaders() throws Exception {
		setGenerateHeaders();
		
		System.out.println(" Authentication Type : " +  merchantConfig.getAuthenticationType().trim());
		Utility.log(this.logger,
				GlobalLabelParameters.BEGIN_TRANSCATION , " Generate GET Headers *******************", Level.INFO);
		merchantConfig.validateMerchantDetails(logger);
		generateGetHeaders();
		Utility.log(this.logger, GlobalLabelParameters.END_TRANSCATION,"", Level.INFO);
		Utility.log(this.logger, " ","", Level.OFF);
	}

	private void setGenerateHeaders() throws Exception {
		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		authenticationType = merchantConfig.getAuthenticationType().trim();
		merchantConfig.setRequestType(requestType);
		this.logger = Log4j.getInstance(merchantConfig);
		auth = new Authorization();
	}

	/**
	 * This method generates the detailed header detail of the transaction and
	 * displays and log them.
	 * 
	 * @throws Exception
	 *             : Throws runtime exception.
	 */
	private void generateGetHeaders() throws Exception {

		authenticationType = merchantConfig.getAuthenticationType().trim();
		Utility.log(this.logger, GlobalLabelParameters.AUTENTICATION_TYPE , authenticationType, Level.INFO);
		Utility.log(this.logger, GlobalLabelParameters.REQUEST_TYPE.concat(" : ") , requestType, Level.INFO);
		Utility.log(logger,
				GlobalLabelParameters.V_C_MERCHANTID .concat(" : ") , merchantConfig.getMerchantID().trim(), Level.INFO);
		Utility.log(logger, GlobalLabelParameters.DATE .concat(" : ") , date, Level.INFO);
		Utility.log(logger, GlobalLabelParameters.GET + "_ID" .concat(" : ") ,  this.getID, Level.INFO);
		Utility.log(logger, GlobalLabelParameters.HOST .concat(" : ") , merchantConfig.getRequestHost(),
				Level.INFO);
		
		System.out.println(" Request Type        : " + requestType);
		System.out.println(" ID                  : " + this.getID);
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
			String jwtRequestBody = getID;
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
	 * @param args
	 *            : Contains getId entry.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new GetGenerateHeaders();
	}
}
