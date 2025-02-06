package samples.authentication.AuthSampleCode;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import samples.authentication.Data.PayloadData;
import samples.authentication.harness.MerchantProperties;

import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;

/**
 * Depending on the authentication type HTTP or JWT , this class performs the
 * PUT operation on the given working domain.
 */
public class PutMethod {
	private ApiController apiController;
	private String authenticationType;
	private Response response;
	private Properties merchantProp;
	private MerchantConfig merchantConfig;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	/**
	 * REQUEST-TARGET [Editable]
	 */
	private String requestTarget = "/reporting/v3/reportSubscriptions/TRRReport?organizationId=testrest";

	/**
	 * REQUEST-TYPE. [Non-Editable]
	 */
	private String requestType = "PUT";

	private String url;

	/* Request Data. */
	private String requestData = PayloadData.readData();

	/* This method initiates or begins the process. */
	public static void main(String[] args) throws Exception {
		new PutMethod();
	}

	/**
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public PutMethod() throws Exception {
		apiController = new ApiController();
		merchantProp = MerchantProperties.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
//		merchantConfig.setRequestType(requestType);

		authenticationType = merchantConfig.getAuthenticationType().trim();

//		merchantConfig.setRequestTarget(requestTarget);
//		merchantConfig.setRequestData(requestData);

		url = "https://" + merchantConfig.getRequestHost() + requestTarget;

		/* Begin PUT process. */
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
			response = apiController.paymentPut(merchantConfig);
			WriteLogAudit(Integer.parseInt(response.getResponseCode()));

			if (!StringUtils.isBlank(response.getResponseCode()) && !StringUtils.isBlank(response.getResponseMessage())) {
				new PutGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			WriteLogAudit(400);
		}
	}
}
