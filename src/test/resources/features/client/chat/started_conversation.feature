Feature: Started Conversation API

  Scenario: Successfully start a chat conversation with different registered Super App users
    Given the "user_02" user logs in and obtains an access token
    When the user start to conversation through "STARTED_CONVERSATION_URL" with "contants_01" to chat each other through super app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
