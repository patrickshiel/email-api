# Email Api:

Exposes a Rest API that accepts the necessary information in JSON Format and sends emails.

The application provides an abstraction between two different email service providers. 
- Send Grid (https://sendgrid.com/docs/API_Reference/Web_API_v3/index.html)
- Mail Gun (https://documentation.mailgun.com/en/latest/api-sending.html)

If one of the services goes down, your service can quickly failover to a different provider without affecting your customers.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

### API Documentation - Swagger

Live running documentation for the definition of the `email` api has been defined using swagger and can be accessed here:

http://api.shiel.io/swagger-ui.html#!/email/sendEmailUsingPOST

`HTTP POST requests` can be made to http://api.shiel.io/email with the following Body structure
```
{
	"to": ["paddyshiel@outlook.com", "someone@shiel.io"],
	"cc": ["paddy@shiel.io", "someone2@shiel.io"],
	"bcc": ["paddyshiel@gmail.com"],
	"from": "email.tester@shiel.io",
	"subject": "Multiple To, CC and BCC",
	"text": "EmailApi: Exposes a Rest API that accepts the necessary information in JSON Format and sends emails."
}
```

All fields are mandatory except for the `subject` field. Specific Providers (MailGun) will allow emails to be sent
without a subject field, but SendGrid will return a Bad Request (400). 

### Prerequisites

This project is build with Maven and runs with Java 8 +.

### API Keys
<mark>
The API Keys have been redacted from the github repository. To build this application you will need to provide the 
following api keys by setting the values in 
</mark> 

`src/main/resources/application.properties`

`provider.mailgun.apikey = ******`
`provider.sendgrid.apikey = ******`



### Installing

The application can be built using the following maven command
```
mvn clean install
```

This produces an executable `.jar` file running Spring Boot API.

## Running the tests

A suite of Unit and Integration tests can be ran with the following command
```
mvn clean test
```

These tests are using the Spock Framework (http://spockframework.org/spock/docs/1.3/index.html) and written in `groovy`

## Deployment

The application can be built and ran locally using 
```
mvn clean install
```
```
java -jar ./target/email-api-1.0-SNAPSHOT.jar
```

### Live Deployment

Additionally, the application has been deployed to AWS.
- The app is running in HA mode across 3 EC2 instances

`http://ec2-13-239-27-66.ap-southeast-2.compute.amazonaws.com:8080/swagger-ui.html`

`http://ec2-13-211-228-54.ap-southeast-2.compute.amazonaws.com/swagger-ui.html`

`http://ec2-52-64-133-30.ap-southeast-2.compute.amazonaws.com/swagger-ui.html`

- These 3 instances are in a Target group behind a loadbalancer (http://email-api-1372118349.ap-southeast-2.elb.amazonaws.com/swagger-ui.html)

- And for convenience I have added a DNS record for a friendly URL (http://api.shiel.io/swagger-ui.html)

## TO-DO:
[TODO-1]: Code Coverage Improvement
- Currently the unit test coverage is below acceptable requirements if this app was to be shipped.
- I have implemented tests to verify API behaviour using MockMvc, and parameterised unit tests using Spock as examples 
to show how I would like to proceed.

[TODO-2]: Create a E2E docker environment which will support
* The application running in a container
* WireMock running in a container (http://wiremock.org/) to simulate provider endpoints
* A suite of tests running with Serenity (http://www.thucydides.info/#/) to allow specification by example acceptance testing.

This Acceptance test suite can be ran using

```
mvn clean verify
```

[TODO-3]: Implement Infrastructure As Code to auto-deploy application and resources
- Create CloudFormation or other to automatically provision the necessary deployment resources.
- Currently the deployment has been done manually


[TODO-4]: Review Application Architecture
- As there is very little state to manage; this application behaviour may suit a serverless approach.
AWS Lambda lets you run code without provisioning or managing servers. You pay only for the compute time you consume.
With Lambda, you can run code for virtually any type of application or backend service - all with zero administration. Just upload your code and Lambda takes care of everything required to run and scale your code with high availability. You can set up your code to automatically trigger from other AWS services or call it directly from any web or mobile app.


## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Create stand-alone Spring applications
* [Maven](https://maven.apache.org/) - Dependency Management
* [Apache Http Components Client](https://hc.apache.org/httpcomponents-client-4.5.x/index.html) - HTTP Client used to integrate with Email Provider HTTP Apis
* [Swagger](https://swagger.io/) -  HTTP API Definition and documentation
## Authors

* **Patrick Shiel** - *Initial work* - (https://github.com/patrickshiel/email-api)

## License

This project is licensed under GNU GENERAL PUBLIC LICENSE

