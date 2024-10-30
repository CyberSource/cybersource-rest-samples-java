//package samples.authentication.AuthSampleCode;
//
//import java.lang.invoke.MethodHandles;
//import java.util.Properties;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.cybersource.apisdk.controller.ApiController;
//import com.cybersource.apisdk.model.Response;
//import com.cybersource.authsdk.core.MerchantConfig;
//import samples.authentication.harness.MerchantProperties;

/**
 * Depending on the authentication type HTTP or JWT , this class performs the
 * PUT operation on the test url 'apitest.cybersource.com'.
 */
//public class DeleteMethod {
//	private ApiController apiController;
//	private String authenticationType;
//	private String response;
//	private String responseCode;
//	private Properties merchantProp;
//	private MerchantConfig merchantConfig;
//	private Response responseObj;
//	/* Request Type is always PUT */
//	private String requestType = "DELETE";
//	/* Give the url path to where the data needs to be authenticated. */
//	private String url;
//	/* Request Target. */
//	private String requestTarget = "/reporting/v2/reportSubscriptions/TRRReport?organizationId=testrest";
//	private String requestJson = "{ \"startDay\":\"23\",\r\n" +
//			"  \"timeZone\":\"America/Chicago\",\r\n" +
//			"  \"reportDefinitionName\":\"TransactionRequestClass\",\r\n" +
//			"  \"startTime\":\"1100\",\r\n" +
//			"  \"reportFrequency\":\"DAILY\",\r\n" +
//			"  \"ReportName\":\"TRRReport\",\r\n" +
//			"  \"reportFormat\":\"csv\",\r\n" +
//			"  \"orgId\":\"testrest\",\r\n" +
//			"  \"reportType\":\"detail\",\r\n" +
//			"  \"reportFields\": [\"Request.RequestID\",\"Request.TransactionDate\",\"Request.MerchantReferenceNumber\",\"Request.MerchantID\"]\r\n" +
//			"}";
//
//	public static void WriteLogAudit(int status) {
//		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
//		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
//	}

	/* This method initiates or begins the process. */
//	public static void main(String[] args) throws Exception {
//		new DeleteMethod();
//	}

	/**
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
//	public DeleteMethod() throws Exception {
//		apiController = new ApiController();
//
//		merchantProp = MerchantProperties.getMerchantProperties();
//		merchantConfig = new MerchantConfig(merchantProp);
//		merchantConfig.setRequestType(requestType);
//
//		authenticationType = merchantConfig.getAuthenticationType().trim();
//
//		merchantConfig.setRequestData(requestJson);
//		merchantConfig.setRequestTarget(requestTarget);
//		/* Begin PUT process. */
//		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
//		process();
//	}

	/**
	 * Calls the APISDK for processing the payment request.
	 * 
	 * @throws Exception
	 */
//	private void process() throws Exception {
//		System.out.println(authenticationType.toUpperCase() + " Request");
//
//		try {
//			/* Call payment method of Api Controller class */
//			responseObj = apiController.paymentGet(merchantConfig);
//			response = responseObj.getResponseMessage();
//			/* Display Response Message from the server. */
//			responseCode = responseObj.getResponseCode();
//			if (responseCode.equals("200") || responseCode.equals("404")) {
//				WriteLogAudit(200);
//			} else {
//				WriteLogAudit(Integer.parseInt(responseCode));
//			}
//			String vcCorrelationId = responseObj.getVcCorelationId();
//			if (!StringUtils.isBlank(responseCode)&& !StringUtils.isBlank(response)) {
//				new DeleteGenerateHeaders(merchantConfig);
//				System.out.println("URL                : " + url);
//				System.out.println("Response Code      : " + responseCode);
//				System.out.println("V-C-Corealation ID : " + vcCorrelationId);
//				System.out.println("Response Message   : " + response);
//			}
//			else {
//				System.out.println(responseObj.getResponseMessage());
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			WriteLogAudit(400);
//		}
//	}
//}
