Feature: Linked Account API

  Scenario: Client successfully obtains linked account information
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "LINKED_ACCOUNT_URL" with "user_02" to get linked account details
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Linked accounts fetched successfully"

    Scenario: Client fails to fetch linked account without deviceUUID
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "LINKED_ACCOUNT_URL" with "user_03" to get linked account details
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to fetch linked account without deviceUUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "LINKED_ACCOUNT_URL" with "user_04" to get linked account detail
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"



