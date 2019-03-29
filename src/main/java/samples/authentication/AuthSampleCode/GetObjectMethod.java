package samples.authentication.AuthSampleCode;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import samples.authentication.Data.Configuration;
import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;

public class GetObjectMethod {
	private ApiController apiController;
	private String authenticationType;
	private Properties merchantProp;
	private MerchantConfig merchantConfig;
	private Response response;
	/**
	 * REQUEST TARGET [Editable]
	 */
	private String requestTarget = "/pts/v2/payments/5525472056876749203002";
	/* Request Type [Non-Editable.] */
	private String requestType = "GET";
	/* [Non Editable] */
	private String url;

	/* This method initiates or begins the process. */
	public static void main(String[] args) throws Exception {
		new GetObjectMethod();
	}

	/**
	 * @throws Exception
	 */
	public GetObjectMethod() throws Exception {
		apiController = new ApiController();
		/* Read Merchant details. */
		merchantProp = Configuration.getMerchantDetails();
		merchantConfig = new MerchantConfig(merchantProp);
		/* Set Request Type */
		merchantConfig.setRequestType(requestType);
		/* Extract Authentication Type from cybs.properties */
		authenticationType = merchantConfig.getAuthenticationType().trim();
		merchantConfig.setRequestTarget(requestTarget);
		/* Construct the URL with respect to GETID. */
		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
		/* Set the URL. */
		merchantConfig.setUrl(url);
		/* Begin the Get process. */
		process();
	}

	/**
	 * Calls the APISDK for processing the payment request.
	 * 
	 * @throws Exception
	 */
	private void process() {
		System.out.println(authenticationType.toUpperCase() + " Request");

		try {
			/* Calling APISDK, com.cybersouce.api.controller. */
			response = apiController.paymentGet(merchantConfig);
			/* Display response message and Headers in console. */
			if (!StringUtils.isBlank(response.getResponseCode()) && !StringUtils.isBlank(response.getResponseMessage())) {
				new GetGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" V-C-Corealation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}
	}
}
