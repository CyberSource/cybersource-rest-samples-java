package samples.SecureFileShare;

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

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.*;

public class DownloadFileWithFileIdentifier {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	public static String resourceFile = "DownloadedFileWithFileID";
	private static final String FILE_PATH = "src/main/resources/";
	private static String responseBody = null;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
		String  fileId = "Q2hhcmdlYmFja0FuZFJldHJpZXZhbFJlcG9ydC1hYWVkMWEwMS03OGNhLTU1YzgtZTA1My1hMjU4OGUwYWNhZWEuY3N2LTIwMjAtMDctMzA=";
		String organizationId = "testrest";

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			SecureFileShareApi apiInstance = new SecureFileShareApi(apiClient);
			ApiResponse<InputStream> responseStream = apiInstance.getFileWithHttpInfo(fileId, organizationId);

			// START : FILE DOWNLOAD FUNCTIONALITY

			String contentType = responseStream.getHeaders().get("Content-Type").get(0);

			String fileExtension = "csv";

			if (contentType.contains("json")) {
				fileExtension = contentType.substring(contentType.length() - 4);
			} else {
				fileExtension = contentType.substring(contentType.length() - 3);
			}

			File targetFile = new File(FILE_PATH + resourceFile + "." + fileExtension);

			FileUtils.copyInputStreamToFile(responseStream.getData(), targetFile);

			// END : FILE DOWNLOAD FUNCTIONALITY

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);

			System.out.println("File Downloaded at the following location : ");
			System.out.println(new File(FILE_PATH + resourceFile + "." + fileExtension).getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
