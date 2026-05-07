package Data;

import java.util.Properties;

/**
 * Configuration for JWT authentication with Shared Secret (symmetric / HS256).
 *
 * <h2>Why JWT with Shared Secret?</h2>
 * <ul>
 *   <li><b>HTTP Signature is being deprecated.</b> JWT with Shared Secret provides a
 *       seamless migration path — it uses the <b>same</b> {@code merchantKeyId} and
 *       {@code merchantsecretKey} credentials you already have for HTTP Signature.</li>
 *   <li><b>Enables MLE (Message Level Encryption).</b> MLE requires JWT authentication.
 *       By switching to JWT with Shared Secret, you can enable MLE without managing
 *       a P12 certificate file.</li>
 *   <li><b>Zero credential changes.</b> Your existing Key ID and Shared Secret from the
 *       CyberSource Business Center work as-is.</li>
 * </ul>
 *
 * <h2>Credentials</h2>
 * <p>The {@code merchantKeyId} and {@code merchantsecretKey} are the same credentials
 * used for HTTP Signature authentication. You can obtain them from the CyberSource
 * Business Center:</p>
 * <ul>
 *   <li>Test: <a href="https://businesscentertest.cybersource.com/ebc2">businesscentertest.cybersource.com/ebc2</a></li>
 *   <li>Production: <a href="https://businesscenter.cybersource.com/ebc2">businesscenter.cybersource.com/ebc2</a></li>
 * </ul>
 */
public class JwtSharedSecretConfiguration {

    /**
     * Returns merchant properties configured for JWT authentication with Shared Secret.
     *
     * <p>This is a drop-in replacement for HTTP Signature authentication.
     * The only changes from a typical HTTP Signature configuration are:</p>
     * <ol>
     *   <li>{@code authenticationType} = {@code jwt} (instead of {@code http_signature})</li>
     *   <li>{@code jwtKeyType} = {@code SHARED_SECRET} (new property)</li>
     * </ol>
     *
     * <p>The {@code merchantKeyId} and {@code merchantsecretKey} remain the same.</p>
     */
    public static Properties getMerchantDetails() {
        Properties props = new Properties();

        // Authentication: JWT with Shared Secret (HS256)
        props.setProperty("authenticationType", "jwt");
        props.setProperty("jwtKeyType", "SHARED_SECRET");

        props.setProperty("merchantID", "testrest");
        props.setProperty("runEnvironment", "apitest.cybersource.com");

        // Shared Secret credentials — same as HTTP Signature credentials
        props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
        props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");

        // MetaKey Parameters
        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        return props;
    }

    /**
     * Returns merchant properties configured for JWT with Shared Secret + MLE enabled.
     *
     * <p>This configuration enables Message Level Encryption (MLE) for request payloads.
     * Response MLE is also supported — set {@code enableResponseMleGlobally} to {@code true}
     * and provide the response MLE private key settings.</p>
     *
     * <p>When using {@code jwtKeyType=SHARED_SECRET}, Request MLE requires the public certificate
     * to be provided via {@code mleForRequestPublicCertPath} because there is no P12 file
     * to auto-extract it from.</p>
     *
     * <p>Download the MLE public certificate from the CyberSource Business Center:</p>
     * <ul>
     *   <li>Test: <a href="https://businesscentertest.cybersource.com/ebc2">businesscentertest.cybersource.com/ebc2</a></li>
     *   <li>Production: <a href="https://businesscenter.cybersource.com/ebc2">businesscenter.cybersource.com/ebc2</a></li>
     * </ul>
     */
    public static Properties getMerchantDetailsWithMLE() {
        Properties props = new Properties();

        // Authentication: JWT with Shared Secret (HS256)
        props.setProperty("authenticationType", "jwt");
        props.setProperty("jwtKeyType", "SHARED_SECRET");

        props.setProperty("merchantID", "testrest");
        props.setProperty("runEnvironment", "apitest.cybersource.com");

        // Shared Secret credentials — same as HTTP Signature credentials
        props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
        props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");

        // --- Request MLE Configuration ---
        // When using SHARED_SECRET, the MLE certificate must be provided separately.
        // Download from CyberSource Business Center:
        //   Test: https://businesscentertest.cybersource.com/ebc2
        //   Prod: https://businesscenter.cybersource.com/ebc2
        props.setProperty("enableRequestMLEForOptionalApisGlobally", "true");
        props.setProperty("mleForRequestPublicCertPath", "src/main/resources/MLE_PublicCert.pem");
        // props.setProperty("requestMleKeyAlias", "CyberSource_SJC_US"); // Optional — defaults to CyberSource_SJC_US

        // --- Response MLE Configuration ---
        // Set to "true" to enable response MLE (encrypted responses from CyberSource).
        // Requires a private key for decryption.
        props.setProperty("enableResponseMleGlobally", "false");
        // Provide EITHER a private key file path OR a PrivateKey object (via MerchantConfig constructor).
        // Supported formats: .p12, .pfx, .pem, .key, .p8
        props.setProperty("responseMlePrivateKeyFilePath", ""); // e.g., "src/main/resources/your_mle_private_key.p12"
        props.setProperty("responseMlePrivateKeyFilePassword", ""); // Required for .p12/.pfx or encrypted keys
        // responseMleKID: Optional for CyberSource-generated P12 files (auto-extracted).
        // Required for PEM/KEY files or when providing PrivateKey object directly.
        props.setProperty("responseMleKID", "");

        // MetaKey Parameters
        props.setProperty("portfolioID", "");
        props.setProperty("useMetaKey", "false");

        return props;
    }
}
