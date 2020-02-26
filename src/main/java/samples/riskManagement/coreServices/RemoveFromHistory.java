package samples.riskManagement.coreServices;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;

public class RemoveFromHistory {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static RiskV1UpdatePost201Response run() {
	
		String id = "5825489395116729903003";
		FraudMarkingActionRequest requestObj = new FraudMarkingActionRequest();

		Riskv1decisionsidmarkingRiskInformation riskInformation = new Riskv1decisionsidmarkingRiskInformation();
		Riskv1decisionsidmarkingRiskInformationMarkingDetails riskInformationMarkingDetails = new Riskv1decisionsidmarkingRiskInformationMarkingDetails();
		riskInformationMarkingDetails.notes("Adding this transaction as suspect");
		riskInformationMarkingDetails.reason("suspected");
		riskInformationMarkingDetails.action("hide");
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
	
	public static void main(String args[]) throws Exception{
		
			run();
	}
}
