package samples.authentication.AuthSampleCode;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import samples.authentication.Data.PayloadData;
import com.cybersource.apisdk.controller.ApiController;
import com.cybersource.apisdk.model.Response;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.util.PropertiesUtil;

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
	/**
	 * REQUEST-TARGET [Editable]
	 */
	private String requestTarget = "/reporting/v2/reportSubscriptions/TRRReport?organizationId=testrest";
	/**
	 * JSON-PATH [Editable]
	 */
	private String requestJsonPath = "src/main/resources/TRRReport.json";
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
		merchantProp = PropertiesUtil.getMerchantProperties();
		merchantConfig = new MerchantConfig(merchantProp);
		merchantConfig.setRequestType(requestType);
		/* Extract Authentication Type from cybs.properties */
		authenticationType = merchantConfig.getAuthenticationType().trim();
		/*
		 * Set Json Path, request target, request data into merchant config
		 * object.
		 */

		merchantConfig.setRequestJsonPath(requestJsonPath);
		merchantConfig.setRequestTarget(requestTarget);
		merchantConfig.setRequestData(requestData);
		// merchantConfig.validateMerchantDetails(logger)
		/* Construct the URL with respect to PUTID. */
		url = "https://" + merchantConfig.getRequestHost() + merchantConfig.getRequestTarget();
		/* Set the URL. */
		merchantConfig.setUrl(url);
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

			if (!StringUtils.isBlank(response.getResponseCode()) && !StringUtils.isBlank(response.getResponseMessage())) {
				new PutGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + response.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + response.getVcCorelationId());
				System.out.println(" Response Message    : " + response.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}

	}
}
