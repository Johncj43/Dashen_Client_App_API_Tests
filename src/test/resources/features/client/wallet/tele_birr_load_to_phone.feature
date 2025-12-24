Feature: Wallet API


  Scenario: Successful Birr load to phone number from account to Telebirr wallet
    Given the "user_B" user logs in and obtains an access token
    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with "valid_telebirr_wallet" to perform account lookup for Tele Birr
    And I send a POST request to "GET_FEES_URL" with a "tele_birr_load_data" to create get fees for tele birr wallet
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "telebirr_valid_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin" to verify the PIN for the transaction
    And the client sends a POST request to "TELE_BIRR_LOAD_TO_PHONE_URL" with "tele_birr_load_to_self" to transfer funds from the account to the TeleBirr wallet
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction completed successfully."
    And the response should contain a field named "data.type" with the value "WALLET"
    And the response should contain a field named "data.status" with the value "PAID"





