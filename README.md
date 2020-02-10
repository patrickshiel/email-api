# Email Api:

Exposes a Rest API that accepts the necessary information in JSON Format and sends emails.

The application provides an abstraction between two different email service providers. 
- Send Grid (https://sendgrid.com/docs/API_Reference/Web_API_v3/index.html)
- Mail Gun (https://documentation.mailgun.com/en/latest/api-sending.html)

If one of the services goes down, your service can quickly failover to a different provider without affecting your customers.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### API Documentation - Swagger

Live running documentation for the definition of the `email` api has been defined using swagger and can be accessed here:

http://api.shiel.io/swagger-ui.html

`HTTP POST requests` can be made to http://api.shiel.io/email with the following Body structure
```
{
	"to": "paddyshiel@gmail.com",
	"from": "paddyshiel@outlook.com",
	"subject": "EmailApi delivering mails via multiple providers",
	"text": "Exposes a Rest API that accepts the necessary information in JSON Format and sends emails."
}
```


### Prerequisites

This project is build with Maven and runs with Java 8 +.

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

### End to end tests

[TODO-1]: Create a E2E docker environent which will support
* The application running in a container
* WireMock running in a container (http://wiremock.org/) to simulate provider endpoints
* A suite of tests running with Serenity (http://www.thucydides.info/#/) to allow specification by example acceptance testing.

This Acceptance test suite can be ran using

```
mvn clean verify
```

## Deployment

The application can be built and ran locally using 
```
mvn clean verify
```
```
java -jar ./target/email-api-1.0-SNAPSHOT.jar
```

### Live Deployment

Additionally, the application has been deployed to AWS.
- The app is running in HA mode across 3 EC2 instances
-- http://ec2-13-239-27-66.ap-southeast-2.compute.amazonaws.com:8080/swagger-ui.html
-- http://ec2-13-211-228-54.ap-southeast-2.compute.amazonaws.com/swagger-ui.html
-- http://ec2-52-64-133-30.ap-southeast-2.compute.amazonaws.com/swagger-ui.html

- These 3 instances are in a Target group behind a loadbalancer (http://email-api-1372118349.ap-southeast-2.elb.amazonaws.com/swagger-ui.html)

- And for convenience I can added a DNS record for a friendly URL (http://api.shiel.io/swagger-ui.html)

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Create stand-alone Spring applications
* [Maven](https://maven.apache.org/) - Dependency Management
* [Apache Http Components Client](.https://hc.apache.org/httpcomponents-client-4.5.x/index.html) - HTTP Client used to integrate with Email Provider HTTP Apis
* [Swagger](https://swagger.io/) -  HTTP API Definition and documentation
## Authors

* **Patrick Shiel** - *Initial work* - (https://github.com/patrickshiel/email-api)

## License

This project is licensed under GNU GENERAL PUBLIC LICENSE

