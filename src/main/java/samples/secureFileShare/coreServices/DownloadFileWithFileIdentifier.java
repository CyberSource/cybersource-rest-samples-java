package samples.secureFileShare.coreServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SecureFileShareApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class DownloadFileWithFileIdentifier {

	private static String responseCode = null;
	private static String status = null;
	private static String responseBody = null;
	
	private static String organizationId = "testrest";
	private static Properties merchantProp;
	private  static String resourceFile = "SecureFile";
    private static final String FILE_PATH = "src/main/resources/";
	
    private static String  fileId = "VFJSUmVwb3J0LTc4NTVkMTNmLTkzOTgtNTExMy1lMDUzLWEyNTg4ZTBhNzE5Mi5jc3YtMjAxOC0xMC0yMA==";


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

			SecureFileShareApi secureFileShareApi = new SecureFileShareApi(apiClient);
			secureFileShareApi.getFileWithHttpInfo(fileId, organizationId);
			
			responseBody = apiClient.responseBody;
			InputStream stream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        org.apache.commons.io.IOUtils.copy(stream, baos);
	        byte[] bytes = baos.toByteArray();
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (new ByteArrayInputStream(bytes))));

	        String output;
	        String reportType="csv";
	        while ((output = br.readLine()) != null) {
	            if(output.contains("xml")){
	                reportType = "xml";
	            }
	        }
	         BufferedReader br_write = new BufferedReader(new InputStreamReader(
	                (new ByteArrayInputStream(bytes))));
	        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_PATH+resourceFile+"."+reportType)));
	        while ((output = br_write.readLine()) != null) {
	           bw.write(output+"\n");
	        }
	        bw.close();

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("File downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + reportType).getAbsolutePath());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
