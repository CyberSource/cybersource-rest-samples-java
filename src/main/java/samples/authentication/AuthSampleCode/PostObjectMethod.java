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
		merchantProp = Configuration.getMerchantDetails();
		merchantConfig = new MerchantConfig(merchantProp);
		merchantConfig.setRequestType(requestType);
		authenticationType = merchantConfig.getAuthenticationType().trim();
		merchantConfig.setRequestData(requestData);
		merchantConfig.setRequestTarget(requestTarget);

		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();

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
			response = apiController.paymentPost(merchantConfig);
			
			if (!StringUtils.isBlank(response.getResponseCode()) && !StringUtils.isBlank(response.getResponseMessage())) {
				new PostGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
