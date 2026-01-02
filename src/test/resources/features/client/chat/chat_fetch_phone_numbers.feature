Feature: Chat Fetch Phone Number API

  This feature validates the functionality of fetching phone number details of contacts
  registered in the Super App. It ensures correct identification of registered,
  unregistered, and empty contact lists, and verifies that the system returns
  accurate and consistent responses.

  Scenario: Successfully fetch details for phone numbers of contacts registered in the Super App
    Given the "user_02" user logs in and obtains an access token
    When the client sends a POST request to "FETCH_PHONE_NUMBER_URL" using test data "contants_01" to lookup a contact registered in the Super App
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"

  Scenario: Phone number lookup fails for the contact which is not registered in the Super App
    Given the "user_02" user logs in and obtains an access token
    When the client sends a POST request to "FETCH_PHONE_NUMBER_URL" using test data "contants_01" to lookup a contact unregistered in the Super App
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.superappusers" with the value "[]"

  Scenario: Phone number lookup fails when an empty contacts is provided
    Given the "user_02" user logs in and obtains an access token
    When the client sends a POST request to "FETCH_PHONE_NUMBER_URL" using test data "contants_01" to lookup with empty contacts
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Success"
    And the response should contain a field named "data.superappusers" with the value "[]"
    And the response should contain a field named "data.unregistereds" with the value "[]"
    And the response should contain a field named "data.invitednumbers" with the value "[]"








