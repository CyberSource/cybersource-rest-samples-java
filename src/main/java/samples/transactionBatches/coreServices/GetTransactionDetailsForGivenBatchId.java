package samples.transactionBatches.coreServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;

public class GetTransactionDetailsForGivenBatchId{
	private static String responseCode = null;
	private static Properties merchantProp;
	private static String responseBody = null;
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
			apiInstance.getTransactionBatchDetails( id );

			responseBody = apiClient.responseBody;
			InputStream stream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			org.apache.commons.io.IOUtils.copy(stream, baos);
			byte[] bytes = baos.toByteArray();
			BufferedReader br = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));

			String output;
			String reportType = "csv";
			while ((output = br.readLine()) != null) {
				if (output.contains("xml")) {
					reportType = "xml";
				}
			}
			BufferedReader br_write = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));
			BufferedWriter bw = new BufferedWriter(
					new FileWriter(new File(FILE_PATH + resourceFile + "." + reportType)));
			while ((output = br_write.readLine()) != null) {
				bw.write(output + "\n");
			}
			bw.close();

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("Batch details downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + reportType).getAbsolutePath());
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}