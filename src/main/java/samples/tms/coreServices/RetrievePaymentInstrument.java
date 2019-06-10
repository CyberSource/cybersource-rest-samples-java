package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments;
/**
 * 
 * Retrieve Payment Instrument
 *
 */
public class RetrievePaymentInstrument {
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "888454C31FB6150CE05340588D0AA9BE";
	private static  String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments response;
	private static Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {
	
	try {
		/* Read Merchant details. */
		merchantProp = Configuration.getMerchantDetails();
		MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
		
		ApiClient apiClient = new ApiClient();
		
		apiClient.merchantConfig = merchantConfig;
		
		PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi(apiClient);
		response=paymentInstrumentApi.getPaymentInstrument(profileId, tokenId);
		
		responseCode = apiClient.responseCode;
		status = apiClient.status;
		
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("Status :" +status);
		System.out.println("ResponseBody :"+apiClient.respBody);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
