package Data;

import java.util.Properties;

public class MerchantBoardingConfiguration {

    public static Properties getMerchantConfigForBoardingAPI() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", "<insert merchantId here for testing the boarding samples>");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        // JWT Parameters
        props.setProperty("keyAlias", "<insert keyAlias (merchantId)  here for testing the boarding samples>");
        props.setProperty("keyPass", "<insert p12 file password here for testing the boarding samples>");
        props.setProperty("keyFileName", "<insert p12 file without .p12 extension here for testing the boarding samples>");

        // P12 key path. Enter the folder path where the .p12 file is located.
        props.setProperty("keysDirectory", "<insert p12 file location directory>");

        // HTTP Parameters
        props.setProperty("merchantKeyId", "");
        props.setProperty("merchantsecretKey", "");
        // Logging to be enabled or not.
        props.setProperty("enableLog", "true");
        // Log directory Path
        props.setProperty("logDirectory", "log");
        props.setProperty("logFilename", "cybs");

        // Log file size in KB
        props.setProperty("logMaximumSize", "5M");

        // OAuth related properties.
        props.setProperty("enableClientCert", "true");
        props.setProperty("clientCertDirectory", "src/main/resources");
        props.setProperty("clientCertFile", "apitester00");
        props.setProperty("clientCertPassword", "Ap!C38tp12@");
        props.setProperty("clientId", "apitester00");
        props.setProperty("clientSecret", "Ap!C38tp12@");
        

        return props;

    }
}
