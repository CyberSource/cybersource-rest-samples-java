package samples.authentication.AuthSampleCode;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;
import samples.authentication.Data.Configuration;
import samples.authentication.Data.PayloadData;

/**
 * Depending on the authentication type HTTP or JWT , this class performs the
 * POST operation on the test url 'apitest.cybersource.com'.
 * 
 */
public class PostObjectMethod {
	private ApiController apiController;
	private String authenticationType;
	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private Response response;
	/**
	 * REQUEST-TYPE. [Non-Editable]
	 */
	private String requestType = "POST";
	/* Give the url path to where the data needs to be authenticated. */
	private String url;
	private String requestData = PayloadData.readData();
	/**
	 * REQUEST TARGET [Editable]
	 */
	private String requestTarget = "/pts/v2/payments";

	/* This method initiates or begins the process. */
	public static void main(String[] args) throws Exception {
		/**
		 * 
		 */
		new PostObjectMethod();
	}

	/**
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public PostObjectMethod() throws Exception {
		apiController = new ApiController();
		/* Read Merchant details. */
		merchantProp = Configuration.getMerchantDetails();
		merchantConfig = new MerchantConfig(merchantProp);
		merchantConfig.setRequestType(requestType);
		/* Extract Authentication Type from cybs.properties */
		authenticationType = merchantConfig.getAuthenticationType().trim();
		/* Set request data */
		merchantConfig.setRequestJsonPath("not required");
		merchantConfig.setRequestData(requestData);
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
			response = apiController.paymentPost(merchantConfig);
			/* Display Response Message from the server and Headers. */
			if (!StringUtils.isBlank(response.getResponseCode()) && !StringUtils.isBlank(response.getResponseMessage())) {
				new PostGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}
	}
}
