# Wecky - Website Checker

This is mostly an AWS playground. :-)

## Domain
Checks a website in a predefined interval and informs the user if anything has changed.

## Architecture
![Wecky Architecture](wecky_architecture.png)

## Getting started 
Before firing up the application for the first time, you need to setting up a local dynamodb instance. The dynmodb url then needs to be added to the [application.properties](https://github.com/smartsquare/wecky-web/blob/master/src/main/resources/application.properties) along with your aws credentials and region.

Further reading:
* [Setting Up DynamoDB Locally](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html)
* [Working with AWS Credentials](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html)
* [Configuration and Credential Files](https://docs.aws.amazon.com/cli/latest/userguide/cli-config-files.html)
