Feature: PIN Verification during Transaction Flow API
  As an authenticated client application
  Users must verify their PIN before authorizing sensitive transactions (e.g., transfers, bill payments, airtime top-ups)
  To ensure transaction security and prevent unauthorized operations

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





