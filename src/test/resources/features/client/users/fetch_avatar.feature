Feature: Fetch Avatar API
  This feature tests the functionality of retrieving a user's avatar in the Dashen Super App.
  It ensures that users can successfully fetch their avatar while enforcing device and access validations.

  The scenarios verify:
  - Successful retrieval of an uploaded avatar for a registered and authenticated user
  - Proper error handling when the device UUID is missing or unregistered
  - Correct HTTP status codes and response messages indicating success or failure

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






