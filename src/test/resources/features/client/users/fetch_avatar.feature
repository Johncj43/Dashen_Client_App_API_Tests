Feature: Fetch Avatar API

  Scenario: Client successfully get upload avatar
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "FETCH_AVATAR_URL" with a "user_02" to get an upload avatar
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Avatar retrieved successfully"

    Scenario: Client fails to get upload avatar without deviceUUID
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "FETCH_AVATAR_URL" with a "user_03" to get an upload avatar
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"






