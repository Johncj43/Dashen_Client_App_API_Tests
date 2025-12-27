Feature: PIN Reset API

  Scenario: Client successfully resets the PIN
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "PIN_RESET_REQUEST_URL" with a "registered deviceUUID" to look up the user
    And I send a POST request to "PIN_RESET_CONFIRM_URL" with a "registered deviceUUID" valid OTP to approve the pin reset request
    And I send a POST request to "SET_PIN_URL" with a "registered deviceUUID" and new password
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN set successfully."

  Scenario: Client Fails to Enter a invalid OTP for PIN Reset Confirmation
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "PIN_RESET_REQUEST_URL" with a "invalid  OTP" to look up the user
    And I send a POST request to "PIN_RESET_CONFIRM_URL" with a "invalid  OTP"  to approve the pin reset request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Incorrect Verification Code"

  Scenario: Client fails to set a weak PIN during PIN reset
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "PIN_RESET_REQUEST_URL" with a "weak password" to look up the user
    And I send a POST request to "PIN_RESET_CONFIRM_URL" with a "weak password" valid OTP to approve the pin reset request
    And I send a POST request to "SET_PIN_URL" with a "weak password" and new password for pin reset
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."











