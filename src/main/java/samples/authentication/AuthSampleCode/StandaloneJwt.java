package samples.authentication.AuthSampleCode;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.KeyStore.PasswordProtection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* <summary>
 * This class is not expected to run on Linux VMs on Travis CI.
 * This sample will not pass due to issues with JWT and keystores on Linux machines.
 * </summary>
 */
public class StandaloneJwt {
    private static String merchantId = "testrest";
    private static String requestHost = "apitest.cybersource.com";

    /* <summary>
     * This is standalone code that show cases how to generate headers for CyberSource REST API - POST and GET calls.
     * This sample code has sample Mercahnt credentails (testrest) with .p12(At same dir level) that you can also use for testing.
     * CyberSource Business Center - https://ebc2test.cybersource.com/ebc2/
     * Instructions on generating your own .P12 from CyberSource Business Center - https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/authentication/createCertSharedKey.html
     * Also, To understand details about CyberSource JWT headers, please check Authentication guide - https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/authentication/GenerateHeader/jwtTokenAuthentication.html
     * This sample app demonstrates calling the CyberSource REST API to fetch report and charge a credit card
     * including building the JWT token for API authentication
     * </summary>
     */
    public static void main(String[] args) throws Exception {
    	try
        {
            String request = "{\n" +
		                    "  \"clientReferenceInformation\": {\n" +
		                    "    \"code\": \"TC50171_3\"\n" +
		                    "  },\n" +
		                    "  \"processingInformation\": {\n" +
		                    "    \"commerceIndicator\": \"internet\"\n" +
		                    "  },\n" +
		                    "  \"orderInformation\": {\n" +
		                    "    \"billTo\": {\n" +
		                    "      \"firstName\": \"john\",\n" +
		                    "      \"lastName\": \"doe\",\n" +
		                    "      \"address1\": \"201 S. Division St.\",\n" +
		                    "      \"postalCode\": \"48104-2201\",\n" +
		                    "      \"locality\": \"Ann Arbor\",\n" +
		                    "      \"administrativeArea\": \"MI\",\n" +
		                    "      \"country\": \"US\",\n" +
		                    "      \"phoneNumber\": \"999999999\",\n" +
		                    "      \"email\": \"test@cybs.com\"\n" +
		                    "    },\n" +
		                    "    \"amountDetails\": {\n" +
		                    "      \"totalAmount\": \"10.00\",\n" +
		                    "      \"currency\": \"USD\"\n" +
		                    "    }\n" +
		                    "  },\n" +
		                    "  \"paymentInformation\": {\n" +
		                    "    \"card\": {\n" +
		                    "      \"expirationYear\": \"2031\",\n" +
		                    "      \"number\": \"5555555555554444\",\n" +
		                    "      \"securityCode\": \"123\",\n" +
		                    "      \"expirationMonth\": \"12\"\n" +
		                    "    }\n" +
		                    "  }\n" +
		                    "}";
            
            // HTTP GET request
            System.out.println("\n\nSample 1: GET call - CyberSource Reporting API - HTTP GET Reporting request");
            int getStatusCode = getCall(request);
            
            if (getStatusCode != 0) {
            	System.out.println("STATUS : ERROR (HTTP Status = " + getStatusCode + ")");
            } else {
            	System.out.println("STATUS : SUCCESS (HTTP Status = " + getStatusCode + ")");
            }
            
            // HTTP POST request
            System.out.println("\n\nSample 2: POST call - CyberSource Payments API - HTTP POST Payment request");
            int postStatusCode = postCall(request);
            
            if (postStatusCode != 0) {
            	System.out.println("STATUS : ERROR (HTTP Status = " + postStatusCode + ")");
            } else {
            	System.out.println("STATUS : SUCCESS (HTTP Status = " + postStatusCode + ")");
            }
        }
        catch (Exception e)
        {
        	System.out.println("ERROR : " + e.getMessage());
        	System.out.println("STACKTRACE : " + e.getStackTrace());
        }
    }

	private static int getCall(String request) {
		int responseStatus = 0;
		
		String resource = "/reporting/v3/reports?startTime=2020-04-01T00:00:00.0Z&endTime=2020-04-05T23:59:59.0Z&timeQueryType=executedTime&reportMimeType=application/xml";
		
		/* HTTP connection */
        URL url;
		try {
			url = new URL("https://" + requestHost + resource);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			System.out.println("\n -- RequestURL -- ");
	        System.out.println("\tURL : " + url);
	        System.out.println("\n -- HTTP Headers -- ");
	        System.out.println("\tContent-Type : " + "application/json");
	        System.out.println("\tv-c-merchant-id : " + merchantId);
	        System.out.println("\tHost : " + requestHost);
			
			String token = generateJWT(request, "GET");
			
	        System.out.println("\n -- TOKEN -- ");
	        System.out.println(token);
	        
	        con.setRequestMethod("GET");
	        con.setRequestProperty("Authorization", token);
	        con.setRequestProperty("Accept", "application/hal+json");
	        con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	        con.setRequestProperty("Host", requestHost);
	        
	        int responseCode = con.getResponseCode();
	        String responseHeader = con.getHeaderField("v-c-correlation-id");	        

	        System.out.println("\n -- Response Message -- " );
	        System.out.println("\tResponse Code :" + responseCode);
	        System.out.println("\tv-c-correlation-id :" + responseHeader);
	        
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            
	            in.close();
	            
	            System.out.println("\tResponse Payload :\n" + response.toString());
	        } else {
	        	responseStatus = -1;
	        }
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		return responseStatus;
	}

	private static int postCall(String request) {
		int responseStatus = 0;
		
		String resource = "/pts/v2/payments";
		
		/* HTTP connection */
        URL url;
		try {
			url = new URL("https://" + requestHost + resource);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			System.out.println("\n -- RequestURL -- ");
	        System.out.println("\tURL : " + url);
	        System.out.println("\n -- HTTP Headers -- ");
	        System.out.println("\tContent-Type : " + "application/json");
	        System.out.println("\tv-c-merchant-id : " + merchantId);
	        System.out.println("\tHost : " + requestHost);
			
			String token = generateJWT(request, "POST");
			
	        System.out.println("\n -- TOKEN -- ");
	        System.out.println(token);
	        
	        con.setRequestMethod("POST");
	        con.setDoOutput(true);
	        con.setRequestProperty("Authorization", token);
	        con.setRequestProperty("Accept", "application/hal+json;charset=utf-8");
	        con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	        con.setRequestProperty("Host", requestHost);
	        
	        try(OutputStream outputStream = con.getOutputStream()) {
	        	byte[] input = request.getBytes("utf-8");
	        	outputStream.write(input, 0, input.length);
	        }
	        
	        int responseCode = con.getResponseCode();
	        String responseHeader = con.getHeaderField("v-c-correlation-id");

	        System.out.println("\n -- Response Message -- " );
	        System.out.println("\tResponse Code :" + responseCode);
	        System.out.println("\tv-c-correlation-id :" + responseHeader);
	        
	        if (responseCode == 200 || responseCode == 201) {
	        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            
	            in.close();
	            
	            System.out.println("\tResponse Payload :\n" + response.toString());
	        } else {
	        	responseStatus = -1;
	        }
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		return responseStatus;
	}

    /* <summary>
     * This method demonstrates the creation of the JWT Authentication credential
     * Takes Request Paylaod and Http method(GET/POST) as input.
     * </summary>
     * <param name="request">Value from which to generate JWT</param>
     * <param name="method">The HTTP Verb that is needed for generating the credential</param>
     * <returns>String containing the JWT Authentication credential</returns>
     */
    private static String generateJWT(String request, String method) {
        String token = "TOKEN_PLACEHOLDER";
        System.out.println("\tMethod : " + method);

        try {
        	KeyStore merchantKeyStore = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
        	FileInputStream keyFile = new FileInputStream("src/main/resources/testrest.p12");
        	
			merchantKeyStore.load(keyFile, merchantId.toCharArray());
			
			String merchantKeyAlias = null;
			Enumeration<String> enumKeyStore = merchantKeyStore.aliases();
			ArrayList<String> array = new ArrayList<String>();
			
			while (enumKeyStore.hasMoreElements()) {
				merchantKeyAlias = (String) enumKeyStore.nextElement();
				array.add(merchantKeyAlias);
				if (merchantKeyAlias.contains(merchantId)) {
					break;
				}
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Initialize private key from certificate        	
			PrivateKeyEntry e = (PrivateKeyEntry) merchantKeyStore.getEntry(merchantKeyAlias,
					new PasswordProtection(merchantId.toCharArray()));
			
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) e.getPrivateKey();
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Initialize certificate
			merchantKeyAlias = keyAliasValidator(array, merchantId);
			
			e = (PrivateKeyEntry) merchantKeyStore.getEntry(merchantKeyAlias,
					new PasswordProtection(merchantId.toCharArray()));
			
			X509Certificate certificate = (X509Certificate) e.getCertificate();
			
			String encryptedSigMessage = "";

			if (request != null && !request.isEmpty()) {
				MessageDigest jwtBody = MessageDigest.getInstance("SHA-256");
				byte[] jwtClaimSetBody = jwtBody.digest(request.getBytes());
				encryptedSigMessage = Base64.getEncoder().encodeToString(jwtClaimSetBody);
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Get JWT
			String claimSet = null;
			if (method.equalsIgnoreCase("GET")) {
				claimSet = "{\n            \"iat\":\"" + ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
						+ "\"\n} \n\n";
			} else if (method.equalsIgnoreCase("POST")) {
				claimSet = "{\n            \"digest\":\"" + encryptedSigMessage
						+ "\",\n            \"digestAlgorithm\":\"SHA-256\",\n            \"iat\":\""
						+ ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT) + "\"\n} \n\n";
			}
			
			/* Preparing the Signature Header. */
			HashMap<String, Object> customHeaders = new HashMap<String, Object>();
			if (merchantId != null) {
				customHeaders.put(JWTCryptoProcessor.MERCHANT_ID, merchantId);
			}

			if (merchantId != null) {
				customHeaders.put("v-c-partner-id", merchantId);
			}

			JWTCryptoProcessor jwtCryptoProcessor = new JWTCryptoProcessor();

	        System.out.println("\t JWT Body : " + claimSet);
			
			token = jwtCryptoProcessor.sign(claimSet, rsaPrivateKey, certificate, customHeaders);
        }
        catch (Exception ex)
        {
            System.out.println("ERROR : " + ex.toString());
        }

        return "Bearer " + token;
    }
    
    /**
	 * @param array
	 *            -list of keyAlias.
	 * @param merchantID
	 *            -Id of merchant.
	 * @return merchantKeyalias for merchant.
	 */
	private static String keyAliasValidator(ArrayList<String> array, String merchantID) {
		int size = array.size();
		String tempKeyAlias, merchantKeyAlias, result;
		StringTokenizer str;
		for (int i = 0; i < size; i++) {
			merchantKeyAlias = array.get(i).toString();
			str = new StringTokenizer(merchantKeyAlias, ",");
			while (str.hasMoreTokens()) {
				tempKeyAlias = str.nextToken();
				if (tempKeyAlias.contains("CN")) {
					str = new StringTokenizer(tempKeyAlias, "=");
					while (str.hasMoreElements()) {
						result = str.nextToken();
						if (result.equalsIgnoreCase(merchantID)) {
							return merchantKeyAlias;
						}
					}
				}
			}
		}
		
		return null;
	}
}

class JWTCryptoProcessor {
	public static String MERCHANT_ID = "v-c-merchant-id";
	
	public String sign(String content, PrivateKey privateKey, X509Certificate x509Certificate,
			Map<String, Object> customHeaders) {
		return serializeToken(signPayload(content, privateKey, x509Certificate, customHeaders));
	}
	
	private String serializeToken(JOSEObject joseObject) {
		return joseObject.serialize();
	}
	
	private JOSEObject signPayload(String content, PrivateKey privateKey, X509Certificate x509Certificate,
			Map<String, Object> customHeaders) {
		if ((content == null) || (content.trim().length() == 0) || (x509Certificate == null) || (privateKey == null)) {
			return null;
		}
		String serialNumber = null;
		String serialNumberPrefix = "SERIALNUMBER=";
		String principal = x509Certificate.getSubjectDN().getName().toUpperCase();
		int beg = principal.indexOf(serialNumberPrefix);
		if (beg >= 0) {
			int end = principal.indexOf(",", beg);
			if (end == -1) {
				end = principal.length();
			}
			serialNumber = principal.substring(beg + serialNumberPrefix.length(), end);
		} else {
			serialNumber = x509Certificate.getSerialNumber().toString();
		}
		List<com.nimbusds.jose.util.Base64> x5cBase64List = new ArrayList<>();
		try {
			x5cBase64List.add(com.nimbusds.jose.util.Base64.encode(x509Certificate.getEncoded()));
		} catch (CertificateEncodingException e) {
			return null;
		}
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
		Payload payload = new Payload(content);

		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
										   .customParams(customHeaders).keyID(serialNumber)
										   .x509CertChain(x5cBase64List).build();
		JWSObject jwsObject = new JWSObject(jwsHeader, payload);
		try {
			RSASSASigner signer = new RSASSASigner(rsaPrivateKey);
			jwsObject.sign(signer);
			if (!jwsObject.getState().equals(JWSObject.State.SIGNED)) {
				return null;
			}
		} catch (JOSEException joseException) {
			return null;
		}
		return jwsObject;
	}
}
