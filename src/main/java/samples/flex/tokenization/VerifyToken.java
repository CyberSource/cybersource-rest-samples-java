package samples.flex.tokenization;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Map;

import com.cybersource.flex.sdk.exception.FlexEncodingException;
import com.cybersource.flex.sdk.exception.FlexException;
import com.cybersource.flex.sdk.exception.FlexSDKInternalException;
import com.cybersource.flex.sdk.exception.FlexSecurityException;
import com.cybersource.flex.sdk.repackaged.Base64;

public class VerifyToken {
	
	public boolean verify(PublicKey publicKey, Map<?, ?> postParams) throws FlexException {
		  if (postParams == null) throw new FlexSecurityException("A valid Map must be supplied");
		  final String signature = (String) postParams.get("signature");
		  if (signature == null) throw new FlexSecurityException("Missing required field: signature");
		  final String signedFields = (String) postParams.get("signedFields");
		  if (signedFields == null) throw new FlexSecurityException("Missing required field: signedFields");
		  StringBuilder sb = new StringBuilder();
		  for (String k : signedFields.split(",")) {
		    sb.append(',');
		    sb.append(postParams.get("" + k));
		  }
		  final String signedValues = sb.substring(1);
		  return validateTokenSignature(publicKey, signedValues, signature);
		
		}
	
	private static boolean validateTokenSignature(PublicKey publicKey, String signedFields, String signature) throws FlexException {
		boolean success=false;
		  if (publicKey == null) throw new FlexSecurityException("Must supply a valid PublicKey instance");
		  if (signature == null) throw new FlexSecurityException("Missing required field: signature");
		  if (signedFields == null) throw new FlexSecurityException("Missing required field: signedFields");
		  try {
		    final Signature signInstance = Signature.getInstance("SHA512withRSA");
		    signInstance.initVerify(publicKey);
		    signInstance.update(signedFields.getBytes());
		    success=signInstance.verify(Base64.decode(signature));
		    System.out.println(success);
		  } catch (IOException e) {
		    throw new FlexEncodingException("Unable to decode signature"+ e);
		  } catch (GeneralSecurityException e) {
		    throw new FlexSDKInternalException(e);
		  }
		return success;

	}
}
