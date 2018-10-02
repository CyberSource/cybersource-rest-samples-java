package payments.authorizePayment.simpleAuth;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsAggregatorInformation;
import Model.V2paymentsAggregatorInformationSubMerchant;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationFluidData;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsProcessingInformation;

public class RetailPosStandardSwipe {
	private String responseCode=null;
	private String responseMsg=null;
	
	CreatePaymentRequest request;
	
   private CreatePaymentRequest getRequest(){
		 request=new CreatePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_16");
		request.clientReferenceInformation(client);
		
		V2paymentsPointOfSaleInformation pointOfSaleInformation=new V2paymentsPointOfSaleInformation();
		pointOfSaleInformation.cardPresent(true);
		pointOfSaleInformation.catLevel(1);
		//field missing in model
		//pointOfSaleInformation.endlessAisleTransactionIndicator(true);
		pointOfSaleInformation.entryMode("swiped");
		pointOfSaleInformation.terminalCapability(1);
		request.pointOfSaleInformation(pointOfSaleInformation);
		
		V2paymentsProcessingInformation processingInformation=new V2paymentsProcessingInformation();
		processingInformation.commerceIndicator("retail");
		processingInformation.paymentSolution("011");
		request.processingInformation(processingInformation);
		
		V2paymentsAggregatorInformationSubMerchant subMerchant = new V2paymentsAggregatorInformationSubMerchant();
		subMerchant.cardAcceptorId("1234567890");
		subMerchant.country("US");
		subMerchant.phoneNumber("650-432-0000");
		subMerchant.address1("900 Metro Center");
		subMerchant.postalCode("94404-2775");
		subMerchant.locality("Foster City");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		subMerchant.region("PEN");
		subMerchant.email("test@cybs.com");
		
		
		V2paymentsAggregatorInformation aggregatorInformation = new V2paymentsAggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorId("123456789");
		request.setAggregatorInformation(aggregatorInformation);
		
		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("address2");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
        billTo.phoneNumber("999999999");
        billTo.district("MI");
        billTo.buildingNumber("123");
        billTo.company("visa");
		billTo.email("test@cybs.com");
		

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("115.0");
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		V2paymentsPaymentInformationFluidData fluidData=new V2paymentsPaymentInformationFluidData();
		fluidData.descriptor("EMV.PAYMENT.AnywhereCommerce.Walker");
		fluidData.value("ewogICJkYXRhIiA6ICJOZmNwRURiK1dLdzBnQkpsaTRcL1hlWm1ITzdUSng0bn"
				+ "RoMnc2Mk9ITVJQK3hCRlFPdFE0WWpRcnY0RmFkOHh6VExqT2VFQm5iNHFzeGZMYTNyNX"
				+ "cxVEdXblFGQnNzMWtPYnA0XC95alNtVE1JSGVjbGc5OFROaEhNb0VRcjJkRkFqYVpBTF"
				+ "AxSlBsdVhKSVwvbTZKSmVwNGh3VHRWZE16Z2laSUhnaWFCYzNXZVd1ZnYzc1l0cGRaZDZY"
				+ "ZENEUFdLeXFkYjBJdUtkdkpBPT0iLAogICJzaWduYXR1cmUiIDogIkFxck1pKzc0cm1GeVB"
				+ "KVE9HN3NuN2p5K1YxTlpBZUNJVE56TW01N1B5cmc9IiwKICAic2lnbmF0dXJlQWxnSW5mb"
				+ "yIgOiAiSE1BQ3dpdGhTSEEyNTYiLAogICJoZWFkZXIiIDogewogICAgInRyYW5zYWN0aW"
				+ "9uSWQiIDogIjE1MTU2MjI2NjIuMTcyMjIwIiwKICAgICJwdWJsaWNLZXlIYXNoIiA6ICJ"
				+ "cLzdmdldqRVhMazJPRWpcL3Z5bk1jeEZvMmRWSTlpRXVoT2Nab0tHQnpGTmM9IiwKICAgI"
				+ "CJhcHBsaWNhdGlvbkRhdGEiIDogIkN5YmVyU291cmNlLlZNcG9zS2l0IiwKICAgICJlcGh"
				+ "lbWVyYWxQdWJsaWNLZXkiIDogIk1Ga3dFd1lIS29aSXpqMENBUVlJS29aSXpqMERBUWNEU"
				+ "WdBRW1JN0tScnRNN2NNelk5Zmw2UWt2NEQzdE9jU0NYR1hoOFwvK2R4K2s5c1Zrbk05UFQ"
				+ "rOXRqMzk2YWF6QjRcL0hYaWlLRW9DXC9jUzdoSzF6UFk3MVwvN0pUUT09IgogIH0sCiAgI"
				+ "nZlcnNpb24iIDogIjEuMCIKfQ==");
		
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.fluidData(fluidData);
		request.setPaymentInformation(paymentInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new RetailPosStandardSwipe();
	}

	public RetailPosStandardSwipe() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	    
		PaymentApi paymentApi=new PaymentApi();
		paymentApi.createPayment(request);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
