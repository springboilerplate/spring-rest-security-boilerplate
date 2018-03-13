# Spring Rest Security Boilerplate

In this project, We, [Ugurcan Lacin](https://github.com/ugurcanlacin) and [Volkan Dogan](https://github.com/volkandgn), are going to build a boilerplate by applying most common Java framework which is Spring. 

We will build Rest APIs by helping of Spring Security and Spring Boot. Every configuration will be Java based Annotations instead XML.

We will try to cover all security problems and their solutions as much as we can do. 

* Registration
* Activation by e-mail
* Resend verification email
* Password Encoding
* Resetting Password

* Token based authentication
* Token expire
* Roles
* Privileges
* Prevent Brute Force attempts

# How to run
-----------------
- Clone this repository 
- Import > Existing Maven Project
- Open MySQL > Create Schema > with the name of `spring-rest-security-boilerplate`
- Install RabbitMQ 
```
  $ sudo apt-get update
  $ sudo apt-get install rabbitmq-server`
  $ service rabbitmq-server start
```
  
- In e-mail progress : 

   - a) If you want to send tokens to mail addresses then mail of the sender address has to be configured in the `application.properties`. Permissions may be needed for defined mail which you should configure.
   
    - b) If you do not want to use mail operations, some informations will be printed in the console (token, new token, bearer token etc.) that you can use them in the next steps.
    
```
This operation can be arranged by boolean SEND_MAIL 
which is in the SpringRestSecurityBoilerplateApplication class
If it is assigned as true mail configurations will be mandatory.
If it is false mail configurations will not be mandatory.
Default value will be false.
```
 #### Example
 
 Project's port is 8082. It can be changed in the application.properties.
 
 -----------------------------
 
 - Registration: Register a new user
 
 POST Method/JSON
 ```
 localhost:8082/register
  ```
   ```
 {
  "name" : "name",
  "surname" : "surname",
  "email" : "email",
  "username" : "user",
  "password" : "password"
  
}
 ```
 > "email" is important. Mail(Token) will be directly sent to this address if mail configurations are completed.
 -----------------------
 - Resend Token: Re-produce token.
 
 GET Method
 ```
 localhost:8082/resend/`registered-mail-address`
 ```
 > 'registered-mail-address' is a path variable value that should be filled registered mail. Token will be re-procuded and sent to the this email again.
 
 Example: 
 `localhost:8082/resend/example@example.com`
 
 -------------------------
 
 - Token Confirmation: To verify user
 
 GET Method
 ```
 localhost:8082/confirm/'token'
 ```
 
 > 'token' is a path variable value that should be filled verification token which is sent to the e-mail/console.
 
 Example: 
 `localhost:8082/confirm/0dbaddb6-e9c1-44d6-9cd5-a88e5b17b4b5`
 
 ----------------------
 - Login
 
 POST Method/JSON
  ```
 localhost:8082/login
 ```
 ```
 {
    "username": "user",
    "password": "password"
}
```
> When login informations are valid, authentication will be processed and Bearer token will be shown in the console/Headers.

--------------------------------------------
- Test Function: After authentication, test for authorization.

GET Method
```
localhost:8082/test
```

> Important: Valid Bearer token is needed for accessing. Otherwise access will be denied.

> How to add: For example; POSTMAN > Authorization > TYPE > Bearer Token and add token.

Token example: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.XbPfbIHMI6arZ3Y922BhjWgQzWXcXNrz0ogtVhfEd2o`

