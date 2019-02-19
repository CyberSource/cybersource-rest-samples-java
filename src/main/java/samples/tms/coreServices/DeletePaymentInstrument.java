package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TmsV1PaymentinstrumentsPost201Response;

public class DeletePaymentInstrument {
	private  String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private Properties merchantProp;

	public static void main(String args[]) throws Exception {
		DeletePaymentInstrument deletePaymentInstrument = new DeletePaymentInstrument();
		deletePaymentInstrument.process();
	}

	private void process() throws Exception {
		String className=DeletePaymentInstrument.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = new ApiClient();
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			CreatePaymentInstrument createPaymentInstrument = new CreatePaymentInstrument();
			TmsV1PaymentinstrumentsPost201Response response=createPaymentInstrument.process();
			if (response != null) {
				PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi(merchantConfig);
				apiClient=Invokers.Configuration.getDefaultApiClient();
				paymentInstrumentApi.tmsV1PaymentinstrumentsTokenIdDelete(profileId, response.getId());
			}
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
	}

}
