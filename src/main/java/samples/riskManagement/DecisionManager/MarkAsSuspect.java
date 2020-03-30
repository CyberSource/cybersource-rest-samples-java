package samples.RiskManagement.DecisionManager;

import java.*;
import java.util.*;
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

public class MarkAsSuspect {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static RiskV1UpdatePost201Response run() {
		String id = "5825489395116729903003";
		FraudMarkingActionRequest requestObj = new FraudMarkingActionRequest();

		Riskv1decisionsidmarkingRiskInformation riskInformation = new Riskv1decisionsidmarkingRiskInformation();
		Riskv1decisionsidmarkingRiskInformationMarkingDetails riskInformationMarkingDetails = new Riskv1decisionsidmarkingRiskInformationMarkingDetails();
		riskInformationMarkingDetails.notes("Adding this transaction as suspect");
		riskInformationMarkingDetails.reason("suspected");

		List <String> fieldsIncluded = new ArrayList <String>();
		fieldsIncluded.add("customer_email");
		fieldsIncluded.add("customer_phone");
		riskInformationMarkingDetails.fieldsIncluded(fieldsIncluded);

		riskInformationMarkingDetails.action("add");
		riskInformation.markingDetails(riskInformationMarkingDetails);

		requestObj.riskInformation(riskInformation);

		RiskV1UpdatePost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			DecisionManagerApi apiInstance = new DecisionManagerApi(apiClient);
			result = apiInstance.fraudUpdate(id, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
