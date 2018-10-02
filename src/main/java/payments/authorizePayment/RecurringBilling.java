package payments.authorizePayment;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCustomer;

public class RecurringBilling {
	private static String responseCode = null;
	private static String responseMsg = null;

	static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC45572-1");
		request.clientReferenceInformation(client);

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("5432");
		amountDetails.currency("USD");
		
		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		V2paymentsPaymentInformationCustomer customer=new V2paymentsPaymentInformationCustomer();
		customer.customerId("5303162577043192705841");
		
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.customer(customer);
		request.setPaymentInformation(paymentInformation);

		return request;

	}
	 public static void main(String args[]) throws Exception {
			new RecurringBilling();
		}

		public RecurringBilling() throws Exception {
			process();
		}
		

	public static void process() throws Exception {

		try {
			request = getRequest();

			PaymentApi paymentApi = new PaymentApi();
			paymentApi.createPayment(request);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
