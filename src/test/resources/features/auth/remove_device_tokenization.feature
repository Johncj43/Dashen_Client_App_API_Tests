Feature: Remove device tokenization API

  Scenario: User successfully remove the device tokenization
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "REMOVE_DEVICE_TOKENIZATION_URL" with a "remove device" with access token

  Scenario: User fails to remove device tokenization
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "REMOVE_DEVICE_TOKENIZATION_URL" with a "remove device" with access token
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

