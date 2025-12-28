Feature: Fetch Linked Account API
  This feature tests the functionality of retrieving linked account information in the Dashen Super App.
  It ensures that authenticated users can access their linked accounts while enforcing proper validations for device UUID and access token.

  The scenarios verify:
  - Successful retrieval of linked account details for a registered and authenticated user
  - Proper error handling when the device UUID is missing or unregistered
  - Rejection of requests with an invalid or missing access token
  - Correct HTTP status codes and response messages indicating success or failure

  Scenario: Client successfully obtains linked account information
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "LINKED_ACCOUNT_URL" with "user_02" to get linked account details
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Linked accounts fetched successfully"

    Scenario: Client fails to fetch linked account without deviceUUID
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "LINKED_ACCOUNT_URL" with "user_03" to get linked account details
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to fetch linked account without deviceUUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "LINKED_ACCOUNT_URL" with "user_04" to get linked account detail
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"



