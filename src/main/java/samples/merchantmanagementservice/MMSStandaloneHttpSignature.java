package samples.merchantmanagementservice;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by shashank929 on 11/4/19.
 */
public class MMSStandaloneHttpSignature {
    /* Try with your own credentaials
     * Get  Key ID, Secret Key and Merchant Id from EBC portal
     */
    public String merchantKeyId  = "cc085277-1e0e-4cd4-bb3a-fd63b0511c31";
    public String merchantsecretKey = "4EU+9YgmAxlqKYSrNi/p1zz7MYyuDcmdLRNpZa9XkcA=";
    public String merchantId = "testmmsv2auth";

    /* Sandbox Host: apitest.cybersource.com
     * Production Host: api.cybersource.com
     */
    public static String requestHost = "apitest.cybersource.com";

    private final String USER_AGENT = "Mozilla/5.0";

    public static String gmtDateTime = "DATE_PLACEHOLDER";
    public static String postRequestTarget = "REQUEST_TARGET_PALCEHOLDER";
    public static String APINAME = "APINAME_PLACEHOLDER";
    public static String resource = "resource_PLACEHOLDER";

    public static String  payload = null;

    public static void main(String[] args) throws Exception {
        /* This Example illustrate two tests - HTTP GET and POST method with Cybersource MMS Boarding API.
         * Each test will caluate HTTP Signature Digest and Authenticate Cybersource MMS API using HTTP Client
         */
        MMSStandaloneHttpSignature http = new MMSStandaloneHttpSignature();

        // POST Example for MMS Boarding API
        postRequestTarget = "post /merchant-mgmt/v3/merchant-batch-jobs";
        APINAME = "mms";
        resource = "/merchant-mgmt/v3/merchant-batch-jobs";

        String boardingRequestId = UUID.randomUUID().toString();
        String mid = "testmmsv2auth_sdkmid_" + boardingRequestId.substring(0,7);
        payload = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<ServiceRequest xmlns=\"http://www.cybersource.com/v1/schema/massboard\"\n" +
                "                xmlns:ctv=\"http://www.cybersource.com/v1/schema/cybersourcethroughvisanet\"\n" +
                "                xmlns:mer=\"http://www.cybersource.com/v1/schema/merchant\"\n" +
                "                xmlns:ent=\"http://www.cybersource.com/v1/schema/enterprise\"\n" +
                "                xmlns:jcn=\"http://www.cybersource.com/v1/schema/jcn\"\n" +
                "                xmlns:common=\"http://www.cybersource.com/v1/schema/merchantcommons\"\n" +
                "                xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                xsi:schemaLocation=\"\n" +
                "                   http://www.cybersource.com/v1/schema/massboard MassBoard.xsd\n" +
                "                   http://www.cybersource.com/v1/schema/merchant MerchantCommonServiceType.xsd\n" +
                "                   http://www.cybersource.com/v1/schema/merchantcommons common.xsd\n" +
                "                   http://www.cybersource.com/v1/schema/cybersourcethroughvisanet CyberSourcethroughVisaNet.xsd \n" +
                "                   http://www.cybersource.com/v1/schema/jcn jcn.xsd\n" +
                "                   http://www.cybersource.com/v1/schema/enterprise Enterprise.xsd\n" +
                "                   \">\n" +
                "    <organizationId>testmmsv2auth</organizationId>\n" +
                "    <boardingRequestId>"+ boardingRequestId +"</boardingRequestId>\n" +
                "    <boardMerchant xsi:type=\"ent:enterpriseMerchantType\">\n" +
                "      <mer:merchantIdentifier>\n" +
                "      <mer:merchantId>"+mid+"</mer:merchantId>\n" +
                "      </mer:merchantIdentifier>\n" +
                "        <mer:merchantInformation>\n" +
                "      <mer:name>Visa</mer:name>\n" +
                "      <mer:address>\n" +
                "        <mer:addressLine1>900</mer:addressLine1>\n" +
                "        <mer:addressLine2>Metro Center Blvd</mer:addressLine2>\n" +
                "        <mer:city>Foster City</mer:city>\n" +
                "        <mer:state>CA</mer:state>\n" +
                "        <mer:postalOrZipCode>94404</mer:postalOrZipCode>\n" +
                "        <mer:country>USA</mer:country>\n" +
                "      </mer:address>\n" +
                "      <mer:emailAddress>test@visa.com</mer:emailAddress>\n" +
                "      <mer:phone>5107897418</mer:phone>\n" +
                "      <mer:timeZone>Pacific/Pago_Pago</mer:timeZone>\n" +
                "      <mer:websiteUrl>http://www.cybersource.com</mer:websiteUrl>\n" +
                "        </mer:merchantInformation>\n" +
                "        <mer:businessContact type=\"Business\">\n" +
                "          <mer:firstName>Shashank</mer:firstName>\n" +
                "          <mer:lastName>Wankhede</mer:lastName>\n" +
                "          <mer:emailAddress>test@visa.com</mer:emailAddress>\n" +
                "          <mer:phone>4153697852</mer:phone>\n" +
                "        </mer:businessContact>\n" +
                "        <mer:technicalContact type=\"Technical\">\n" +
                "          <mer:firstName>Shashank</mer:firstName>\n" +
                "          <mer:lastName>Wankhede</mer:lastName>\n" +
                "          <mer:emailAddress>test@visa.com</mer:emailAddress>\n" +
                "          <mer:phone>4153697852</mer:phone>\n" +
                "        </mer:technicalContact>\n" +
                "        <mer:emergencyContact type=\"Emergency\">\n" +
                "          <mer:firstName>Shashank</mer:firstName>\n" +
                "          <mer:lastName>Wankhede</mer:lastName>\n" +
                "          <mer:emailAddress>test@visa.com</mer:emailAddress>\n" +
                "          <mer:phone>4153697852</mer:phone>\n" +
                "          </mer:emergencyContact>\n" +
                "        <mer:skipProcessor>true</mer:skipProcessor>\n" +
                "      <mer:merchantType>EXTERNAL</mer:merchantType>\n" +
                "    </boardMerchant>\n" +
                "</ServiceRequest>";

        System.out.println("\n\nSample 1: POST call - CyberSource MMS API - HTTP POST Boarding request");
        System.out.println("\n Merchant " + mid + " being boarded with boardingRequestId " + boardingRequestId + "\n");
        http.sendPost("https://" + requestHost + resource, boardingRequestId);

        // GET Example for MMS API get file result
        // pass requestId received from the post call to get that file's result
        resource = "/merchant-mgmt/v3/merchant-batch-jobs/eacc383a-0498-4481-8f2f-b85896772cb0";

        System.out.println("\n\nSample 2: GET call - CyberSource MMS API - HTTP GET File result request");
        http.sendGet("https://" + requestHost + resource, boardingRequestId);
    }

    // HTTP GET request
    private void sendGet(String url, String corrId) throws Exception {
        /* HTTP connection */
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        /* Add Request Header
		 * "v-c-merchant-id" set value to Cybersource Merchant ID or v-c-merchant-id
         * This ID can be found on EBC portal
         */
        con.setRequestProperty("v-c-merchant-id", merchantId);
        con.setRequestProperty("v-c-correlation-id", corrId);

        /* Add Request Header
         * "Date" The date and time that the message was originated from.
         * "HTTP-date" format as defined by RFC7231.
         */
        gmtDateTime = getdate();

        con.setRequestProperty("date", gmtDateTime);
        con.setRequestProperty("Content-Type", "application/xml");
        con.setRequestProperty("Accept", "application/xml");
        /* Add Request Header
         * "Host" Host to send the request to.
         */
        con.setRequestProperty("Host", requestHost);

        /* Add Request Header
         * "Signature" Contains keyId, algorithm, headers and signature as paramters
         * Check getSignatureHeader() method for more details
         */
        StringBuilder signatureHeaderValue = getSignatureHeader("GET");

        con.setRequestProperty("Signature", signatureHeaderValue.toString());

        /* HTTP Method GET */
        con.setRequestMethod("GET");

        /* Establishing HTTP connection*/
        int responseCode = con.getResponseCode();
        String responseHeader = con.getHeaderField("v-c-correlation-id");

        System.out.println("\n -- RequestURL -- ");
        System.out.println("\tURL : " + url);
        System.out.println("\n -- HTTP Headers -- ");
        System.out.println("\tContent-Type : " + "application/xml");
        System.out.println("\tAccept : " + "application/xml");
        System.out.println("\tv-c-merchant-id : " + merchantId);
        System.out.println("\tDate : " + gmtDateTime);
        System.out.println("\tHost : " + requestHost);
        System.out.println("\tSignature : " + signatureHeaderValue);
        System.out.println("\n -- Response Message -- " );
        System.out.println("\tResponse Code :" + responseCode);
        System.out.println("\tv-c-correlation-id :" + responseHeader);

        /* Reading Response Message */
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        /* print Response */
        System.out.println("\tResponse Payload :\n" + response.toString());
    }

    // HTTP POST request
    private void sendPost(String url, String corrId) throws Exception {
        /* HTTP connection */
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        /* Add Request Header
         * "v-c-merchant-id" set value to Cybersource Merchant ID or v-c-merchant-id
         * This ID can be found on EBC portal
         */
        con.setRequestProperty("v-c-merchant-id", merchantId);
        con.setRequestProperty("v-c-correlation-id", corrId);

        /* Add Request Header
         * "Date" The date and time that the message was originated from.
         * "HTTP-date" format as defined by RFC7231.
         */
        gmtDateTime = getdate();

        con.setRequestProperty("date", gmtDateTime);

        /* Add Request Header
         * "Host" Name of the host to send the request to.
         */
        con.setRequestProperty("Host", requestHost);

        /* Add Request Header
		 * "Digest" SHA-256 hash of payload that is BASE64 encoded
		 */
        con.setRequestProperty("Digest", getDigest());

        /* Add Request Header
         * "Signature" Contains keyId, algorithm, headers and signature as paramters
         * Check getSignatureHeader() method for more details
         */
        StringBuilder signatureHeaderValue = getSignatureHeader("POST");

        con.setRequestProperty("Signature", signatureHeaderValue.toString());

        /* HTTP Method POST */
        con.setRequestMethod("POST");

        /* Additional Request Headers */
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/xml");

        // Send POST request
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(getPayload().getBytes("UTF-8"));
        wr.flush();
        wr.close();

        /* Establishing HTTP connection*/
        int responseCode = con.getResponseCode();

        String responseHeader = con.getHeaderField("v-c-correlation-id");
        System.out.println("\n -- RequestURL -- ");
        System.out.println("\tURL : " + url);
        System.out.println("\n -- HTTP Headers -- ");
        System.out.println("\tContent-Type : " + "application/xml");
        System.out.println("\tv-c-merchant-id : " + merchantId);
        System.out.println("\tDate : " + gmtDateTime);
        System.out.println("\tHost : " + requestHost);
        System.out.println("\tDigest : " + getDigest());
        System.out.println("\tSignature : " + signatureHeaderValue);
        System.out.println("\n -- Response Message -- " );
        System.out.println("\tResponse Code :" + responseCode);
        System.out.println("\tv-c-correlation-id :" + responseHeader);

        try {
        	/* Reading Response Message */
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            /* print Response */
            System.out.println("\tResponse Payload :\n" + response.toString());
        } catch (Exception exception) {
            // Output unexpected IOExceptions
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            /* print Response */
            System.out.println("Response Payload : " + response.toString());
            System.out.println(exception);
        }
    }

    private String getdate() {
        /*  This Method returns Date in GMT format as defined by RFC7231. */
        return(DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))));
    }

    private StringBuilder getSignatureHeader(String httpMethod) throws Exception {
        /* This method return SignatureHeader Value that contains following paramters
         * keyid -- Merchant ID obtained from EBC portal
         * algorithm -- Should have value as "HmacSHA256"
         * headers -- List of all header name passed in the Signature paramter below
         *            String getHeaders = "host date request-target" + " " + "v-c-merchant-id";
         *            String postHeaders = "host date request-target digest v-c-merchant-id";
         *            Note: Digest is not passed for GET calls
         * signature -- Signature header has paramter called signature
         *              Paramter 'Signature' must contain all the paramters mentioned in header above in given order
         */
        StringBuilder signatureHeaderValue = new StringBuilder();

        /* KeyId is the key obtained from EBC */
        signatureHeaderValue.append("keyid=\"" + merchantKeyId + "\"");

        /* Algorithm should be always HmacSHA256 for http signature */
        signatureHeaderValue.append(", algorithm=\"HmacSHA256\"");

        /* Headers - list is choosen based on HTTP method. Digest is not required for GET Method */
        String getHeaders = "host date request-target" + " " + "v-c-merchant-id";
        String postHeaders = "host date request-target digest v-c-merchant-id";

        if(httpMethod.equalsIgnoreCase("GET"))
            signatureHeaderValue.append(", headers=\"" + getHeaders + "\"");
        else if(httpMethod.equalsIgnoreCase("POST"))
            signatureHeaderValue.append(", headers=\"" + postHeaders + "\"");

        /* Get Value for paramter 'Signature' to be passed to Signature Header */
        String signatureValue = getSignatureParam(httpMethod);
        signatureHeaderValue.append(", signature=\"" + signatureValue + "\"");

        return signatureHeaderValue;
    }

    private String getSignatureParam(String httpMethod)  throws Exception{
        /* This method returns value for paramter Signature which is then passed to Signature header
         * paramter 'Signature' is calucated based on below key values and then signed with SECRET KEY -
         * host: Sandbox (apitest.cybersource.com) or Production (api.cybersource.com) hostname
         * date: "HTTP-date" format as defined by RFC7231.
         * request-target: Should be in format of httpMethod: path
         *                   Example: "post /merchant-mgmt/v3/merchant-batch-jobs"
         * Digest: Only needed for POST calls.
         *          digestString = BASE64( HMAC-SHA256 ( Payload ));
         *          Digest: SHA-256=  digestString;
         * v-c-merchant-id: set value to Cybersource Merchant ID
         *                   This ID can be found on EBC portal
         */
        StringBuilder signatureString = new StringBuilder();
        signatureString.append('\n');
        signatureString.append("host");
        signatureString.append(": ");
        signatureString.append(requestHost);
        signatureString.append('\n');
        signatureString.append("date");
        signatureString.append(": ");
        signatureString.append(gmtDateTime);
        signatureString.append('\n');
        signatureString.append("request-target");
        signatureString.append(": ");

        String getRequestTarget = "get " + resource;

        if(httpMethod.equalsIgnoreCase("GET"))
            signatureString.append(getRequestTarget);
        else if(httpMethod.equalsIgnoreCase("POST"))
            signatureString.append(postRequestTarget);

        signatureString.append('\n');

        if(httpMethod.equalsIgnoreCase("POST")) {
            signatureString.append("digest");
            signatureString.append(": ");
            signatureString.append(getDigest());
            signatureString.append('\n');
        }

        signatureString.append("v-c-merchant-id");
        signatureString.append(": ");
        signatureString.append(merchantId);
        signatureString.delete(0, 1);

        String signatureStr = signatureString.toString();

        /* Signature string generated from above parameters is Signed with SecretKey hased with SHA256 and base64 encoded.
         *  Secret Key is Base64 decoded before signing
         */
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(merchantsecretKey), "HmacSHA256");
        Mac aKeyId = Mac.getInstance("HmacSHA256");
        aKeyId.init(secretKey);
        aKeyId.update(signatureStr.getBytes());
        byte[] aHeaders = aKeyId.doFinal();
        String base64EncodedSignature = Base64.getEncoder().encodeToString(aHeaders);

        return base64EncodedSignature;
    }

    private String getDigest() throws NoSuchAlgorithmException, IOException {
        /* This method return Digest value which is SHA-256 hash of payload that is BASE64 encoded */
        String messageBody = getPayload();

        MessageDigest digestString = MessageDigest.getInstance("SHA-256");

        byte[] digestBytes = digestString.digest(messageBody.getBytes("UTF-8"));

        String bluePrint = Base64.getEncoder().encodeToString(digestBytes);
        bluePrint = "SHA-256="+ bluePrint;

        return bluePrint;
    }

    private String getPayload() throws IOException {
        String messageBody = payload;
        return messageBody;
    }
}
