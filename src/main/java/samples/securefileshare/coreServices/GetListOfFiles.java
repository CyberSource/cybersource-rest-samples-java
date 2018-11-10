package samples.securefileshare.coreServices;

import java.util.Properties;

import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SecureFileShareApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.V1FileDetailsGet200Response;

public class GetListOfFiles {

	private static String responseCode = null;
	private static String status = null;
	private static String organizationId = "testrest";
	private static Properties merchantProp;
	
	static LocalDate startDate=new LocalDate("2018-10-20");
	static LocalDate endDate=new LocalDate("2018-10-30");


	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);

			SecureFileShareApi secureFileShareApi = new SecureFileShareApi();
			V1FileDetailsGet200Response response = secureFileShareApi.getFileDetails(startDate, endDate, organizationId,merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
