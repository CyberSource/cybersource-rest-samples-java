package samples.Invoicing.Invoices;

import Api.InvoicesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;
import org.joda.time.LocalDate;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UpdateInvoice {
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
    public static InvoicingV2InvoicesPost201Response run() {
        String invoiceId = CreateDraftInvoice.run().getId();
        UpdateInvoiceRequest requestObj = new UpdateInvoiceRequest();

        Invoicingv2invoicesCustomerInformation customerInformation = new Invoicingv2invoicesCustomerInformation();
        customerInformation.name("New Customer Name");
        customerInformation.email("new_email@my-email.world");
        requestObj.customerInformation(customerInformation);

        Invoicingv2invoicesidInvoiceInformation invoiceInformation = new Invoicingv2invoicesidInvoiceInformation();
        invoiceInformation.description("This is after updating invoice");
        invoiceInformation.dueDate(new LocalDate("2019-07-11"));
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

        List<Invoicingv2invoicesOrderInformationLineItems> lineItems =  new ArrayList<>();
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
            result = apiInstance.updateInvoice(invoiceId, requestObj);

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
