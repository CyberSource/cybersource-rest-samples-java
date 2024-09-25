package Data;

import java.util.Properties;

public class MerchantBoardingConfiguration {

    public static Properties getMerchantConfigForBoardingAPI() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", "apitester00");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        // JWT Parameters
        props.setProperty("keyAlias", "apitester00");//merchid
        props.setProperty("keyPass", "Ap!C38tp12@");//pwd
        props.setProperty("keyFileName", "apitester00");//filename

        // P12 key path. Enter the folder path where the .p12 file is located.

        props.setProperty("keysDirectory", "src/main/resources");
        // HTTP Parameters
        props.setProperty("merchantKeyId", "");//blank
        props.setProperty("merchantsecretKey", "");//blank
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

		/*
		PEM Key file path for decoding JWE Response Enter the folder path where the .pem file is located.
		It is optional property, require adding only during JWE decryption.
		*/
       // props.setProperty("jwePEMFileDirectory", "src/main/resources/NetworkTokenCert.pem");

        return props;

    }
}
