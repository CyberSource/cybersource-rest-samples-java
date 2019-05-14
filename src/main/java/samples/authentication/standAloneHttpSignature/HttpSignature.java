package samples.authentication.standAloneHttpSignature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Created by glondhe on 3/11/2019.
 */

/** @noinspection ALL*/
public class HttpSignature {

    private final String USER_AGENT = "Mozilla/5.0";
    /* Try with your own credentaials
    *  Get  Key ID, Secret Key and Merchant Id from EBC portal*/
    public String merchantKeyId  = "08c94330-f618-42a3-b09d-e1e43be5efda";
    public String merchantsecretKey = "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=";
    public String merchantId = "testrest";

    public static String requestHost = "apitest.cybersource.com";
    public String gmtDateTime = "DATE_PLACEHOLDER";
    public static String postRequestTarget = "REQUEST_TARGET_PALCEHOLDER";
    public static String APINAME = "APINAME_PLACEHOLDER";
    public static String resource = "resource_PLACEHOLDER";

    public static String  payload = null;

    public static void main(String[] args) throws Exception {

        /* This Example illustrate two tests - HTTP GET and POST method with Cybersource Payments API.
        *  Each test will caluate HTTP Signature Digest and Authenticate Cybersource Payments API using HTTP Client */
        HttpSignature http = new HttpSignature();

        payload = "{\n" +
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
                "      \"totalAmount\": \"10\",\n" +
                "      \"currency\": \"USD\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"paymentInformation\": {\n" +
                "    \"card\": {\n" +
                "      \"expirationYear\": \"2031\",\n" +
                "      \"number\": \"5555555555554444\",\n" +
                "      \"securityCode\": \"123\",\n" +
                "      \"expirationMonth\": \"12\",\n" +
                "      \"type\": \"002\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //POST Example for Payments
        postRequestTarget = "post /pts/v2/payments";
        APINAME = "payments";
        System.out.println("\n\n    Test #1 - HTTP POST Payment request");
        http.sendPost("https://" + requestHost + "/pts/v2/payments");

        //GET Example for Reports
        resource = "/reporting/v3/reports?startTime=2018-10-01T00:00:00.0Z&endTime=2018-10-30T23:59:59.0Z&timeQueryType=executedTime&reportMimeType=application/xml";
        System.out.println("\n\n    Test #2 - HTTP GET Reporting request");
        http.sendGet(resource);
    }

    // HTTP GET request
    private void sendGet(String resource) throws Exception {

        String url = "https://" + requestHost + resource;

        /* HTTP connection */
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        /* Add Request Header :: "v-c-merchant-id" set value to Cybersource Merchant ID or v-c-merchant-id
         * This ID can be found on EBC portal */
        con.setRequestProperty("v-c-merchant-id", merchantId);
        con.setRequestProperty("v-c-correlation-id", "123");
        //con.setRequestProperty("profile-id", "93B32398-AD51-4CC2-A682-EA3E93614EB1");

        /* Add Request Header :: "Date" The date and time that the message was originated from.
         * "HTTP-date" format as defined by RFC7231. */
        gmtDateTime = getdate();
        con.setRequestProperty("date", gmtDateTime);

        /* Add Request Header :: "Host"
         * Sandbox Host: apitest.cybersource.com
         * Production Host: api.cybersource.com */
        con.setRequestProperty("Host", requestHost);

        /* Add Request Header :: "Signature"
         * Signature header contains keyId, algorithm, headers and signature as paramters
         * method getSignatureHeader() has more details */
        StringBuilder signatureHeaderValue = getSignatureHeader("GET");
        con.setRequestProperty("Signature", signatureHeaderValue.toString());


        /* HTTP Method GET */
        con.setRequestMethod("GET");

        /* Addition Request Headers */
//        con.setRequestProperty("User-Agent", USER_AGENT);
//        con.setRequestProperty("Content-Type", "application/json");

        /* Establishing HTTP connection*/
        int responseCode = con.getResponseCode();
        String responseHeader = con.getHeaderField("v-c-correlation-id");

        System.out.println("\n -- RequestURL -- ");
        System.out.println("URL : " + url);
        System.out.println("\n -- HTTP Headers -- ");
        System.out.println("Content-Type : "+ "application/json");
        System.out.println("v-c-merchant-id : "+ merchantId);
        System.out.println("Date : "+ gmtDateTime);
        System.out.println("Host : "+ requestHost);
        System.out.println("Signature : "+ signatureHeaderValue);
        System.out.println("\n -- Response Message -- " );
        System.out.println("Response Code :" + responseCode);
        System.out.println("v-c-correlation-id :" + responseHeader);

        /* Reading Response Message */
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        /* print Response */
        System.out.println("Response Payload : " + response.toString());

    }

    // HTTP POST request
    private void sendPost(String url) throws Exception {

        /* HTTP connection */
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        /* Add Request Header :: "v-c-merchant-id" set value to Cybersource Merchant ID or v-c-merchant-id
         * This ID can be found on EBC portal */
        con.setRequestProperty("v-c-merchant-id", merchantId);
        con.setRequestProperty("v-c-correlation-id", "123");
        con.setRequestProperty("profile-id", "93B32398-AD51-4CC2-A682-EA3E93614EB1");

        //oleg's profile-ID
//        con.setRequestProperty("profile-id", "DE14A8CD-CC3E-4889-AC46-67ACFB7CD27C");

        /* Add Request Header :: "Date" The date and time that the message was originated from.
         * "HTTP-date" format as defined by RFC7231. */
        gmtDateTime = getdate();

        con.setRequestProperty("date", gmtDateTime);

        /* Add Request Header :: "Host"
         * Sandbox Host: apitest.cybersource.com
         * Production Host: api.cybersource.com */
        con.setRequestProperty("Host", requestHost);

        /* Add Request Header :: "Digest"
         * Digest is SHA-256 hash of payload that is BASE64 encoded */
        con.setRequestProperty("Digest", getDigest());

        /* Add Request Header :: "Signature"
         * Signature header contains keyId, algorithm, headers and signature as paramters
         * method getSignatureHeader() has more details */
        StringBuilder signatureHeaderValue = getSignatureHeader("POST");
        con.setRequestProperty("Signature", signatureHeaderValue.toString());


        /* HTTP Method POST */
        con.setRequestMethod("POST");

        /* Addition Request Headers */
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");


        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(getPayload().getBytes("UTF-8"));
        wr.flush();
        wr.close();

        /* Establishing HTTP connection*/
        int responseCode = con.getResponseCode();

        String responseMessge = con.getResponseMessage();
        String responseHeader = con.getHeaderField("v-c-correlation-id");
        System.out.println("\n -- RequestURL -- ");
        System.out.println("URL : " + url);
        System.out.println("\n -- HTTP Headers -- ");
        System.out.println("Content-Type : "+ "application/json");
        System.out.println("v-c-merchant-id : "+ merchantId);
        System.out.println("Date : "+ gmtDateTime);
        System.out.println("Host : "+ requestHost);
        System.out.println("Digest : "+ getDigest());
        System.out.println("Signature : "+ signatureHeaderValue);
        System.out.println("\n -- Response Message -- " );
        System.out.println("Response Code :" + responseCode);
        System.out.println("v-c-correlation-id :" + responseHeader);
        try {
        /* Reading Response Message */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        /* print Response */
            System.out.println("Response Payload : " + response.toString());
        } catch (Exception exception) {
            // Output unexpected IOExceptions.
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
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
        //JSONObject jsonObj = new JSONObject(response.toString());

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
                      String getHeaders = "host date (request-target)" + " " + "v-c-merchant-id";
                      String postHeaders = "host date (request-target) digest v-c-merchant-id";
                      Note: Digest is not passed for GET calls
         * signature -- Signature header has paramter called signature
                        Paramter 'Signature' must contain all the paramters mentioned in header above in given order  */

        StringBuilder signatureHeaderValue = new StringBuilder();

        /* KeyId is the key obtained from EBC */
        signatureHeaderValue.append("keyid=\"" + merchantKeyId + "\"");


        /* Algorithm should be always HmacSHA256 for http signature */
        signatureHeaderValue.append(", algorithm=\"HmacSHA256\"");

        /* Headers - list is choosen based on HTTP method. Digest is not required for GET Method */
        String getHeaders = "host date (request-target)" + " " + "v-c-merchant-id";
        String postHeaders = "host date (request-target) digest v-c-merchant-id";

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
         * (request-target): Should be in format of httpMethod: path
                             Example: "post /pts/v2/payments"
         * Digest: Only needed for POST calls.
                    digestString = BASE64( HMAC-SHA256 ( Payload ));
                    Digest: “SHA-256=“ + digestString;
         * v-c-merchant-id: set value to Cybersource Merchant ID
                             This ID can be found on EBC portal*/

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
        signatureString.append("(request-target)");
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
        String signature = "";

        /* Signature string generated from above parameters is Signed with SecretKey hased with SHA256 and base64 encoded.
         *  Secret Key is Base64 decoded before signing */
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