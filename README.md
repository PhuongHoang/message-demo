# Table of Contents
1. **[Description](#description)**</br>
1.1 **[REST APIs](#rest-apis)**</br>
1.2 **[Web based documentation](#web-based-documentation)**</br>
2. **[Build instruction](#build-instruction)**</br>
2.1 **[Requirements](#requirements)**</br>
2.2 **[Build command](#build-command)**</br>   
3. **[Run the application](#run-the-application)**</br>
3.1 **[Run using Gradle task](#run-using-gradle-task)**</br>
3.2 **[Run as Docker container](#run-as-docker-container)**</br>
4 **[Accessing the APIs](#accessing-the-apis)**</br>
4.1 **[Through Swagger UI](#through-swagger-ui)**</br>
4.2 **[Through Postman](#through-postman)**</br>
5 **[Design of the application](#design-of-the-application)**   

## Description
This is an example project to demonstrate an application that provides CRUD (Create-Retrieve-Update-Delete) operations for messages. In addition, the application provides a web UI that lists all the REST APIs exposed by this application. The following sections describe the features in details.

### REST APIs
The application exposes the following REST APIs to perform
1. Create a message
2. Update an existing message
3. Retrieve a message
4. Delete a message
5. Retrieve a list of messages (with pagination) 

The message contains the following details:

* The message content
* A flag indicating if the message is a palindrome
* The time the message was created, e.g. 2018-08-22T10:00:00Z
* The last time the message was updated, e.g. 2018-08-22T10:00:00Z
### Web based documentation
The application also exposes a web UI that describes all the REST APIs exposed by the application. It is based on Swagger UI. As a result, it allows the user to try out the REST API calls within the web UI as well.

## Build instruction
### Requirements
The application requires the following to be installed 
* JDK 15
* Docker engine
### Build command
Run the following command. This command builds the executable and publish a local Docker image to the local Docker registry.

```
./gradlew bootBuildImage --imageName=audition/message-demo
```

This command results in a new Docker image being built and stored locally with the following tag `audition/message-demo:latest`.

**Note:** The command takes quite a bit of time for the first run. After that, the build time should be less.

## Run the application
The application can run as either a Docker container or can run using the `bootRun` Gradle task
### Run using Gradle task
The application can run from the project directory. The command to run the application is below.
```
./gradlew bootRun
```
### Run as Docker container
In order to run the built Docker image (see section [Build command](#build-command)), you can use the following command. This command starts the application which listens to http://localhost:8080.

```
docker run -d -p 8080:8080 -t docker.io/audition/message-demo:latest
```

## Accessing the APIs
There are two main ways to access the APIs. They are listed below.

### Through Swagger UI
When the application runs, it serves a Swagger UI that lists all the available REST APIs provided by the application. The location of the Swagger UI is at http://localhost:8080/swagger-ui.html. This assumes that the port in the [Run the application](#run-the-application) section is kept as the default value of `8080`. If it runs with a different port, the Swagger UI has to be changed as well.

### Through Postman
There is a Postman collection that can be imported into Postman. It contains all the REST APIs with test data that can be sent to the application. The Postman collection is stored inside the [postman-script](/postman-script) folder.

## Design of the application
