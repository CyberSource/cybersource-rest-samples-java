package samples.RecurringBillingSubscriptions.Plans;

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

public class CreatePlan {
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

	public static InlineResponse201 run() {
		// Required to make the sample code ActivatePlan.java work
		String planStatus = "DRAFT";
	
		CreatePlanRequest requestObj = new CreatePlanRequest();

		Rbsv1plansPlanInformation planInformation = new Rbsv1plansPlanInformation();
		planInformation.name("Gold Plan");
		planInformation.description("New Gold Plan");
		planInformation.setStatus(planStatus);
		InlineResponse200PlanInformationBillingPeriod planInformationBillingPeriod = new InlineResponse200PlanInformationBillingPeriod();
		planInformationBillingPeriod.length("1");
		planInformationBillingPeriod.unit("M");
		planInformation.billingPeriod(planInformationBillingPeriod);

		Rbsv1plansPlanInformationBillingCycles planInformationBillingCycles = new Rbsv1plansPlanInformationBillingCycles();
		planInformationBillingCycles.total("12");
		planInformation.billingCycles(planInformationBillingCycles);

		requestObj.planInformation(planInformation);

		Rbsv1plansOrderInformation orderInformation = new Rbsv1plansOrderInformation();
		Rbsv1plansOrderInformationAmountDetails orderInformationAmountDetails = new Rbsv1plansOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.billingAmount("10");
		orderInformationAmountDetails.setupFee("2");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		InlineResponse201 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PlansApi apiInstance = new PlansApi(apiClient);
			result = apiInstance.createPlan(requestObj);

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