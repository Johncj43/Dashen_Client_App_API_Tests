Feature: Fetch Encrypted Account API
  This feature tests the functionality of retrieving an encrypted account number in the Dashen Super App.
  It ensures that users can successfully fetch encrypted account numbers while enforcing proper validations for account fields, device UUID, and access tokens.

  The scenarios verify:
  - Successful retrieval of an encrypted account number for a registered and authenticated user
  - Proper error handling when the account number field is empty
  - Rejection of requests without a registered device UUID
  - Rejection of requests with an invalid or missing access token
  - Correct HTTP status codes and response messages indicating success or failure

  Scenario: successfully fetch encrypted account
    Given the "user_02" user logs in and obtains an access token
    When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_02" to get encrypted account
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Encrypted account number generated successfully"

  Scenario: Fails to fetch encrypted account with empty field of account number
    Given the "user_02" user logs in and obtains an access token
    When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_08" to get encrypted account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid request"


    Scenario: Fails to fetch encrypted account without deviceUUID
      Given the "user_02" user logs in and obtains an access token
      When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_03" to get encrypted account
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

      Scenario: Fails to fetch encrypted account without access token
        Given the "user_02" user logs in and obtains an access token
        When I send "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_04" to get encrypted accounts
        Then the response status code should be 401
        And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"




