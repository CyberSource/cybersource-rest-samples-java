package samples.Transaction_Batches;

import java.*;
import java.util.*;
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

import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class GetTransactionDetailsForGivenBatchId {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	public static String resourceFile = "BatchDetailsReport";
	private static final String FILE_PATH = "src/main/resources/";
	private static String responseBody = null;


/*
	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run(id);
	}
*/
	public static void run(String id) {
	
		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			TransactionBatchesApi apiInstance = new TransactionBatchesApi(apiClient);
			apiInstance.getTransactionBatchDetailsWithHttpInfo(id);

			// START : FILE DOWNLOAD FUNCTIONALITY

			responseBody = apiClient.responseBody;

			InputStream inStream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

			org.apache.commons.io.IOUtils.copy(inStream, byteOutStream);
			byte[] streamInBytes = byteOutStream.toByteArray();

			BufferedReader bReader = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(streamInBytes))));
			String output;
			String reportType = "csv";

			while((output = bReader.readLine()) != null) {
				if (output.contains("xml")) {
					reportType = "xml";
				} else if (output.contains("json")) {
					reportType = "json";
				}
			}

			bReader.close();

			bReader = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(streamInBytes))));
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(FILE_PATH + resourceFile + "." + reportType)));

			while((output = bReader.readLine()) != null) {
				bWriter.write(output + "\n");
			}

			bReader.close();
			bWriter.close();

			// END : FILE DOWNLOAD FUNCTIONALITY

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);

			System.out.println("File Downloaded at the following location : ");
			System.out.println(new File(FILE_PATH + resourceFile + "." + reportType).getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
