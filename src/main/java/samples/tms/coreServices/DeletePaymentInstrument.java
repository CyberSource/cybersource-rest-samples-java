package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
/**
 * 
 * Delete Payment instrument identifer
 *
 */
public class DeletePaymentInstrument {
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "82EF9C807EA197ECE05340588D0A4700";
	private static String responseCode = null;
	private static String status = null;
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
			
			PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi();
			paymentInstrumentApi.deletePaymentInstrument(profileId, tokenId);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
