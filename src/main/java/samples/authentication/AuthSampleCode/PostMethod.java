package samples.authentication.AuthSampleCode;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.util.PropertiesUtil;

/**
 * Depending on the authentication type HTTP or JWT , this class performs the
 * POST operation on the test url 'apitest.cybersource.com'.
 * 
 */
public class PostMethod {
	private ApiController apiController;
	private String authenticationType;
	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private Response responseObj;
	/**
	 * REQUEST TARGET [Editable]
	 */
	private String requestTarget = "/pts/v2/payments/";
	/**
	 * JSON PATH [Editable]
	 */
	private String requestJsonPath = "src/main/resources/request.json";
	/**
	 * REQUEST-TYPE. [Non-Editable]
	 */
	private String requestType = "POST";
	private String url;

	/* This method initiates or begins the process. */
	public static void main(String[] args) throws Exception {
		/**
		 * 
		 */
		new PostMethod();
	}

	/**
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public PostMethod() throws Exception {
		apiController = new ApiController();

		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		/* Extract Authentication Type from cybs.properties */
		authenticationType = merchantConfig.getAuthenticationType().trim();
		/* Set request Type. */
		merchantConfig.setRequestType(requestType);
		/* Set request data */
		merchantConfig.setRequestJsonPath(requestJsonPath);
		/* Set request target */
		merchantConfig.setRequestTarget(requestTarget);
		/* Set URL path w.r.t Post operation. */
		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
		merchantConfig.setUrl(url);
		/* Begin Post process. */
		process();
	}

	/**
	 * Calls the APISDK for processing the payment request.
	 * 
	 * @throws Exception
	 */
	private void process() throws Exception {
		System.out.println(authenticationType.toUpperCase() + " Request");
		try {
			/* Call payment method of Api Controller class */
			responseObj = apiController.paymentPost(merchantConfig);
			/* Display Response Message from the server and Headers. */
			if (!StringUtils.isBlank(responseObj.getResponseCode())&& !StringUtils.isBlank(responseObj.getResponseMessage())) {
				new PostGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + responseObj.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + responseObj.getVcCorelationId());
				System.out.println(" Response Message    : " + responseObj.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}
	}
}
