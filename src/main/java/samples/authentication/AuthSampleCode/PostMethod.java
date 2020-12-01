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
	private String requestJson = "{\r\n" + 
			"  \"clientReferenceInformation\": {\r\n" + 
			"    \"code\": \"TC50171_3\"\r\n" + 
			"  },\r\n" + 
			"  \"processingInformation\": {\r\n" + 
			"    \"commerceIndicator\": \"internet\"\r\n" + 
			"  },\r\n" + 
			"  \"aggregatorInformation\": {\r\n" + 
			"    \"subMerchant\": {\r\n" + 
			"      \"cardAcceptorID\": \"1234567890\",\r\n" + 
			"      \"country\": \"US\",\r\n" + 
			"      \"phoneNumber\": \"650-432-0000\",\r\n" + 
			"      \"address1\": \"900 Metro Center\",\r\n" + 
			"      \"postalCode\": \"94404-2775\",\r\n" + 
			"      \"locality\": \"Foster City\",\r\n" + 
			"      \"name\": \"Visa Inc\",\r\n" + 
			"      \"administrativeArea\": \"CA\",\r\n" + 
			"      \"region\": \"PEN\",\r\n" + 
			"      \"email\": \"test@cybs.com\"\r\n" + 
			"    },\r\n" + 
			"    \"name\": \"V-Internatio\",\r\n" + 
			"    \"aggregatorID\": \"123456789\"\r\n" + 
			"  },\r\n" + 
			"  \"orderInformation\": {\r\n" + 
			"    \"billTo\": {\r\n" + 
			"      \"country\": \"US\",\r\n" + 
			"      \"lastName\": \"VDP\",\r\n" + 
			"      \"address2\": \"Address 2\",\r\n" + 
			"      \"address1\": \"201 S. Division St.\",\r\n" + 
			"      \"postalCode\": \"48104-2201\",\r\n" + 
			"      \"locality\": \"Ann Arbor\",\r\n" + 
			"      \"administrativeArea\": \"MI\",\r\n" + 
			"      \"firstName\": \"RTS\",\r\n" + 
			"      \"phoneNumber\": \"999999999\",\r\n" + 
			"      \"district\": \"MI\",\r\n" + 
			"      \"buildingNumber\": \"123\",\r\n" + 
			"      \"company\": \"Visa\",\r\n" + 
			"      \"email\": \"test@cybs.com\"\r\n" + 
			"    },\r\n" + 
			"    \"amountDetails\": {\r\n" + 
			"      \"totalAmount\": \"102.21\",\r\n" + 
			"      \"currency\": \"USD\"\r\n" + 
			"    }\r\n" + 
			"  },\r\n" + 
			"  \"paymentInformation\": {\r\n" + 
			"    \"card\": {\r\n" + 
			"      \"expirationYear\": \"2031\",\r\n" + 
			"      \"number\": \"5555555555554444\",\r\n" + 
			"      \"securityCode\": \"123\",\r\n" + 
			"      \"expirationMonth\": \"12\",\r\n" + 
			"      \"type\": \"002\"\r\n" + 
			"    }\r\n" + 
			"  }\r\n" + 
			"}";
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
		merchantConfig.setRequestData(requestJson);
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
			if (!StringUtils.isBlank(responseObj.getResponseCode())
					&& !StringUtils.isBlank(responseObj.getResponseMessage())) {
				new PostGenerateHeaders(merchantConfig);
				System.out.println(" URL                 : " + url);
				System.out.println(" Response Code       : " + responseObj.getResponseCode());
				System.out.println(" v-c-Co-relation ID  : " + responseObj.getVcCorelationId());
				System.out.println(" Response Message    : " + responseObj.getResponseMessage());
			} else {
				System.out.println(responseObj.getResponseMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// return;
		}
	}
}
