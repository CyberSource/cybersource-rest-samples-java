package samples.transactionBatches.coreServices;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.FileUtils;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiResponse;

public class GetTransactionDetailsForGivenBatchId{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	public static String resourceFile = "BatchDetailsReport";
	private static final String FILE_PATH = "src/main/resources/";
	
	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {
	
		String id = "12345";
		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			TransactionBatchesApi apiInstance = new TransactionBatchesApi(apiClient);
			ApiResponse<InputStream> responseStream = apiInstance.getTransactionBatchDetailsWithHttpInfo( id );
			
			String contentType = responseStream.getHeaders().get("Content-Type").get(0);
			
			String fileExtension = "csv";
			
			if (contentType.contains("json")) {
				fileExtension = contentType.substring(contentType.length() - 4);
			} else {
				fileExtension = contentType.substring(contentType.length() - 3);
			}
			
			File targetFile = new File(FILE_PATH + resourceFile + "." + fileExtension);
			
			FileUtils.copyInputStreamToFile(responseStream.getData(), targetFile);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("File downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + fileExtension).getAbsolutePath());
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}