Feature: PIN Verification during Transaction Flow API
  This feature tests the PIN verification functionality in the Dashen Super App.
  It ensures that users must verify their PIN before authorizing sensitive transactions, including transfers, bill payments, and airtime top-ups, to maintain transaction security and prevent unauthorized operations.

  The scenarios verify:
  - Successful PIN verification when the correct PIN is provided
  - Proper handling and error messages when an incorrect PIN is entered
  - Validation of required fields, including handling of empty PIN inputs
  - Accurate HTTP status codes for each scenario
  - Confirmation messages returned in the response indicating whether the PIN verification succeeded or failed
  Scenario: Successfully verify PIN for a transaction by entering the correct PIN
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN confirmed"

  Scenario: Unsuccessful PIN verification for a transaction when entering an incorrect PIN
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "incorrect pin" to verify the PIN for the transaction
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "incorrect credential provided"

  Scenario: PIN verification fails when the PIN field is empty
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "empty field pin" to verify the PIN for the transaction
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "incorrect credential provided"





