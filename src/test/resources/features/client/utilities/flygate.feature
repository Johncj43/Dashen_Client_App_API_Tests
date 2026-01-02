Feature: Get flyGate ticket
  As a registered user
  I want to perform a flyGate ticket

  Scenario: Successfully lookup an account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "FLYGATE_URL" for flyGate ticket lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
