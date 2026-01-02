Feature: Fetch Transactions API

  Scenario: Successfully fetch available transactions
    Given the "account_03" user logs in and obtains an access token
    When device user "account_03" sends "FETCH_TRANSACTIONS" for fetch transactions
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transactions fetched successfully"