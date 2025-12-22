Feature: Fetch Encrypted Account API

  Scenario: successfully fetch encrypted account
    Given the "user_02" user logs in and obtains an access token
    When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_02" to get encrypted account
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Encrypted account number generated successfully"

  Scenario: Fails to fetch encrypted account with empty field of account number
    Given the "user_02" user logs in and obtains an access token
    When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_08" to get encrypted account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid request"


    Scenario: Fails to fetch encrypted account without deviceUUID
      Given the "user_02" user logs in and obtains an access token
      When I send  "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_03" to get encrypted account
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

      Scenario: Fails to fetch encrypted account without access token
        Given the "user_02" user logs in and obtains an access token
        When I send "FETCH_ENCRYPTED_ACCOUNT_URL" with a "user_04" to get encrypted accounts
        Then the response status code should be 401
        And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"




