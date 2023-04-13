package samples.authentication.AuthSampleCode;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;

import samples.authentication.harness.MerchantProperties;

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

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}
	
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

		merchantProp = MerchantProperties.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		merchantConfig.setRequestType(requestType);
		authenticationType = merchantConfig.getAuthenticationType().trim();
		merchantConfig.setRequestTarget(requestTarget);
		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
		/* Begin the Get process. */
		process();
	}

	/**
	 * 
	 */
	private void process() {
		System.out.println(authenticationType.toUpperCase() + " Request");

		try {
			response = apiController.paymentGet(merchantConfig);
			WriteLogAudit(Integer.parseInt(response.getResponseCode()));

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
			WriteLogAudit(400);
		}
	}
}
