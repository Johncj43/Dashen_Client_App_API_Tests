Feature: Remove avatar API

  Scenario: Client Successfully remove an upload avatar
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "REMOVE_AVATAR_URL" with a "user_02" to remove an upload avatar
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Profile picture removed successfully"

  Scenario: Client fails to remove uploaded images without providing a device UUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "REMOVE_AVATAR_URL" with a "user_03" to remove an upload avatar
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to remove uploaded images without an access token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "REMOVE_AVATAR_URL" with a "user_04" to remove an upload avatars
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"

