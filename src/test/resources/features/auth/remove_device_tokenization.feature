Feature: Remove device tokenization API
  This feature validates the device token removal functionality in the client app.
  It ensures that users can successfully unsubscribe a device, and that the system
  handles unauthorized or invalid requests correctly. The scenarios verify proper
  response codes and messages for both successful and failed removal attempts.

  Scenario: User successfully remove the device tokenization
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "REMOVE_DEVICE_TOKENIZATION_URL" with a "remove device" with access token
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Device unsubscribed successfully."


  Scenario: User fails to remove device tokenization
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "REMOVE_DEVICE_TOKENIZATION_URL" with a "remove device" with access token
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

