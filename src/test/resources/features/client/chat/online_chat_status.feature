Feature: Online Chat Status API

  This feature tests the functionality of checking the online status of Super App users in the Dashen Super App.
  It covers scenarios for verifying whether registered contacts are currently online or offline.

  The scenarios verify:
  - Successful retrieval of online status for multiple registered Super App contacts
  - Correct response values for both online (true) and offline (false) statuses
  - Proper HTTP status codes for successful status checks
  - Accurate mapping of user IDs or contacts to their current online status

  Scenario: Successfully check online status for different registered Super App contacts
    Given the "user_02" user logs in and obtains an access token
    When the user start to check online status through "ONLINE_CHAT_STATUS_URL" with "contants_01" to check active status
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.onlinestatus" with the value "true"


  Scenario: Successfully check online status for different registered Super App contact
    Given the "user_02" user logs in and obtains an access token
    When the user start to check online status through "ONLINE_CHAT_STATUS_URL" with "contants_01" to check active status of user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.onlinestatus" with the value "false"

