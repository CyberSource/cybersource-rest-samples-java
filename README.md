# Java Sample Code for the CyberSource SDK

This repository contains working code samples which demonstrate Java integration with the CyberSource REST APIs through the CyberSource Java SDK.

**__NOTE: THIS REPO OF CODE SAMPLES HAS BEEN MADE PUBLIC FOR SDK TESTING AND SHOULD NOT BE USED FOR PRODUCTION - YET.  PLEASE RAISE AN ISSUE ON THIS REPO IF YOU HAVE FURTHER QUESTIONS AND CHECK BACK SOON FOR GENERAL AVAILABILITY__**

The samples are organized into categories and common usage examples.

## Requirements
* JDK 1.8
* Maven 2.2.0 or higher (build SDK only)
* Eclipse IDE
* [CyberSource Account](https://developer.cybersource.com/api/developer-guides/dita-gettingstarted/registration.html)
* [CyberSource API Keys](https://prod.developer.cybersource.com/api/developer-guides/dita-gettingstarted/registration/createCertSharedKey.html)


## Installation
```
		<dependency>
			<groupId>com.cybersource</groupId>
			<artifactId>cybersource-rest-client-java</artifactId>
			<version>0.0.2</version>
		</dependency>
```
The samples are organized into categories and common usage examples.

## Using the Sample Code

The samples are all completely independent and self-contained. You can analyze them to get an understanding of how a particular method works, or you can use the snippets as a starting point for your own project.

You can also run each sample directly from the command line.

## Running the Samples From the Command Line
* Clone this repository:
```
    $ git clone https://github.com/CyberSource/cybersource-rest-samples-java
```
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


#### To set your API credentials for an API request,Configure the following information in resources/cybersource.properties file:
  
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
constant in resources/cybersource.properties file.  For example:

```java
// For PRODUCTION use
  runEnvironment      = CyberSource.Environment.PRODUCTION
```


The [API Reference Guide](https://developer.cybersource.com/api/reference/api-reference.html) provides examples of what information is needed for a particular request and how that information would be formatted. Using those examples, you can easily determine what methods would be necessary to include that information in a request
using this SDK.

  
## License
This repository is distributed under a proprietary license.
