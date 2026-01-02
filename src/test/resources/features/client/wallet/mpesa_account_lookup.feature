Feature: Mpesa wallet API
  Background:
    Given the "user_B" user logs in and obtains an access token
  Scenario: Successful account lookup for Tele Birr
    When the client sends a POST request to "MPESA_ACCOUNT_LOOKUP_URL" with "valid_Mpesa_wallet" to perform account lookup for Mpesa wallet
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Suspended wallet account"