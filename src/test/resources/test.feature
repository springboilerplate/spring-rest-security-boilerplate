Feature: Register, Token, Confirmation, Login and Accessing Tests
  
  Scenario: Registration Test
  	Given configurations /config 
  	When username password and email are "volkan" AND "123456" AND "volkandogan.d@gmail.com"
  	Then visitor calls /register
  	When attempt to register with registered email but different username "volkan2" AND "123456" AND "volkandogan.d@gmail.com"
  	And make sure that there is already an account with that email "volkandogan.d@gmail.com"
	Then make sure register is failure with HTTP Response Conflict and there is no user with different username "volkan2"
  	When attempt to register with registered username "volkan" AND "123456" AND "volkandogan.dd@gmail.com"
	And make sure that there is already an account with that username "volkan"
	Then make sure register is failure with HTTP Response Conflict and there is no user with different email "volkandogan.dd@gmail.com"
	
  Scenario: Testing Resend Token and Confirmation
  	When Resend Token with unregistered user "unregistered@unregistered.com"
  	Then Make sure that user is null
  	When Resend Token "volkandogan.d@gmail.com"
  	And Make sure that user is not null
  	Then Make sure that user is not active
  	When Confirm Token
  	And Make sure that user is not null
  	Then Make sure that user is active "volkandogan.d@gmail.com"
  	When Resend Token for already confirmed user "volkandogan.d@gmail.com"
  	And Make sure that user is not null
  	Then Make sure that user is active "volkandogan.d@gmail.com"
  	
  Scenario: Reset password by email 
  	When Reset password by email "volkandogan.d@gmail.com"
  	Then Reset password parameters with "654321" AND "654321"
  	
  Scenario: Login Test
  	When Login with bad creds username and password are "volkan" AND "123"
  	When Successful login username and password are "volkan" AND "654321"
 
  Scenario: Access Test
	Then Access with token /test
    Then Access without token /test