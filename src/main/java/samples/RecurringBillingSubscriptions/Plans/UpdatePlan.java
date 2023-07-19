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

public class UpdatePlan {
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

	public static void run() {
		
		String id = CreatePlan.run().getId();
	
		UpdatePlanRequest requestObj = new UpdatePlanRequest();

		Rbsv1plansidPlanInformation planInformation = new Rbsv1plansidPlanInformation();
		planInformation.name("Gold Plan NA");
		planInformation.description("Updated Gold Plan");
		InlineResponse200PlanInformationBillingPeriod planInformationBillingPeriod = new InlineResponse200PlanInformationBillingPeriod();
		planInformationBillingPeriod.length("2");
		planInformationBillingPeriod.unit("W");
		planInformation.billingPeriod(planInformationBillingPeriod);

		Rbsv1plansPlanInformationBillingCycles planInformationBillingCycles = new Rbsv1plansPlanInformationBillingCycles();
		planInformationBillingCycles.total("11");
		planInformation.billingCycles(planInformationBillingCycles);

		requestObj.planInformation(planInformation);

		Rbsv1plansidProcessingInformation processingInformation = new Rbsv1plansidProcessingInformation();
		Rbsv1plansidProcessingInformationSubscriptionBillingOptions processingInformationSubscriptionBillingOptions = new Rbsv1plansidProcessingInformationSubscriptionBillingOptions();
		processingInformationSubscriptionBillingOptions.applyTo("ALL");
		processingInformation.subscriptionBillingOptions(processingInformationSubscriptionBillingOptions);

		requestObj.processingInformation(processingInformation);

		InlineResponse200OrderInformation orderInformation = new InlineResponse200OrderInformation();
		InlineResponse200OrderInformationAmountDetails orderInformationAmountDetails = new InlineResponse200OrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.billingAmount("11");
		orderInformationAmountDetails.setupFee("2");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PlansApi apiInstance = new PlansApi(apiClient);
			apiInstance.updatePlan(id, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}