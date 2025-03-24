//package samples.authentication.AuthSampleCode;
//
//import java.lang.invoke.MethodHandles;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//
//public class reportDownload {
//	// Before using this example, replace the generic values with your merchant ID,
//	// shared secrect key, etc
//	// Note: please use %20 for space if your report name has space eg.your report
//	// name is "my test", then use "my20%test"
//	private final String USER_AGENT = "Mozilla/5.0";
//	public String merchantKeyId = "08c94330-f618-42a3-b09d-e1e43be5efda";
//	public String merchantsecretKey = "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=";
//	public String merchantId = "testrest";
//
//	/*
//	 * Add Request Header :: "Host" Sandbox Host: apitest.cybersource.com Production
//	 * Host: api.cybersource.com
//	 */
//	public String requestHost = "apitest.cybersource.com";
//	public String gmtDateTime = "";
//	public static String resourceURI = "/reporting/v2/reportSubscriptions/TRRReport?organizationId=testrest";
//	public static String resourceFile = "report";
//	private static final String FILE_PATH = "src/main/resources/";
//
//	public static void WriteLogAudit(int status) {
//		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
//		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
//	}
//
//	public static void main(String[] args) throws Exception {
//
//		/*
//		 * Calculate HTTP Signature Digest and Authenticate Cybersource Report API using
//		 * HTTP Client
//		 */
//		reportDownload http = new reportDownload();
//
//		try {
//			http.sendGet(resourceURI, resourceFile);
//			WriteLogAudit(200);
//		} catch (Exception e){
//			WriteLogAudit(200);
//			throw e;
//		}
//	}
//
//	// HTTP GET request
//	private void sendGet(String resourceURI, String resourceFile) throws Exception {
//
//		String url = "https://" + requestHost + resourceURI;
//
//		/* HTTP connection */
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//		/*
//		 * Add Request Header :: "v-c-merchant-id" set value to Cybersource Merchant ID
//		 * or v-c-merchant-id This ID can be found on EBC2 portal
//		 */
//		con.setRequestProperty("v-c-merchant-id", merchantId);
//
//		/*
//		 * Add Request Header :: "Date" The date and time that the message was
//		 * originated from. "HTTP-date" format as defined by RFC7231.
//		 */
//		gmtDateTime = getDate();
//		con.setRequestProperty("Date", gmtDateTime);
//
//		/*
//		 * Add Request Header :: "Host" Sandbox Host: apitest.cybersource.com Production
//		 * Host: api.cybersource.com
//		 */
//		con.setRequestProperty("Host", requestHost);
//
//		/*
//		 * Add Request Header :: "Signature" Signature header contains keyId, algorithm,
//		 * headers and signature as paramters method getSignatureHeader() has more
//		 * details
//		 */
//		StringBuilder signatureHeaderValue = getSignatureHeader("GET", resourceURI, resourceFile);
//		con.setRequestProperty("Signature", signatureHeaderValue.toString());
//
//		/* HTTP Method GET */
//		con.setRequestMethod("GET");
//
//		/* Addition Request Headers */
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		con.setRequestProperty("Content-Type", "application/json");
//
//		/* Establishing HTTP connection */
////		int responseCode = con.getResponseCode();
////		String responseHeader = con.getHeaderField("v-c-correlation-id");
//
//		/* Download Report */
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		org.apache.commons.io.IOUtils.copy(con.getInputStream(), outputStream);
//		byte[] bytes = outputStream.toByteArray();
//		BufferedReader br = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));
//
//		String output;
//		String reportType = "csv";
//		System.out.println("Output from Server .... \n");
//		while ((output = br.readLine()) != null) {
//			if (output.contains("xml")) {
//				reportType = "xml";
//			}
//		}
//
//		BufferedReader br_write = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));
//		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_PATH + resourceFile + "." + reportType)));
//		while ((output = br_write.readLine()) != null) {
//			bw.write(output + "\n");
//		}
//
//		con.disconnect();
//		bw.close();
//
//	}
//
//	private String getDate() {
//		/* This Method returns Date in GMT format as defined by RFC7231. */
//		return (DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))));
//	}
//
//	private StringBuilder getSignatureHeader(String httpMethod, String resourceURI, String resourceFile)
//			throws Exception {
//
//		/*
//		 * This method return SignatureHeader Value that contains following paramters
//		 * keyid -- Merchant ID obtained from EBC2 portal algorithm -- Should have value
//		 * as "HmacSHA256" headers -- List of all header name passed in the Signature
//		 * paramter below String getHeaders = "host date request-target" + " " +
//		 * "v-c-merchant-id"; String postHeaders =
//		 * "host date request-target digest v-c-merchant-id"; Note: Digest is not
//		 * passed for GET calls signature -- Signature header has paramter called
//		 * signature Paramter 'Signature' must contain all the paramters mentioned in
//		 * header above in given order
//		 */
//
//		StringBuilder signatureHeaderValue = new StringBuilder();
//
//		/* KeyId is the key obtained from EBC */
//		signatureHeaderValue.append("keyid=\"" + merchantKeyId + "\"");
//
//		/* Algorithm should be always HmacSHA256 for http signature */
//		signatureHeaderValue.append(", algorithm=\"HmacSHA256\"");
//
//		/*
//		 * Headers - list is choosen based on HTTP method. Digest is not required for
//		 * GET Method
//		 */
//		String getHeaders = "host date request-target" + " " + "v-c-merchant-id";
//		String postHeaders = "host date request-target digest v-c-merchant-id";
//
//		if (httpMethod.equalsIgnoreCase("GET"))
//			signatureHeaderValue.append(", headers=\"" + getHeaders + "\"");
//		else if (httpMethod.equalsIgnoreCase("POST"))
//			signatureHeaderValue.append(", headers=\"" + postHeaders + "\"");
//		else if (httpMethod.equalsIgnoreCase("PUT"))
//			signatureHeaderValue.append(", headers=\"" + postHeaders + "\"");
//
//		/* Get Value for paramter 'Signature' to be passed to Signature Header */
//		String signatureValue = getSignatureParam(httpMethod, resourceURI, resourceFile);
//		signatureHeaderValue.append(", signature=\"" + signatureValue + "\"");
//
//		return signatureHeaderValue;
//	}
//
//	private String getSignatureParam(String httpMethod, String resourceURI, String resourceFile) throws Exception {
//
//		/*
//		 * This method returns value for paramter Signature which is then passed to
//		 * Signature header paramter 'Signature' is calucated based on below key values
//		 * and then signed with SECRET KEY - host: Sandbox (apitest.cybersource.com) or
//		 * Production (api.cybersource.com) hostname date: "HTTP-date" format as defined
//		 * by RFC7231. request-target: Should be in format of httpMethod: path
//		 * Example: "post /pts/v2/payments" Digest: Only needed for POST calls.
//		 * digestString = BASE64( HMAC-SHA256 ( Payload )); Digest:
//		 * â€œSHA-256=â€œ + digestString; v-c-merchant-id: set value to
//		 * Cybersource Merchant ID This ID can be found on EBC portal
//		 */
//
//		StringBuilder signatureString = new StringBuilder();
//		signatureString.append('\n');
//		signatureString.append("host");
//		signatureString.append(": ");
//		signatureString.append(requestHost);
//		signatureString.append('\n');
//		signatureString.append("date");
//		signatureString.append(": ");
//		signatureString.append(gmtDateTime);
//		signatureString.append('\n');
//		signatureString.append("request-target");
//		signatureString.append(": ");
//
//		String getRequestTarget = "get " + resourceURI;
//
//		if (httpMethod.equalsIgnoreCase("GET"))
//			signatureString.append(getRequestTarget);
//		signatureString.append('\n');
//
//		if (httpMethod.equalsIgnoreCase("POST") || httpMethod.equalsIgnoreCase("PUT")) {
//			signatureString.append("digest");
//			signatureString.append(": ");
//			signatureString.append(getDigest(resourceFile));
//			signatureString.append('\n');
//		}
//
//		signatureString.append("v-c-merchant-id");
//		signatureString.append(": ");
//		signatureString.append(merchantId);
//		signatureString.delete(0, 1);
//		String signatureStr = signatureString.toString();
//
//		/*
//		 * Signature string generated from above parameters is Signed with SecretKey
//		 * hased with SHA256 and base64 encoded. Secret Key is Base64 decoded before
//		 * signing
//		 */
//		SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(merchantsecretKey), "HmacSHA256");
//		Mac aKeyId = Mac.getInstance("HmacSHA256");
//		aKeyId.init(secretKey);
//		aKeyId.update(signatureStr.getBytes());
//		byte[] aHeaders = aKeyId.doFinal();
//		String base64EncodedSignature = Base64.getEncoder().encodeToString(aHeaders);
//
//		return base64EncodedSignature;
//	}
//
//	private String getDigest(String resourceFile) throws NoSuchAlgorithmException, IOException {
//
//		/*
//		 * This method return Digest value which is SHA-256 hash of payload that is
//		 * BASE64 encoded
//		 */
//		String messageBody = getPayload(resourceFile);
//		MessageDigest digestString = MessageDigest.getInstance("SHA-256");
//		byte[] digestBytes = digestString.digest(messageBody.getBytes("UTF-8"));
//
//		String bluePrint = Base64.getEncoder().encodeToString(digestBytes);
//		bluePrint = "SHA-256=" + bluePrint;
//		return bluePrint;
//	}
//
//	private String getPayload(String resourceFile) throws IOException {
//
//		URL path = reportDownload.class.getResource("resource/" + resourceFile);
//		File fr = new File(path.getFile());
//		BufferedReader buf = new BufferedReader(new FileReader(fr));
//		String line = buf.readLine();
//		StringBuilder sb = new StringBuilder();
//		while (line != null) {
//			sb.append(line).append("\n");
//			line = buf.readLine();
//		}
//		String messageBody = sb.toString();
//		buf.close();
//		return messageBody;
//	}
//}