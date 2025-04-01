package samples.TransactionBatches;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import sun.net.www.MimeEntry;

import java.io.File;

public class UploadTransactionBatch {
	private static String responseCode = null;
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
			merchantProp = Configuration.getMerchantDetailsForBatchUploadSample();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			TransactionBatchesApi apiInstance = new TransactionBatchesApi(apiClient);
			
			// Get the file path from the resources folder
			String filePath = UploadTransactionBatch.class.getClassLoader().getResource("qaebc2.rgdltnd0.csv").getPath();
			
			// Create a File object
			File file = new File(filePath);
            
			apiInstance.uploadTransactionBatch(file);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
