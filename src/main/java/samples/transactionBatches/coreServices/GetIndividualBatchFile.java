package samples.transactionBatches.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.PtsV1TransactionBatchesGet200Response;

public class GetIndividualBatchFile {

	private static String responseCode = null;
	private static String status = null;
	private static String id = "Owcyk6pl";
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);

			TransactionBatchApi transactionBatchApi = new TransactionBatchApi();
			ApiResponse<PtsV1TransactionBatchesGet200Response> response = transactionBatchApi.ptsV1TransactionBatchesIdGet(id);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
