package Data;

import java.util.Properties;

public class ConfigurationForBankAccountValidation {
    public static Properties getMerchantDetailsForBankAccountValidation() {

        Properties props = new Properties();

        // MLE (Message Level Encryption) is only supported with JWT authentication. For the Bank Account Validation API, MLE is mandatory, and the SDK defaults to encrypted requests for such APIs.
        props.setProperty("authenticationType", "JWT");
        props.setProperty("merchantID", "testcasmerchpd01001");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("requestJsonPath", "src/main/resources/request.json");


        // JWT Parameters
        props.setProperty("keyAlias", "testcasmerchpd01001");
        props.setProperty("keyPass", "Authnet101!");
        props.setProperty("keyFileName", "testcasmerchpd01001");
        // P12 key path. Enter the folder path where the .p12 file is located.
        props.setProperty("keysDirectory", "src/main/resources");

        return props;

    }
}
