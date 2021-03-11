
### Description
This is an example project to demonstrate CRUD operations for messages. The application provides REST APIs to perform the following

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

### Build an executable
#### Requirements
The application requires the following to be installed 
* JDK 15
* Docker engine
#### Build command
Run the following command, which will create a local Docker image

`./gradlew bootBuildImage --imageName=audition/message-demo`

This command results in a new Docker image being built and stored locally with the following tag `audition/message-demo:latest`.

### Run the application

In order to run the built Docker image (see the previous section), you can use the following command. This command starts the application which listens to `localhost:8080`.

`docker run -d -p 8080:8080 -t docker.io/audition/message-demo:latest`

### Accessing the APIs

#### Through Swagger UI
When the application runs, it serves a Swagger UI that lists all the available REST APIs provided by the application. The location of the Swagger UI is at http://localhost:8080/swagger-ui.html. This assumes that the port in the `Run the application` section is 8080.

#### Through Postman

### Design of the application
