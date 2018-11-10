package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TmsV1PaymentinstrumentsPost201Response;

public class RetrievePaymentInstrument {
	private static String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId="7A487901519D17A1E05340588D0A5171";
	private static  String responseCode=null;
	private static String status=null;
	static TmsV1PaymentinstrumentsPost201Response response;
	private static Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {
	
	try {
		/* Read Merchant details. */
		merchantProp = Configuration.getMerchantDetails();
		MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
		
		PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi();
		response=paymentInstrumentApi.tmsV1PaymentinstrumentsTokenIdGet(profileId, tokenId,merchantConfig);
		
		responseCode=ApiClient.responseCode;
		status=ApiClient.status;
		
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("Status :" +status);
		System.out.println(response.getId());
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
