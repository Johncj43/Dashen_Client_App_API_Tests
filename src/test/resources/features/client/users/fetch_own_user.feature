Feature: Fetch own user API

  Scenario: Client successfully fetches their own user details
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "FETCH_OWN_USER_URL" with "user_02" to fetch their own user details
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "User data retrieved successfully"

  Scenario: Client fails to get upload avatar without deviceUUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "FETCH_OWN_USER_URL" with "user_03" to fetch their own user details
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to get upload avatar with an invalid token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "FETCH_OWN_USER_URL" with "user_04" to fetch their own user detail
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"






