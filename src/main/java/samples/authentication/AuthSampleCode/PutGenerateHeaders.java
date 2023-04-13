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
import com.cybersource.authsdk.payloaddigest.PayloadDigest;
import com.cybersource.authsdk.util.GlobalLabelParameters;
import samples.authentication.harness.FileReader;
import samples.authentication.harness.MerchantProperties;

/**
 * This class generates the Headers that are present in the token was sent the
 * server for transaction, for PUT operations.
 *
 */
public class PutGenerateHeaders {
	private static Properties merchantProp;
	private static MerchantConfig merchantConfig;
	private String authenticationType = null;
	private Authorization auth;
	private String requestType = "PUT";
	private String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
	private PayloadDigest digest;
	private Logger logger;
	private String jsonRequestData;
	private String TempSig;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public PutGenerateHeaders(MerchantConfig merchantConfig) throws Exception {
		auth = new Authorization();
		logger = LogManager.getLogger(getClass());
		digest = new PayloadDigest(merchantConfig);
		PutMethodHeaders(merchantConfig);
	}

	public PutGenerateHeaders() throws Exception {
		auth = new Authorization();
		this.logger = LogManager.getLogger(getClass());
		
		this.jsonRequestData = FileReader.readJsonFromFile("src/main/resources/TRRReport.json");
		
		merchantConfig.setRequestData(jsonRequestData);
		
		digest = new PayloadDigest(merchantConfig);
		this.logger.info(GlobalLabelParameters.BEGIN_TRANSACTION + "\n******************* Generate PUT Headers *******************");
		
		boolean isMerchant = merchantConfig.validateMerchantDetails();
		if(isMerchant){
			PutMethodHeaders(merchantConfig);
		}
		
		this.logger.info(GlobalLabelParameters.END_TRANSACTION + "\n");
	}

	private void PutMethodHeaders(MerchantConfig merchantConfig) throws Exception {
		authenticationType = merchantConfig.getAuthenticationType().trim();

		this.logger.info(GlobalLabelParameters.AUTENTICATION_TYPE + authenticationType);
		this.logger.info(GlobalLabelParameters.REQUEST_TYPE + " : " + requestType);
		this.logger.info(GlobalLabelParameters.DATE + " : " + date);
		this.logger.info(GlobalLabelParameters.V_C_MERCHANTID + " : " + merchantConfig.getMerchantID().trim());
		this.logger.info(GlobalLabelParameters.CONTENT_TYPE + " : " + GlobalLabelParameters.APPLICATION_JSON);
		this.logger.info(GlobalLabelParameters.HOST + " : " + merchantConfig.getRequestHost());		

		System.out.println("MerchantID          : " + merchantConfig.getMerchantID().trim());
		System.out.println("Date                : " + date);
		System.out.println("Request Type        : " + requestType);
		System.out.println("HOST                : " + merchantConfig.getRequestHost());
		System.out.println(GlobalLabelParameters.CONTENT_TYPE + "        : " + GlobalLabelParameters.APPLICATION_JSON);

		if (authenticationType.equalsIgnoreCase(GlobalLabelParameters.HTTP)) {
			System.out.println(GlobalLabelParameters.USERAGENT + "          : " + GlobalLabelParameters.USER_AGENT_VALUE);
			this.logger.info(GlobalLabelParameters.USERAGENT + " : " + GlobalLabelParameters.USER_AGENT_VALUE);

			System.out.println("Digest              : " + digest.getDigest());

			TempSig = auth.getToken(merchantConfig);
			System.out.println("Signature           : " + TempSig.toString());
			WriteLogAudit(200);
			
		} else {
			String jwtRequestBody = this.jsonRequestData;
			auth.setJWTRequestBody(jwtRequestBody);
			TempSig = auth.getToken(merchantConfig);
			System.out.println("Authorization, Bearer " + TempSig.toString());
			WriteLogAudit(200);
		}
	}

	/* This method initiate the PUT Header program. */
	public static void main(String[] args) throws Exception {
		merchantProp = MerchantProperties.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);

		merchantConfig.setRequestType("PUT");		

		new PutGenerateHeaders();
	}
}
