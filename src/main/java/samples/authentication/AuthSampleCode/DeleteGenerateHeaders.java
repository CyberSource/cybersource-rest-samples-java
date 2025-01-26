package samples.authentication.AuthSampleCode;

import java.lang.invoke.MethodHandles;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cybersource.authsdk.core.Authorization;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.util.GlobalLabelParameters;
import com.cybersource.authsdk.util.PropertiesUtil;

import samples.authentication.harness.MerchantProperties;
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

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	/**
	 * 
	 * @param getID
	 *            : Unique getID
	 * @throws Exception
	 *             :
	 */
	public DeleteGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		logger = LogManager.getLogger(getClass());
		merchantConfig.validateMerchantDetails(requestType);
		auth = new Authorization();
		generateDeleteHeaders(merchantConfig);
	}

	public DeleteGenerateHeaders() throws Exception {
		auth = new Authorization();
		merchantProp = MerchantProperties.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		
		this.logger = LogManager.getLogger(getClass());
		this.logger.info(GlobalLabelParameters.BEGIN_TRANSACTION + "\n******************* Delete Generate Headers *******************");
		
		System.out.println("Authentication Type : " +  merchantConfig.getAuthenticationType().trim());
		
		boolean isMerchant = merchantConfig.validateMerchantDetails("DELETE");
		if(isMerchant){
			generateDeleteHeaders(merchantConfig);
		} else {
			WriteLogAudit(400);
		}
		
		this.logger.info(GlobalLabelParameters.END_TRANSACTION + "\n");
	}
	
	/**
	 * This method generates the detailed header detail of the transaction and displays and log them. 
	 * @throws Exception
	 */
	private void generateDeleteHeaders(MerchantConfig merchantConfig) throws Exception {
		authenticationType = merchantConfig.getAuthenticationType().trim();
		
		this.logger.info(GlobalLabelParameters.AUTENTICATION_TYPE + authenticationType);
		this.logger.info(GlobalLabelParameters.REQUEST_TYPE + " : " + requestType);
		this.logger.info(GlobalLabelParameters.DATE + " : " + date);
		this.logger.info(GlobalLabelParameters.HOST + " : " + merchantConfig.getRequestHost());
		this.logger.info(GlobalLabelParameters.V_C_MERCHANTID + " : " + merchantConfig.getMerchantID().trim());
		this.logger.info(GlobalLabelParameters.CONTENT_TYPE + " : " + GlobalLabelParameters.APPLICATION_JSON);
		
		System.out.println("Request Type        : " + requestType);
		System.out.println("Date                : " + date);
		System.out.println("MerchantID          : " + merchantConfig.getMerchantID().trim());
		System.out.println("HOST                : " + merchantConfig.getRequestHost());
		System.out.println(GlobalLabelParameters.CONTENT_TYPE + "        : " + GlobalLabelParameters.APPLICATION_JSON);
		
		if (authenticationType.equalsIgnoreCase(GlobalLabelParameters.HTTP)) {
			System.out.println(GlobalLabelParameters.USERAGENT + "          : " + GlobalLabelParameters.USER_AGENT_VALUE);
			this.logger.info(GlobalLabelParameters.USERAGENT + " : " + GlobalLabelParameters.USER_AGENT_VALUE);
			String date = PropertiesUtil.getNewDate();
			tempSig = auth.getToken(merchantConfig,requestType,null,null,date);
			System.out.println("Signature           : " + tempSig.toString());
			WriteLogAudit(200);
			
		} else {
			String jwtRequestBody = null;
			String date = PropertiesUtil.getNewDate();
			tempSig = auth.getToken(merchantConfig,requestType,jwtRequestBody,null,date);
			System.out.println("Authorization, Bearer " + tempSig.toString());
			WriteLogAudit(200);
		}
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
