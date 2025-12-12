Feature: Set Primary notification API

  Scenario: Client successfully sets primary notification through phone
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_02" to set primary notification through email
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Primary authentication updated successfully"

  Scenario: Client successfully sets primary notification through email
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_05" to set primary notification through email
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Primary authentication updated successfully"

  Scenario: Client successfully sets primary notification through both
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_06" to set primary notification through email
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Primary authentication updated successfully"


  Scenario: Client fails sets primary notification channel with empty field
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_07" to set primary notification through email
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid request parameters"

  Scenario: Client fails sets primary notification without deviceUUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_03" to set primary notification through email
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to set primary notification channel with an invalid token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "SET_PRIMARY_NOTIFICATION_URL" with "user_04" to set primary notification through emails
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"













