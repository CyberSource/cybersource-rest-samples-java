package Data;

import java.util.Properties;

public class MerchantBoardingConfiguration {

    public static Properties getMerchantConfigForBoardingAPI() {
        Properties props = new Properties();

        props.setProperty("authenticationType", "jwt");
        props.setProperty("merchantID", "<insert merchantId here for testing the boarding samples>");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("requestJsonPath", "src/main/resources/request.json");


        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");


        props.setProperty("keyAlias", "<insert keyAlias (merchantId)  here for testing the boarding samples>");
        props.setProperty("keyPass", "<insert p12 file password here for testing the boarding samples>");
        props.setProperty("keyFileName", "<insert p12 file without .p12 extension here for testing the boarding samples>");
        props.setProperty("keysDirectory", "src/main/resources");


        props.setProperty("merchantKeyId", "");
        props.setProperty("merchantsecretKey", "");

        props.setProperty("enableLog", "true");
        props.setProperty("logDirectory", "log");
        props.setProperty("logFilename", "cybs");
        props.setProperty("logMaximumSize", "5M");


        return props;

    }
}
