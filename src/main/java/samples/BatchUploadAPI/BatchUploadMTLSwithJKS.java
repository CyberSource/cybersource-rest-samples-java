package samples.BatchUploadAPI;

import java.io.File;
import java.lang.invoke.MethodHandles;
import Api.BatchUploadwithMTLSApi;
import Invokers.ApiResponse;

public class BatchUploadMTLSwithJKS {
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
			String filePath = BatchUploadMTLSwithJKS.class.getClassLoader().getResource("batchApiMTLS\\"+fileName).getPath();
			
			//Input1 - Create a File object
			File inputFile = new File(filePath);
			//Input2 - Env Host name
			String envHostName = "secure-batch-test.cybersource.com"; //cas env
			//Input3 - File path of public key for pgp encryption
			String publicKeyFile = BatchUploadMTLSwithJKS.class.getClassLoader().getResource("batchApiMTLS\\bts-encryption-public.asc").getPath();
			//Input4 - keystore path which contains client private key and client cert
			String keystorePath = BatchUploadMTLSwithJKS.class.getClassLoader().getResource("batchApiMTLS\\pushtest.jks").getPath();
			//Input5 - keystore password
			char[] keyStorePassword = "changeit".toCharArray();
			//Input6 - trustStore path which contains server cert
			String truststorePath = BatchUploadMTLSwithJKS.class.getClassLoader().getResource("batchApiMTLS\\castrust.jks").getPath();
			//Input7 - trustStore Password
	        char[] truststorePassword = "changeit".toCharArray();
	        
	        
	        //SDK need file object and jks to upload file to batch api endpoint
			BatchUploadwithMTLSApi apiInstance= new BatchUploadwithMTLSApi();
			ApiResponse<String> result= apiInstance.uploadBatchAPI(inputFile, envHostName, publicKeyFile, keystorePath, keyStorePassword, truststorePath, truststorePassword);
			

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
