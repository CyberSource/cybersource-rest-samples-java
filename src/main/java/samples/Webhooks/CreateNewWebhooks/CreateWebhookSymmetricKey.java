package samples.Webhooks.CreateNewWebhooks;

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

public class CreateWebhookSymmetricKey {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}
	
	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		
		run();
	}
	
	public static InlineResponse2012 run() {
		
		String vCcorrelationId="";
		String vCsenderOrganizationId="";
		String vCpermissions="";
		
		SaveSymEgressKey requestObj = new SaveSymEgressKey();
		
		requestObj.clientRequestAction("CREATE");
		Kmsegressv2keyssymKeyInformation keyInformation = new Kmsegressv2keyssymKeyInformation();
		keyInformation.provider("nrtd");
		keyInformation.tenant("merchantName");
		keyInformation.keyType("sharedSecret");
		keyInformation.organizationId("merchantName");
		requestObj.keyInformation(keyInformation);
		
		InlineResponse2012 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreateNewWebhooksApi apiInstance = new CreateNewWebhooksApi(apiClient);
			result=apiInstance.saveSymEgressKey(vCsenderOrganizationId, vCpermissions, vCcorrelationId, requestObj);

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