Feature: Account Lookup
  As a registered user
  I want to perform an mini statement
  So that I can verify account details

  Scenario: Successfully validate an ACTIVE account
    Given the "account_03" user logs in and obtains an access token
    When device user "account_03" sends "MINI_STATEMENT_URL" for mini statement
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Statement retrieved successfully"


  Scenario: Fetch mini statement when no transactions exist
    Given the "user_02" user logs in and obtains an access token
    When device user "user_02" sends "MINI_STATEMENT_URL" for mini statement
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Data/statement not found"



