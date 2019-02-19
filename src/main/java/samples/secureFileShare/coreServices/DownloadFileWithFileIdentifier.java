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

	private String organizationId = "testrest";
	private Properties merchantProp;
	private String resourceFile = "SecureFile";
    private static final String FILE_PATH = "src/test/resources/";
	
    private  String  fileId = "VFJSUmVwb3J0LTc4NTVkMTNmLTkzOTgtNTExMy1lMDUzLWEyNTg4ZTBhNzE5Mi5jc3YtMjAxOC0xMC0yMA==";


	public static void main(String args[]) throws Exception {
		DownloadFileWithFileIdentifier downloadFileWithFileIdentifier = new DownloadFileWithFileIdentifier();
		downloadFileWithFileIdentifier.process();
	}

	private void process() throws Exception {
		String className=DownloadFileWithFileIdentifier.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			SecureFileShareApi secureFileShareApi = new SecureFileShareApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			secureFileShareApi.getFileWithHttpInfo(fileId, organizationId);
			InputStream stream = new ByteArrayInputStream(apiClient.getResponseBody().getBytes(StandardCharsets.UTF_8));
			
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
	        System.out.println("File downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + reportType).getAbsolutePath() + "\n");
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getResponseBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
	}

}
