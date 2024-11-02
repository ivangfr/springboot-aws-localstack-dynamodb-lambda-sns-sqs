# springboot-aws-localstack-dynamodb-lambda-sns-sqs

In this project, we are going to use [`LocalStack`](https://localstack.cloud/) to simulate locally, some services provided by [`AWS Cloud`](https://aws.amazon.com/) such as: [`DynamoDB`](https://aws.amazon.com/dynamodb/), [`Lambda`](https://aws.amazon.com/lambda/), [`SNS`](https://aws.amazon.com/sns/) and [`SQS`](https://aws.amazon.com/sqs/). Also, in order to simplify the use of AWS managed services, we are going to use [`Spring Cloud AWS`](https://spring.io/projects/spring-cloud-aws).

> **Note**: Also, take a look at the [`springboot-aws-localstack-opensearch-s3-secretsmanager`](https://github.com/ivangfr/springboot-aws-localstack-opensearch-s3-secretsmanager) repository. There, we have implemented two Spring Boot applications for indexing and searching movies. We also used LocalStack to simulate AWS Cloud services locally, such as [`OpenSearch`](https://aws.amazon.com/opensearch-service/), [`S3`](https://aws.amazon.com/s3/), and [`Secrets Manager`](https://aws.amazon.com/secrets-manager/).

## Proof-of-Concepts & Articles

On [ivangfr.github.io](https://ivangfr.github.io), I have compiled my Proof-of-Concepts (PoCs) and articles. You can easily search for the technology you are interested in by using the filter. Who knows, perhaps I have already implemented a PoC or written an article about what you are looking for.

## Additional Readings

- \[**Medium**\] [**Spring Boot apps to trigger and consume DynamoDB News table updates using AWS Lambda, SNS and SQS**](https://medium.com/@ivangfr/spring-boot-apps-to-trigger-and-consume-dynamodb-news-table-updates-using-aws-lambda-sns-and-sqs-957570cf9a3a)
- \[**Medium**\] [**Spring Boot Apps for Movie Indexing/Search with AWS OpenSearch, S3 and Secrets Manager**](https://medium.com/@ivangfr/spring-boot-apps-for-movie-indexing-search-with-aws-opensearch-s3-and-secrets-manager-a95ad0697e51)
- \[**Medium**\] [**Implementing a Spring Boot App using AWS DynamoDB as database**](https://medium.com/@ivangfr/implementing-a-spring-boot-app-using-aws-dynamodb-as-database-5dbf8b7fc924)
- \[**Medium**\] [**Implementing a Spring Boot App that uses AWS Secrets Manager to store its MongoDB credentials**](https://medium.com/@ivangfr/implementing-a-spring-boot-app-that-uses-aws-secrets-manager-to-store-its-mongodb-credentials-f805a4c74d9a)
- \[**Medium**\] [**Implementing a Serverless AWS Lambda with Spring Cloud Function & AWS Adapter**](https://medium.com/@ivangfr/implementing-a-serverless-aws-lambda-with-spring-cloud-function-aws-adapter-05fd6d48ba45)
- \[**Medium**\] [**Using AWS SNS and SQS to stream Alerts from a Spring Boot producer to consumers**](https://medium.com/@ivangfr/using-aws-sns-and-sqs-to-stream-alerts-from-a-spring-boot-producer-to-consumers-0b0a974e40fc)

## Project Diagram

![project-diagram](documentation/project-diagram.jpeg)

## Applications

- ### news-producer

  [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) Java Web application that exposes a REST API to manage news. It uses DynamoDB as database.

  It has the following endpoints:
  ```
     GET /api/news
     GET /api/news/{id}
    POST /api/news {"title": "..."}
    POST /api/news/randomly
  DELETE /api/news/{id}
  ```

- ### dynamodb-lambda-function

  [`Spring Cloud Function`](https://spring.io/projects/spring-cloud-function) application that uses [`AWS Adapter`](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html) to convert it to a form that can run in `AWS Lambda`.

  `dynamodb-lambda-function` listens to events emitted by an event-source created to monitor changes in `DynamoDB` news table. Once it receives an event, it processes it and publishes a news event to an `SNS` topic. Later, `SNS` publishes the news event to a `SQS` queue.

- ### news-consumer

  `Spring Boot` Java Web application that polls the news events that are queued in a `SQS` queue.

## Prerequisites

- [`Java 21+`](https://www.oracle.com/java/technologies/downloads/#java21)
- [`Docker`](https://www.docker.com/)

## Package dynamodb-lambda-function jar

- In a terminal, make sure you inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder

- Run the following script
  ```
  ./package-dynamodb-lambda-function-jar.sh
  ```
  When `Maven` packaging finishes, the jar file generated in `dynamodb-lambda-function/target` folder is copied to `dynamodb-lambda-function/shared` folder

## Start and Initialize LocalStack

- In a terminal, make sure you are in inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder

- Start `LocalStack` Docker container
  ```
  DEBUG=1 docker compose up -d
  ```

- \[Optional\] Debug logs are enabled so that we have more insights about what is happening. To monitor `localstack` Docker container logs, run the command below
  ```
  docker logs localstack -f
  ```

- Initialize `LocalStack` by running the following script
  ```
  ./init-localstack.sh
  ```
  The script will create:
  - create `news-topic` in `SNS`;
  - create `news-consumer-queue` in `SQS`;
  - subscribe `news-consumer-queue` to `news-topic`;
  - create `news` table in `DynamoDB`;
  - create `ProcessDynamoDBEvent` Lambda function;
  - create an `event-source-mapping` to connect `DynamoDB` to `ProcessDynamoDBEvent` Lambda function.

## Running applications with Maven

- **news-producer**

  In a terminal and, inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder, run the following command
  ```
  export AWS_REGION=eu-west-1 && export AWS_ACCESS_KEY_ID=key && export AWS_SECRET_ACCESS_KEY=secret && \
    ./mvnw clean spring-boot:run --projects news-producer
  ```

- **news-consumer**

  In another terminal and, inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder, run the command below
  ```
  export AWS_REGION=eu-west-1 && export AWS_ACCESS_KEY_ID=key && export AWS_SECRET_ACCESS_KEY=secret && \
    ./mvnw clean spring-boot:run --projects news-consumer
  ```

## Running applications as Docker container

- ### Build Docker images

  In a terminal and, inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder, run the following script
  ```
  ./docker-build.sh
  ```

- ### Run Docker containers

  - **news-producer**

    In a terminal, run the following command
    ```
    docker run --rm --name news-producer -p 9080:9080 \
      -e AWS_REGION=eu-west-1 -e AWS_ACCESS_KEY_ID=key -e AWS_SECRET_ACCESS_KEY=secret \
      --network=springboot-aws-localstack-dynamodb-lambda-sns-sqs_default \
      ivanfranchin/news-producer:1.0.0
    ```

  - **news-consumer**

    In a new terminal, run the command below
    ```
    docker run --rm --name news-consumer -p 9081:9081 \
      -e AWS_REGION=eu-west-1 -e AWS_ACCESS_KEY_ID=key -e AWS_SECRET_ACCESS_KEY=secret \
      -e NEWS_PRODUCER_URL=http://news-producer:9080 \
      --network=springboot-aws-localstack-dynamodb-lambda-sns-sqs_default \
      ivanfranchin/news-consumer:1.0.0
    ```

## Application URL

| Application     | Type    | URL                                   |
|-----------------|---------|---------------------------------------|
| `news-producer` | Swagger | http://localhost:9080/swagger-ui.html |
| `news-consumer` | UI      | http://localhost:9081                 |

## Playing around

- **Creating news**

  - In a terminal, run the following command
    ```
    curl -i -X POST http://localhost:9080/api/news \
      -H 'Content-Type: application/json' \
      -d '{"title": "Palmeiras is three-time champion of the Copa Libertadores da América"}'
    ```

    or to create news randomly
    ```
    curl -i -X POST http://localhost:9080/api/news/randomly
    ```

  - In `news-consumer` UI, the news should be displayed

- **Deleting news**

  - In a terminal, run the following command
    ```
    curl -i -X DELETE http://localhost:9080/api/news/<NEWS-ID>
    ```

  - In `news-consumer` UI, the news should be removed

## Demo

In the `GIF` below, we use `news-producer` Swagger UI to create one random news. Then, we delete the news created previously. Finally, we create more two news randomly.

![demo](documentation/demo.gif)

## Shutdown

- To stop applications, go to the terminal where they are running and press `Ctrl+C`
- To stop and remove docker compose containers, network and volumes, go to a terminal and, inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder, run the following command
  ```
  docker compose down -v
  ```

## Cleanup

To remove the Docker images created by this project, go to a terminal and, inside `springboot-aws-localstack-dynamodb-lambda-sns-sqs` root folder, run the script below
```
./remove-docker-images.sh
```
