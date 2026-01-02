Feature: As a registered user
  I want to fetch CBS transactions
  So that I can view transaction history securely


  Scenario: Successfully fetch CBS transaction
    Given the "account_03" user logs in and obtains an access token
    When device user "account_03" sends "FETCH_CBS_TRANSACTION" for fetch Cbs transaction
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transactions fetched successfully"


  Scenario:  Fetch CBS transaction with invalid account number
    Given the "account_03" user logs in and obtains an access token
    When device user "account_04" sends "FETCH_CBS_TRANSACTION" for fetch Cbs transaction
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid account number"


  Scenario:  Fetch CBS transaction with another user's account
    Given the "account_03" user logs in and obtains an access token
    When device user "account_05" sends "FETCH_CBS_TRANSACTION" for fetch Cbs transaction
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Access denied for this account"