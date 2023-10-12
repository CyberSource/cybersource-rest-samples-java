package samples.RecurringBillingSubscriptions.Plans;

import Api.PlansApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

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
		GetAllPlansResponsePlanInformationBillingPeriod planInformationBillingPeriod = new GetAllPlansResponsePlanInformationBillingPeriod();
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

		GetAllPlansResponseOrderInformation orderInformation = new GetAllPlansResponseOrderInformation();
		GetAllPlansResponseOrderInformationAmountDetails orderInformationAmountDetails = new GetAllPlansResponseOrderInformationAmountDetails();
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