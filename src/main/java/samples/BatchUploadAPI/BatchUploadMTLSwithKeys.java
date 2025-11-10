package samples.BatchUploadAPI;

import java.io.File;
import java.io.FileReader;
import java.lang.invoke.MethodHandles;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Collection;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;

import Api.BatchUploadwithMTLSApi;
import Invokers.ApiResponse;
import utilities.pgpBatchUpload.BatchUploadUtility;

public class BatchUploadMTLSwithKeys {
	private static int responseCode ;
	private static String responseMessage = null;
	
	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}
	
	public static void main(String args[]) throws Exception {
		run();
	}
	
	public static void run() {
		try {
						
			// Get the file path from the resources folder
			String fileName="batchapiTest.csv";
			String filePath = BatchUploadMTLSwithKeys.class.getClassLoader().getResource("batchApiMTLS/"+fileName).getPath();
			
			//Input1 - Create a File object
			File inputFile = new File(filePath);
			
			//Input2 - Env Host name
			String envHostName = "secure-batch-test.cybersource.com"; //cas env
			
			//Read public and private keys
			
			//Input3 - PGP Public Key Object 
			String publicKeyFile = BatchUploadMTLSwithKeys.class.getClassLoader().getResource("batchApiMTLS/bts-encryption-public.asc").getPath();
			PGPPublicKey pgpPublicKey = BatchUploadUtility.readPGPPublicKey(publicKeyFile);
			
			//Input4 - Client Private Key Object
			String clientprivatekeypath= BatchUploadMTLSwithKeys.class.getClassLoader().getResource("batchApiMTLS/client_private_key.key").getPath();
			PrivateKey clientPrivateKey = loadPrivateKeyFromPemFile(clientprivatekeypath,null);
			
			//Input5 - Client Certificate Key Object
			String clientcertPath= BatchUploadMTLSwithKeys.class.getClassLoader().getResource("batchApiMTLS/client_cert.crt").getPath();
			Collection<X509Certificate> clientCerts = BatchUploadUtility.loadCertificatesFromPemFile(clientcertPath);
			
			//Input6 - Server Certificate Key Object for ssl verification for endpoint url (optional) can pass as null
//			String servercertPath= BatchUploadMTLSwithKeys.class.getClassLoader().getResource("batchApiMTLS/serverCasCert.pem").getPath();
//			Collection<X509Certificate> serverCerts = BatchUploadUtility.loadCertificatesFromPemFile(servercertPath);
			Collection<X509Certificate> serverCerts = null ;
	        
	        //SDK need file object and keys to upload file to batch api endpoint
			BatchUploadwithMTLSApi apiInstance= new BatchUploadwithMTLSApi();
			ApiResponse<String> result= apiInstance.uploadBatchAPI(inputFile, envHostName, pgpPublicKey, clientPrivateKey, clientCerts.iterator().next(), serverCerts);
			
			
			responseCode = result.getStatusCode();
			responseMessage = result.getMessage();
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMessage);
			WriteLogAudit(responseCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PrivateKey loadPrivateKeyFromPemFile(String keyFilePath, char[] password) throws Exception {
	    Security.addProvider(new BouncyCastleProvider());
	    try (FileReader keyReader = new FileReader(keyFilePath);
	         PEMParser pemParser = new PEMParser(keyReader)) {
	        Object object = pemParser.readObject();
	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

	        if (object instanceof PEMKeyPair) {
	            // Unencrypted PKCS#1 key
	            return converter.getKeyPair((PEMKeyPair) object).getPrivate();
	        } else if (object instanceof PEMEncryptedKeyPair) {
	            // Encrypted PKCS#1 key
	            if (password == null) {
	                throw new IllegalArgumentException("Private key is password protected, but no password was provided.");
	            }
	            JcePEMDecryptorProviderBuilder decProvBuilder = new JcePEMDecryptorProviderBuilder();
	            PEMEncryptedKeyPair encKeyPair = (PEMEncryptedKeyPair) object;
	            PEMKeyPair decryptedKeyPair = encKeyPair.decryptKeyPair(decProvBuilder.build(password));
	            return converter.getKeyPair(decryptedKeyPair).getPrivate();
	        } else if (object instanceof PrivateKeyInfo) {
	            // Unencrypted PKCS#8 key
	            return converter.getPrivateKey((PrivateKeyInfo) object);
	        } else if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
	            // Encrypted PKCS#8 key
	            if (password == null) {
	                throw new IllegalArgumentException("Private key is password protected, but no password was provided.");
	            }
	            PKCS8EncryptedPrivateKeyInfo encPrivKeyInfo = (PKCS8EncryptedPrivateKeyInfo) object;
	            JceOpenSSLPKCS8DecryptorProviderBuilder decryptorBuilder = new JceOpenSSLPKCS8DecryptorProviderBuilder();
	            PrivateKeyInfo pkInfo = encPrivKeyInfo.decryptPrivateKeyInfo(decryptorBuilder.build(password));
	            return converter.getPrivateKey(pkInfo);
	        } else {
	            throw new IllegalArgumentException("Unsupported PEM object: " + object.getClass());
	        }
	    }
	}

}
