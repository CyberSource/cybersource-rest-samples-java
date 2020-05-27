package samples.Invoicing.Invoices;

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

public class CreateDraftInvoice {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static InvoicingV2InvoicesPost201Response run() {
	
		CreateInvoiceRequest requestObj = new CreateInvoiceRequest();

		Invoicingv2invoicesCustomerInformation customerInformation = new Invoicingv2invoicesCustomerInformation();
		customerInformation.name("Tanya Lee");
		customerInformation.email("tanya.lee@my-email.world");
		requestObj.customerInformation(customerInformation);

		Invoicingv2invoicesInvoiceInformation invoiceInformation = new Invoicingv2invoicesInvoiceInformation();
		invoiceInformation.description("This is a test invoice");
		invoiceInformation.dueDate(new LocalDate("2019-07-11"));
		invoiceInformation.sendImmediately(false);
		invoiceInformation.allowPartialPayments(true);
		invoiceInformation.deliveryMode("none");
		requestObj.invoiceInformation(invoiceInformation);

		Invoicingv2invoicesOrderInformation orderInformation = new Invoicingv2invoicesOrderInformation();
		Invoicingv2invoicesOrderInformationAmountDetails orderInformationAmountDetails = new Invoicingv2invoicesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("2623.64");
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.discountAmount("126.08");
		orderInformationAmountDetails.discountPercent(new BigDecimal(5.0).setScale(2, BigDecimal.ROUND_HALF_UP));
		orderInformationAmountDetails.subAmount(new BigDecimal(2749.72).setScale(2, BigDecimal.ROUND_HALF_UP));
		orderInformationAmountDetails.minimumPartialAmount(new BigDecimal(20.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		Invoicingv2invoicesOrderInformationAmountDetailsTaxDetails orderInformationAmountDetailsTaxDetails = new Invoicingv2invoicesOrderInformationAmountDetailsTaxDetails();
		orderInformationAmountDetailsTaxDetails.type("State Tax");
		orderInformationAmountDetailsTaxDetails.amount("208.00");
		orderInformationAmountDetailsTaxDetails.rate("8.25");
		orderInformationAmountDetails.taxDetails(orderInformationAmountDetailsTaxDetails);

		Invoicingv2invoicesOrderInformationAmountDetailsFreight orderInformationAmountDetailsFreight = new Invoicingv2invoicesOrderInformationAmountDetailsFreight();
		orderInformationAmountDetailsFreight.amount("20.00");
		orderInformationAmountDetailsFreight.taxable(true);
		orderInformationAmountDetails.freight(orderInformationAmountDetailsFreight);

		orderInformation.amountDetails(orderInformationAmountDetails);


		List <Invoicingv2invoicesOrderInformationLineItems> lineItems =  new ArrayList <Invoicingv2invoicesOrderInformationLineItems>();
		Invoicingv2invoicesOrderInformationLineItems lineItems1 = new Invoicingv2invoicesOrderInformationLineItems();
		lineItems1.productSku("P653727383");
		lineItems1.productName("First line item's name");
		lineItems1.quantity(21);
		lineItems1.unitPrice("120.08");
		lineItems.add(lineItems1);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		InvoicingV2InvoicesPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			InvoicesApi apiInstance = new InvoicesApi(apiClient);
			result = apiInstance.createInvoice(requestObj);

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
