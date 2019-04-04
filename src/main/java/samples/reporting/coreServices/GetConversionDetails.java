package samples.reporting.coreServices;

import java.util.Properties;
import org.joda.time.DateTime;
import com.cybersource.authsdk.core.MerchantConfig;
import Api.ConversionDetailsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
/**
 * This is a sample code  to get conversion details for a given org Id
 * User should pass org id, starttime and endtime
 *
 */
public class GetConversionDetails {
	private static String responseCode = null;
	private static String status = null;
	private static String organizationId="testrest";
	private static DateTime startTime=new DateTime ("2019-03-21T00:00:00.0Z");
	private static DateTime endTime=new DateTime ("2019-03-21T23:00:00.0Z");
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}
	private static void process() throws Exception {

		try {

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;	
			
			ConversionDetailsApi conversionDetailsApi=new ConversionDetailsApi();
			conversionDetailsApi.getConversionDetail(startTime, endTime, organizationId);
			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}
}
