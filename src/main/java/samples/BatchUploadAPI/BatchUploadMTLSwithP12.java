package samples.BatchUploadAPI;

import java.io.File;
import java.lang.invoke.MethodHandles;

import Api.BatchUploadwithMTLSApi;
import Invokers.ApiResponse;

public class BatchUploadMTLSwithP12 {
	private static int responseCode ;
	private static String responseMessage = null;
	
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
			String filePath = BatchUploadMTLSwithP12.class.getClassLoader().getResource("batchApiMTLS/"+fileName).getPath();
			
			//Input1 - Create a File object
			File inputFile = new File(filePath);
			//Input2 - Env Host name
			String envHostName = "secure-batch-test.cybersource.com"; //cas env
			//Input3 - File path of public key for pgp encryption
			String publicKeyFile = BatchUploadMTLSwithP12.class.getClassLoader().getResource("batchApiMTLS/bts-encryption-public.asc").getPath();
			//Input4 - keystore path which contains client private key and client cert
			String p12Path = BatchUploadMTLSwithP12.class.getClassLoader().getResource("batchApiMTLS/pushtest.p12").getPath();
			//Input5 - keystore password
			char[] p12Password = "changeit".toCharArray();
			
			//Input6 - trustStore path which contains server cert for ssl verification for endpoint url (optional) can pass as null
			//String serverCertPath = BatchUploadMTLSwithP12.class.getClassLoader().getResource("batchApiMTLS/serverCasCert.pem").getPath();
			String serverCertPath = null;
			
	        
	        
	        //SDK need file object and p12 to upload file to batch api endpoint
			BatchUploadwithMTLSApi apiInstance= new BatchUploadwithMTLSApi();
			ApiResponse<String> result= apiInstance.uploadBatchAPI(inputFile, envHostName, publicKeyFile, p12Path, p12Password, serverCertPath);
			

			responseCode = result.getStatusCode();
			responseMessage = result.getMessage();
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMessage);
			WriteLogAudit(responseCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
