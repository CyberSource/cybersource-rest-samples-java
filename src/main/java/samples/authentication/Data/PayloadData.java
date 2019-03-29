package samples.authentication.Data;

import com.cybersource.apisdk.model.AggregatorInformation;
import com.cybersource.apisdk.model.AmountDetails;
import com.cybersource.apisdk.model.BillTo;
import com.cybersource.apisdk.model.Card;
import com.cybersource.apisdk.model.ClientReferenceInformation;
import com.cybersource.apisdk.model.OrderInformation;
import com.cybersource.apisdk.model.Payment;
import com.cybersource.apisdk.model.PaymentInformation;
import com.cybersource.apisdk.model.ProcessingInformation;
import com.cybersource.apisdk.model.SubMerchant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PayloadData {

	public static String readData() {

		ClientReferenceInformation client = new ClientReferenceInformation();
		client.code("TC50171_3");

		ProcessingInformation processingInformation = new ProcessingInformation();
		processingInformation.commerceIndicator("internet");

		SubMerchant subMerchant = new SubMerchant();
		subMerchant.cardAcceptorID("1234567890");
		subMerchant.country("US");
		subMerchant.phoneNumber("650-432-0000");
		subMerchant.address1("900 Metro Center");
		subMerchant.postalCode("94404-2775");
		subMerchant.locality("Foster City");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		subMerchant.region("PEN");
		subMerchant.email("test@cybs.com");

		AggregatorInformation aggregatorInformation = new AggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorID("123456789");

		BillTo billTo = new BillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("Address 2");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.phoneNumber("999999999");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.company("Visa");
		billTo.email("test@cybs.com");

		AmountDetails amountDetails = new AmountDetails();
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");

		OrderInformation orderInformation = new OrderInformation(billTo, amountDetails);

		Card card = new Card();
		card.expirationYear("2031");
		card.number("5555555555554444");
		card.securityCode("123");
		card.expirationMonth("12");
		card.type("002");

		PaymentInformation paymentInformation = new PaymentInformation(card);

		Payment payments = new Payment(client, processingInformation, aggregatorInformation, orderInformation,
				paymentInformation);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// Gson gson=new Gson();
		String paymentsRequesString = gson.toJson(payments);
		return paymentsRequesString;

	}

}