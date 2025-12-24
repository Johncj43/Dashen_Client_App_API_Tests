Feature:  Get My Chat API

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






