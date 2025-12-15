Feature: ETHIOTELECOM AND SAFARI TOPUP API

  Scenario: Successful Ethiotelecom Topup
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends "TOPUP_ETHIOTELECOM_URL" request with "topup_self_01" to perform topup with ethiotelecom
