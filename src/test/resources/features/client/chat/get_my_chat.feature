Feature:  Get My Chat API
  This feature tests the retrieval of chat conversation history in the Dashen Super App.
  It covers scenarios for fetching chat history between Super App users and handling cases where no previous conversation exists.

  The scenarios verify:
  - Successful retrieval of chat conversation history with another registered Super App user
  - Handling of non-registered contacts or users with no chat history
  - Correct response structure, including message values and chat start indicators
  - Proper HTTP status codes for successful retrieval

  Scenario: Successfully retrieve chat conversation history with another Super App user
    Given the "user_02" user logs in and obtains an access token
    When I send a GET request to "GET_MY_CHAT_URL" with "contants_01" to get my chat
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.startedchat.startedchat" with the value "[true]"

  Scenario: Chat conversation history retrieval returns no history for non-registered contacts Super App user
    Given the "sender_user_frozen" user logs in and obtains an access token
    When I send a GET request to "GET_MY_CHAT_URL" with "contants_02" to get my chat
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.startedchat.startedchat" with the value "[]"






