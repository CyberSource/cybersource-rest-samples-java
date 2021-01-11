# Java Sample Code for the CyberSource SDK
[![Travis CI Status](https://travis-ci.org/Cybersource/cybersource-rest-samples-java.svg?branch=master)](https://travis-ci.org/Cybersource/cybersource-rest-samples-java)

This repository contains working code samples which demonstrate Java integration with the CyberSource REST APIs through the [CyberSource Java SDK](https://github.com/CyberSource/cybersource-rest-client-java).

The samples are organized into categories and common usage examples.

## Using the Sample Code

The samples are all completely independent and self-contained. You can analyze them to get an understanding of how a particular method works, or you can use the snippets as a starting point for your own project.

### Clone (or download) this repository:
```
    $ git clone https://github.com/CyberSource/cybersource-rest-samples-java
```
### Running the Samples using IntelliJ IDE
* Open the project/folder (rather than import or new).

* Build the project:
	* From the Build menu, select Rebuild Project.

* Run any sample:
	* For example, select ProcessPayment class from the class Explorer
	* Right-click and select Run ProcessPayment.Main()
	
### Running the Samples using Eclipse IDE
* Import the project:
	* From File menu,select Import.
	* Expand Maven menu. 
	* And click Existing Maven Projects
	* Click next and browse the location where you have the Maven project source code. 
	* Click next, Eclipse will recognize the Maven project and it will show you a list of all possible Maven projects located there. 
	* Just select the project and click next. 
	* Eclipse will show you a Maven Build message. Just click finish. 
	* The Maven project is successfully imported into Eclipse IDE.

* Run the project: 
	* Right-click the project folder.
	* Select Run as Maven Build.
	* In the Goals field, enter "clean install"
	* Select the JRE tab and make sure it is pointing at a JRE associated with a JDK.
	* Click Run.
	

## Setting Your API Credentials

To set your API credentials for an API request,Configure the following information in src/main/java/data/Configuration.java file:
  
  * Http Signature

```java
   authenticationType  = http_Signature
   merchantID 	       = your_merchant_id
   merchantKeyId       = your_key_serial_number
   merchantsecretKey   = your_key_shared_secret
   useMetaKey          = false
   enableClientCert    = false
```
  * Jwt

```java
   authenticationType  = Jwt
   merchantID 	       = your_merchant_id
   keyAlias	       = your_merchant_id
   keyPassword	       = your_merchant_id
   keyFileName         = your_merchant_id
   keysDirectory       = resources
   useMetaKey          = false
   enableClientCert    = false
```

   * MetaKey Http

```java
	authenticationType  = http_Signature
	merchantID          = your_child_merchant_id
	merchantKeyId       = your_metakey_serial_number
	merchantsecretKey   = your_metakey_shared_secret
	portfolioId         = your_portfolio_id
   useMetaKey          = true
   enableClientCert    = false
```

   * MetaKey JWT

```java
    authenticationType  = Jwt
    merchantID          = your_child_merchant_id
    keyAlias            = your_child_merchant_id
    keyPassword         = your_portfolio_id
    keyFileName         = your_portfolio_id
    keysDirectory       = Resource
    useMetaKey          = true
    enableClientCert    = false
```


   * OAuth

   To use OAuth, please follow the steps. 
   Cybersource OAuth uses mutual authentication. Client Certificate is required to authenticate against OAuth API. Refer to this link - https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro/Supporting-Mutual-Authentication.html to get information on how to generate Client certificate.
   
   If the certificate (Public Key) and Private Key are in 2 different files, merge them into a single .p12 file using openssl. 

```
   openssl pkcs12 -export -out certificate.p12 -inkey privateKey.key -in certificate.crt
```

   Set the run environment to OAuth enabled URLs. OAuth only works in these run environments.
```
   // For TESTING use
   runEnvironment      = CyberSource.Environment.MutualAuth.SANDBOX
   // For PRODUCTION use
   // runEnvironment   = CyberSource.Environment.MutualAuth.PRODUCTION
```

   Set the config below, Set authenticationType to 'MutualAuth' only to generate Access Token and Refresh Token. 

```java
   authenticationType  = MutualAuth
   enableClientCert    = true
   clientCertDirectory = resources
   clientCertFile      = your_client_cert - .p12 format
   clientCertPassword  = password_for_client_cert
   clientId            = your_client_id
   clientSecret        = your_client_secret
```

   Hit OAuth API with the above configuration. To generate tokens, an Auth Code is required. Generate the Auth Code by following the steps mentioned here - https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro/integrating_OAuth.html.
   Use the generated Auth Code to create the Access Token and Refresh Token.
   
   Once the tokens are obtained, set the config below, Set authenticationType to OAuth to use the generated Access Token

```java
   authenticationType  = OAuth
   enableClientCert    = true
   clientCertDirectory = resources
   clientCertFile      = your_client_cert - .p12 format
   clientCertPassword  = password_for_client_cert
   clientId            = your_client_id
   clientSecret        = your_client_secret
   accessToken         = generated_access_token
   refreshToken        = generated_refresh_token
```

   With the above config, we can use OAuth to hit other APIs. Access Token is valid for 15 mins, Refresh Token is valid for 1 year. Once the Access Token expires, use the Refresh Token to generate another Access Token.   
   Refer to StandAloneOAuth.java (https://github.com/CyberSource/cybersource-rest-samples-java/blob/master/src/main/java/samples/authentication/AuthSampleCode/StandaloneOAuth.java) to understand how to consume OAuth.
   For further information, refer to this link - https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro.html

## Switching between the sandbox environment and the production environment
CyberSource maintains a complete sandbox environment for testing and development purposes. This sandbox environment is an exact
duplicate of our production environment with the transaction authorization and settlement process simulated. By default, this SDK is 
configured to communicate with the sandbox environment. To switch to the production environment, set the appropriate environment 
constant in resources/cybersource.properties file.  For example:

```java
// For TESTING use
  runEnvironment      = CyberSource.Environment.SANDBOX
// For PRODUCTION use
// runEnvironment      = CyberSource.Environment.PRODUCTION
```


The [API Reference Guide](https://developer.cybersource.com/api/reference/api-reference.html) provides examples of what information is needed for a particular request and how that information would be formatted. Using those examples, you can easily determine what methods would be necessary to include that information in a request
using this SDK.

  
