package tms.paymentsWithToken;

import Api.InstrumentIdentifierApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body;
import Model.InstrumentidentifiersCard;
import Model.InstrumentidentifiersProcessingInformation;
import Model.InstrumentidentifiersProcessingInformationAuthorizationOptions;
import Model.InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiator;
import Model.InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction;

public class CreateInstrumentIdentifier {
	private String responseCode = null;
	private String responseMsg = null;
	private String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";

	Body body;

	private Body getRequest() {
		body = new Body();
		
		InstrumentidentifiersCard card=new InstrumentidentifiersCard();
		card.number("1234567890987654");
		body.card(card);
		
		InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction merchantInitiatedTransaction=new InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction();
		merchantInitiatedTransaction.previousTransactionId("123456789012345");
		
		InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiator initiator=new InstrumentidentifiersProcessingInformationAuthorizationOptionsInitiator();
		initiator.merchantInitiatedTransaction(merchantInitiatedTransaction);
		
		InstrumentidentifiersProcessingInformationAuthorizationOptions authorizationOptions=new InstrumentidentifiersProcessingInformationAuthorizationOptions();
		authorizationOptions.initiator(initiator);
		
		InstrumentidentifiersProcessingInformation processingInformation=new InstrumentidentifiersProcessingInformation();
		processingInformation.authorizationOptions(authorizationOptions);
		body.processingInformation(processingInformation);
		
		return body;

	}

	public static void main(String args[]) throws Exception {
		new CreateInstrumentIdentifier();
	}

	public CreateInstrumentIdentifier() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			body = getRequest();

			InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi();
			instrumentIdentifierApi.instrumentidentifiersPost(profileId,body);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
