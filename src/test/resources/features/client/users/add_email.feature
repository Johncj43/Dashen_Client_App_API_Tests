Feature: Add email API
  This feature tests the functionality of adding an email to a user account in the Dashen Super App.
  It ensures that users can successfully add an email, while handling invalid operations and enforcing security checks.

  The scenarios verify:
  - The system prevents adding an email that is already associated with the account
  - Proper error handling when the email field is empty
  - Correct HTTP status codes and messages when an invalid access token is used
  - Validation of the device UUID to prevent unregistered devices from adding emails
  - Accurate response messages indicating the success or failure of the email addition operation

  Scenario: Client fails to add an email if it is already added
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "ADD_EMAIL_URL" with "user_02" to add an email to the app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "email set. please visit your nearest branch to change your email"

    Scenario: User fails to add an email with empty field
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "ADD_EMAIL_URL" with "user_10" to add an email to the app
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

   Scenario: User fails to add an email with an invalid access token
        Given the "user_02" user logs in and obtains an access token
        When I send a POST request to "ADD_EMAIL_URL" with "user_04" to add an email with an invalid access token
        Then the response status code should be 401
        And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"

  Scenario: User fails to add an email with an unregistered deviceUUID
    Given the "user_02" user logs in and obtains an access token
        When I send a POST request to "ADD_EMAIL_URL" with "user_03" to add an email to the app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "invalid request"








