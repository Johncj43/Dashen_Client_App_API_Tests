Feature: Transaction Verification API
  Background:
    Given the "user_02" user logs in and obtains an access token

  Scenario: Successfully verify a transaction with valid data
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "successfully submitted"

    Scenario: Client fails to verify transaction with an invalid data token
      When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
      And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data" and an invalid data token
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "something went wrong, please try again later"

  Scenario: Client fails to verify transaction with an invalid access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data" and an invalid access token
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"






