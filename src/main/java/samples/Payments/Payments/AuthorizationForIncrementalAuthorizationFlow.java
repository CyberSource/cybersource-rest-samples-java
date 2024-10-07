package samples.Payments.Payments;

import java.*;
import java.lang.invoke.MethodHandles;
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

public class AuthorizationForIncrementalAuthorizationFlow {
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
	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(false);
		processingInformation.industryDataType("lodging");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4111111111111111");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2031");
		paymentInformationCard.type("001");
		paymentInformation.card(paymentInformationCard);

		Ptsv2paymentsPaymentInformationTokenizedCard paymentInformationTokenizedCard = new Ptsv2paymentsPaymentInformationTokenizedCard();
		paymentInformationTokenizedCard.securityCode("123");
		paymentInformation.tokenizedCard(paymentInformationTokenizedCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("20");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Smith");
		orderInformationBillTo.address1("201 S. Division St.");
		orderInformationBillTo.address2("Suite 500");
		orderInformationBillTo.locality("Ann Arbor");
		orderInformationBillTo.administrativeArea("MI");
		orderInformationBillTo.postalCode("12345");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("null@cybersource.com");
		orderInformationBillTo.phoneNumber("514-670-8700");
		orderInformation.billTo(orderInformationBillTo);

		Ptsv2paymentsOrderInformationShipTo orderInformationShipTo = new Ptsv2paymentsOrderInformationShipTo();
		orderInformationShipTo.firstName("Olivia");
		orderInformationShipTo.lastName("White");
		orderInformationShipTo.address1("1295 Charleston Rd");
		orderInformationShipTo.address2("Cube 2386");
		orderInformationShipTo.locality("Mountain View");
		orderInformationShipTo.administrativeArea("CA");
		orderInformationShipTo.postalCode("94041");
		orderInformationShipTo.country("AE");
		orderInformationShipTo.phoneNumber("650-965-6000");
		orderInformation.shipTo(orderInformationShipTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsMerchantInformation merchantInformation = new Ptsv2paymentsMerchantInformation();
		Ptsv2paymentsMerchantInformationMerchantDescriptor merchantInformationMerchantDescriptor = new Ptsv2paymentsMerchantInformationMerchantDescriptor();
		merchantInformationMerchantDescriptor.contact("965-6000");
		merchantInformation.merchantDescriptor(merchantInformationMerchantDescriptor);

		requestObj.merchantInformation(merchantInformation);

		Ptsv2paymentsConsumerAuthenticationInformation consumerAuthenticationInformation = new Ptsv2paymentsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.cavv("ABCDEabcde12345678900987654321abcdeABCDE");
		consumerAuthenticationInformation.xid("12345678909876543210ABCDEabcdeABCDEF1234");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Ptsv2paymentsInstallmentInformation installmentInformation = new Ptsv2paymentsInstallmentInformation();
		installmentInformation.amount("1200");
		installmentInformation.frequency("W");
		installmentInformation.sequence(34);
		installmentInformation.totalAmount("2000");
		installmentInformation.totalCount(12);
		requestObj.installmentInformation(installmentInformation);

		Ptsv2paymentsTravelInformation travelInformation = new Ptsv2paymentsTravelInformation();
		travelInformation.duration("3");
		Ptsv2paymentsTravelInformationLodging travelInformationLodging = new Ptsv2paymentsTravelInformationLodging();
		travelInformationLodging.checkInDate("11062019");
		travelInformationLodging.checkOutDate("11092019");

		List <Ptsv2paymentsTravelInformationLodgingRoom> room =  new ArrayList <Ptsv2paymentsTravelInformationLodgingRoom>();
		Ptsv2paymentsTravelInformationLodgingRoom room1 = new Ptsv2paymentsTravelInformationLodgingRoom();
		room1.dailyRate("1.50");
		room1.numberOfNights(5);
		room.add(room1);

		Ptsv2paymentsTravelInformationLodgingRoom room2 = new Ptsv2paymentsTravelInformationLodgingRoom();
		room2.dailyRate("11.50");
		room2.numberOfNights(5);
		room.add(room2);

		travelInformationLodging.room(room);

		travelInformationLodging.smokingPreference("yes");
		travelInformationLodging.numberOfRooms(1);
		travelInformationLodging.numberOfGuests(3);
		travelInformationLodging.roomBedType("king");
		travelInformationLodging.roomTaxType("tourist");
		travelInformationLodging.roomRateType("sr citizen");
		travelInformationLodging.guestName("Tulasi");
		travelInformationLodging.customerServicePhoneNumber("+13304026334");
		travelInformationLodging.corporateClientCode("HDGGASJDGSUY");
		travelInformationLodging.additionalDiscountAmount("99.123456781");
		travelInformationLodging.roomLocation("seaview");
		travelInformationLodging.specialProgramCode("2");
		travelInformationLodging.totalTaxAmount("99.1234567891");
		travelInformationLodging.prepaidCost("9999999999.99");
		travelInformationLodging.foodAndBeverageCost("9999999999.99");
		travelInformationLodging.roomTaxAmount("9999999999.99");
		travelInformationLodging.adjustmentAmount("9999999999.99");
		travelInformationLodging.phoneCost("9999999999.99");
		travelInformationLodging.restaurantCost("9999999999.99");
		travelInformationLodging.roomServiceCost("9999999999.99");
		travelInformationLodging.miniBarCost("9999999999.99");
		travelInformationLodging.laundryCost("9999999999.99");
		travelInformationLodging.miscellaneousCost("9999999999.99");
		travelInformationLodging.giftShopCost("9999999999.99");
		travelInformationLodging.movieCost("9999999999.99");
		travelInformationLodging.healthClubCost("9999999999.99");
		travelInformationLodging.valetParkingCost("9999999999.99");
		travelInformationLodging.cashDisbursementCost("9999999999.99");
		travelInformationLodging.nonRoomCost("9999999999.99");
		travelInformationLodging.businessCenterCost("9999999999.99");
		travelInformationLodging.loungeBarCost("9999999999.99");
		travelInformationLodging.transportationCost("9999999999.99");
		travelInformationLodging.gratuityAmount("9999999999.99");
		travelInformationLodging.conferenceRoomCost("9999999999.99");
		travelInformationLodging.audioVisualCost("9999999999.99");
		travelInformationLodging.nonRoomTaxAmount("9999999999.99");
		travelInformationLodging.earlyCheckOutCost("9999999999.99");
		travelInformationLodging.internetAccessCost("9999999999.99");
		travelInformation.lodging(travelInformationLodging);

		requestObj.travelInformation(travelInformation);

		Ptsv2paymentsPromotionInformation promotionInformation = new Ptsv2paymentsPromotionInformation();
		promotionInformation.additionalCode("999999.99");
		requestObj.promotionInformation(promotionInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

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
