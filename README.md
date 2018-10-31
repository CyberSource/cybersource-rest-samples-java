# Java Sample Code for the CyberSource SDK
This project provides multiple sample codes for REST APIs supported by CyberSource.

## Requirements
* JDK 1.8
* Maven 2.2.0 or higher (build SDK only)
* [CyberSource Account](https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/registration.html)
* [CyberSource API Keys](https://prod.developer.cybersource.com/api/developer-guides/dita-gettingstarted/registration/createCertSharedKey.html)

_Note: Support for building the SDK with Maven has been made. Please see the respective build processes below. 
 All initial jars and docs were built with Maven, however._
 
 ## Dependencies
* log4j-api-2.11.0              : logging
* log4j-2.11.0.jar              : logging
* log4j-core                    : logging
* httpclient-4.0.1.jar          : http communication with the payment gateway
* httpcore-4.0.1.jar            : http communication with the payment gateway
* junit-4.8.1.jar               : unit testing
* sonar-maven-plugin-3.4.0.905  : Sonar coverage

## Installation
```
		<dependency>
			<groupId>io.swagger</groupId>
			<artifactId>cybersource-rest-client-java</artifactId>
			<version>0.0.1</version>
		</dependency>
```
## To run Sample Code

The SDK works for POST, GET, PATCH, DELETE requests.
It works with any one of the two authentication mechanisms, which are HTTP signature and JWT token.

FOR TESTING/POC PURPOSE - YOU CAN USE EXISTING CREDENTIALS or configure your own credentails. 

#### To set your API credentials for an API request,Configure the following information in cybs.properties file:
  
  * Http

```
   authenticationType  = http_Signature
   merchantID 	       = testrest
   runEnvironment      = CyberSource.Environment.SANDBOX
   merchantKeyId       = 08c94330-f618-42a3-b09d-e1e43be5efda
   merchantsecretKey   = yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=
   enableLog           = true
   logDirectory        = log
   logMaximumSize      = 5M
   logFilename         = cybs
```
  * Jwt

```
   authenticationType  = Jwt
   merchantID 	       = testrest
   runEnvironment      = CyberSource.Environment.SANDBOX
   keyAlias		       = testrest
   keyPassword	       = testrest
   keyFileName         = testrest
   keysDirectory       = resources
   enableLog           = true
   logDirectory        = log
   logMaximumSize      = 5M
   logFilename         = cybs
```

### Switching between the sandbox environment and the production environment
CyberSource maintains a complete sandbox environment for testing and development purposes. This sandbox environment is an exact
duplicate of our production environment with the transaction authorization and settlement process simulated. By default, this SDK is 
configured to communicate with the sandbox environment. To switch to the production environment, set the appropriate environment 
constant in cybs.properties file.  For example:

```java
// For PRODUCTION use
  runEnvironment      = CyberSource.Environment.PRODUCTION
```

## SDK Usage Examples and Sample Code
 * To get started using this SDK, it's highly recommended to download our sample code repository.
 * In that respository, we have comprehensive sample code for all common uses of our API.
 * Additionally, you can find details and examples of how our API is structured in our API Reference Guide.

The [API Reference Guide](https://developer.cybersource.com/api/reference/api-reference.html) provides examples of what information is needed for a particular request and how that information would be formatted. Using those examples, you can easily determine what methods would be necessary to include that information in a request
using this SDK.

## Building & Testing the SDK
Build the SDK with Maven
------------------------

To compile the SDK and create a jar...

  ` $ mvn clean package`
  
## License
This repository is distributed under a proprietary license.
