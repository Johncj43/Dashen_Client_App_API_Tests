Feature: Set default account API

  Scenario: Client successfully sets a default account
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_DEFAULT_ACCOUNT_URL" with "user_02" to set a default account
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Default account set successfully"


    Scenario: User fails to set a default account without access token
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "SET_DEFAULT_ACCOUNT_URL" with "user_04" to set a default accounts
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"

      Scenario: User fails to set a default account without deviceUUID
        Given the "user_02" user logs in and obtains an access token
        When I send a POST request to "SET_DEFAULT_ACCOUNT_URL" with "user_03" to set a default account
        Then the response status code should be 400
        And the response should contain a field named "message" with the value "invalid request"

  Scenario: User fails to set a default account with empty account
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_DEFAULT_ACCOUNT_URL" with "user_08" to set a default account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Account not found"

  Scenario: User fails to set a default account with empty account
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_DEFAULT_ACCOUNT_URL" with "user_09" to set a default account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Account length must be 13 digits"




