package samples.BinLookup;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
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

public class BINLookupWithTMSJWTTransientToken {
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

	public static InlineResponse2011 run() {
	
		CreateBinLookupRequest requestObj = new CreateBinLookupRequest();

		Binv1binlookupTokenInformation tokenInformation = new Binv1binlookupTokenInformation();
		tokenInformation.transientTokenJwt("eyJraWQiOiIwODd0bk1DNU04bXJHR3JHMVJQTkwzZ2VyRUh5VWV1ciIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJGbGV4LzA4IiwiZXhwIjoxNjYwMTk1ODcwLCJ0eXBlIjoiYXBpLTAuMS4wIiwiaWF0IjoxNjYwMTk0OTcwLCJqdGkiOiIxRTBXQzFHTzg3SkcxQkRQMENROFNDUjFUVEs4NlU5Tjk4SDNXSDhJRk05TVZFV1RJWUZJNjJGNDk0MUU3QTkyIiwiY29udGVudCI6eyJwYXltZW50SW5mb3JtYXRpb24iOnsiY2FyZCI6eyJudW1iZXIiOnsibWFza2VkVmFsdWUiOiJYWFhYWFhYWFhYWFgxMTExIiwiYmluIjoiNDExMTExIn0sInR5cGUiOnsidmFsdWUiOiIwMDEifX19fX0.MkCLbyvufN4prGRvHJcqCu1WceDVlgubZVpShNWQVjpuFQUuqwrKg284sC7ucVKuIsOU0DTN8_OoxDLduvZhS7X_5TnO0QjyA_aFxbRBvU_bEz1l9V99VPADG89T-fox_L6sLUaoTJ8T4PyD7rkPHEA0nmXbqQCVqw4Czc5TqlKCwmL-Fe0NBR2HlOFI1PrSXT-7_wI-JTgXI0dQzb8Ub20erHwOLwu3oni4_ZmS3rGI_gxq2MHi8pO-ZOgA597be4WfVFAx1wnMbareqR72a0QM4DefeoltrpNqXSaASVyb5G0zuqg-BOjWJbawmg2QgcZ_vE3rJ6PDgWROvp9Tbw");
		requestObj.tokenInformation(tokenInformation);

		
		InlineResponse2011 result=null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			BinLookupApi apiInstance = new BinLookupApi(apiClient);
			result =apiInstance.getAccountInfo(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	
	}
}