package samples.TransactionBatches;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;

import java.io.File;

public class UploadTransactionBatch {
	private static int responseCode ;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
		try {
						
			// Get the file path from the resources folder
			String fileName="batchapiTest.csv";
			String filePath = UploadTransactionBatch.class.getClassLoader().getResource(fileName).getPath();
			// Create a File object
			File file = new File(filePath);
			
            //SDK need file object to send to cybs api endpoint
			merchantProp = Configuration.getMerchantDetailsForBatchUploadSample();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;
			TransactionBatchesApi apiInstance = new TransactionBatchesApi(apiClient);
			ApiResponse<Void> result = apiInstance.uploadTransactionBatchWithHttpInfo(file);

			responseCode = result.getStatusCode();
			status = result.getMessage();
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(responseCode);
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
