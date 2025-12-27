Feature: Online Chat Status API

  Scenario: Successfully check online status for different registered Super App contacts
    Given the "user_02" user logs in and obtains an access token
    When the user start to check online status through "ONLINE_CHAT_STATUS_URL" with "contants_01" to check active status
    Then the response status code should be 200
    And the response should contain a field named "data.onlinestatus" with the value "true"


  Scenario: Successfully check online status for different registered Super App contact
    Given the "user_02" user logs in and obtains an access token
    When the user start to check online status through "ONLINE_CHAT_STATUS_URL" with "contants_01" to check active status of user
    Then the response status code should be 200
    And the response should contain a field named "onlinestatus" with the value "false"

