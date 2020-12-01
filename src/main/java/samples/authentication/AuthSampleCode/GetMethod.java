package samples.authentication.AuthSampleCode;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.util.PropertiesUtil;

public class GetMethod {
	private ApiController apiController;
	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private Response response;
	private String authenticationType;
	/**
	 * REQUEST TARGET / UNIQUE GET ID [Editable]
	 */
	private String requestTarget = "/pts/v2/payments/6065710300556203803003";
	/**
	 * REQUEST-TYPE. [Non-Editable]
	 */
	private String requestType = "GET";
	private String url;

	/* This method initiates or begins the process. */
	public static void main(String args[]) throws Exception {
		new GetMethod();
	}

	/**
	 * 
	 * @throws Exception
	 *             : Throws runtime exceptions.
	 */
	public GetMethod() throws Exception {
		apiController = new ApiController();

		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		/* Set Request Type into the merchant config object. */
		merchantConfig.setRequestType(requestType);
		/* Extract Authentication Type from cybs.properties */
		authenticationType = merchantConfig.getAuthenticationType().trim();
		merchantConfig.setRequestTarget(requestTarget);
		/* Construct the URL. */
		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
		/* Set the URL. */
		merchantConfig.setUrl(url);
		/* Begin the Get process. */
		process();
	}

	/**
	 * 
	 */
	private void process() {
		System.out.println(authenticationType.toUpperCase() + " Request");

		try {
			/* Calling APISDK, com.cybersouce.api.controller. */
			response = apiController.paymentGet(merchantConfig);
			/* Display response message and Headers in console. */
			if (!StringUtils.isBlank(response.getResponseCode())&& !StringUtils.isBlank(response.getResponseMessage())) {
				new GetGenerateHeaders(requestTarget);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" V-C-Corealation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response.getResponseMessage());
			} 
			else {
				System.out.println(response.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}
	}
}
