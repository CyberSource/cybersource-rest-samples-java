# Java Sample Code for the CyberSource SDK

[![Travis CI Status](https://travis-ci.org/Cybersource/cybersource-rest-samples-java.svg?branch=master)](https://travis-ci.org/Cybersource/cybersource-rest-samples-java)

This repository contains working code samples which demonstrate Java integration with the CyberSource REST APIs through the [CyberSource Java SDK](https://github.com/CyberSource/cybersource-rest-client-java).

The samples are organized into categories and common usage examples.

## Using the Sample Code

The samples are all completely independent and self-contained. You can analyze them to get an understanding of how a particular method works, or you can use the snippets as a starting point for your own project.

### Clone (or download) this repository

```bash
    git clone https://github.com/CyberSource/cybersource-rest-samples-java
```

### Running the Samples using IntelliJ IDE

* Open the project/folder (rather than import or new).

* Build the project:
  * From the Build menu, select Rebuild Project.

* Run any sample:
  * For example, select `SimpleAuthorizationInternet` class from the class Explorer
  * Right-click and select Run `SimpleAuthorizationInternet.Main()`
  
### Running the Samples using Eclipse IDE

* Import the project:
  * From File menu, select Import.
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

To set your API credentials for an API request, configure the following information in `src/main/java/data/Configuration.java` file:
  
* Http Signature

```java
  authenticationType  = http_Signature
  merchantID          = your_merchant_id
  merchantKeyId       = your_key_serial_number
  merchantsecretKey   = your_key_shared_secret
  useMetaKey          = false
  enableClientCert    = false
```

* Jwt

```java
  authenticationType  = jwt
  merchantID          = your_merchant_id
  keyAlias            = your_merchant_id
  keyPass             = your_merchant_id
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
  authenticationType  = jwt
  merchantID          = your_child_merchant_id
  keyAlias            = your_child_merchant_id
  keyPass             = your_portfolio_id
  keyFileName         = your_portfolio_id
  portfolioId         = your_portfolio_id
  keysDirectory       = resources
  useMetaKey          = true
  enableClientCert    = false
```

* OAuth

  CyberSource OAuth uses mutual authentication. A Client Certificate is required to authenticate against the OAuth API. 

  Refer to [Supporting Mutual Authentication](https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro/Supporting-Mutual-Authentication.html) to get information on how to generate Client certificate.

  If the certificate (Public Key) and Private Key are in 2 different files, merge them into a single .p12 file using `openssl`.

    ```bash
    openssl pkcs12 -export -out certificate.p12 -inkey privateKey.key -in certificate.crt
    ```

  Set the run environment to OAuth enabled URLs. OAuth only works in these run environments.

    ```java
    // For TESTING use
       runEnvironment      = api-matest.cybersource.com
    // For PRODUCTION use
    // runEnvironment   = api-ma.cybersource.com
    ```

  To generate tokens, an Auth Code is required. The Auth Code can be generated by following the instructions given in [Integrating OAuth](https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro/integrating_OAuth.html).

  This generated Auth Code can then be used to create the Access Token and Refresh Token.

  In `src/main/java/data/Configuration.java` file, set the following properties.
  
  Note that `authenticationType` is set to `MutualAuth` only to generate the Access Token and the Refresh Token.

    ```java
    authenticationType  = MutualAuth
    enableClientCert    = true
    clientCertDirectory = resources
    clientCertFile      = your_client_cert in .p12 format
    clientCertPassword  = password_for_client_cert
    clientId            = your_client_id
    clientSecret        = your_client_secret
    ```

  Once the tokens are obtained, the `authenticationType` can then be set to `OAuth` to use the generated Access Token to send requests to other APIs.

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

  The Access Token is valid for 15 mins, whereas the Refresh Token is valid for 1 year.

  Once the Access Token expires, use the Refresh Token to generate another Access Token.

  Refer to [StandAloneOAuth.java](https://github.com/CyberSource/cybersource-rest-samples-java/blob/master/src/main/java/samples/authentication/AuthSampleCode/StandaloneOAuth.java) to understand how to consume OAuth.

  For further information, refer to the documentation at [Cybersource OAuth 2.0](https://developer.cybersource.com/api/developer-guides/OAuth/cybs_extend_intro.html).

## Run Environments

The run environments that were supported in CyberSource Java SDK have been deprecated.
Moving forward, the SDKs will only support the direct hostname as the run environment.

For the old run environments previously used, they should be replaced by the following hostnames:

|              Old Run Environment              |               New Hostname Value               |
|-----------------------------------------------|------------------------------------------------|
|`cybersource.environment.sandbox`              |`apitest.cybersource.com`                       |
|`cybersource.environment.production`           |`api.cybersource.com`                           |
|`cybersource.environment.mutualauth.sandbox`   |`api-matest.cybersource.com`                    |
|`cybersource.environment.mutualauth.production`|`api-ma.cybersource.com`                        |
|`cybersource.in.environment.sandbox`           |`apitest.cybersource.com`                       |
|`cybersource.in.environment.production`        |`api.in.cybersource.com`                        |
|`cybersource.environment.sit`                  |`pnrstage.ic3.com:8451`                         |
|`cybersource.environment.mutualauth.sit`       |`pnrstage.ic3.com:8451`                         |

For example, replace the following code in the Configuration file:

```java
   // For TESTING use
      runEnvironment      = CyberSource.Environment.SANDBOX
   // For PRODUCTION use
   // runEnvironment      = CyberSource.Environment.PRODUCTION
```

with the following code:

```java
   // For TESTING use
      runEnvironment      = apitest.cybersource.com
   // For PRODUCTION use
   // runEnvironment      = api.cybersource.com
```

## Switching between the sandbox environment and the production environment

CyberSource maintains a complete sandbox environment for testing and development purposes. This sandbox environment is an exact duplicate of our production environment with the transaction authorization and settlement process simulated. By default, this SDK is configured to communicate with the sandbox environment. To switch to the production environment, set the appropriate environment constant in resources/cybersource.properties file.  For example:

```java
   // For TESTING use
      runEnvironment      = apitest.cybersource.com
   // For PRODUCTION use
   // runEnvironment      = api.cybersource.com
```

To use OAuth, switch to OAuth enabled URLs

```java
   // For TESTING use
      runEnvironment   = api-matest.cybersource.com
   // For PRODUCTION use
   // runEnvironment   = api-ma.cybersource.com
```

The [API Reference Guide](https://developer.cybersource.com/api/reference/api-reference.html) provides examples of what information is needed for a particular request and how that information would be formatted. Using those examples, you can easily determine what methods would be necessary to include that information in a request
using this SDK.
