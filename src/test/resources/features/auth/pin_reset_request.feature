Feature: PIN Reset API

  Scenario: Client successfully resets the PIN
    Given I send a POST request to "PIN_RESET_REQUEST_URL" with a "registered deviceUUID" to look up the user
    When I send a POST request to "PIN_RESET_CONFIRM_URL" with a "registered deviceUUID" valid OTP to approve the pin reset request
    And I send a POST request to "SET_PIN_URL" with a "registered deviceUUID" and new password
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN set successfully."

  Scenario: Client fails to reset PIN an unregistered device UUID
    Given I send a POST request to "PIN_RESET_REQUEST_URL" with a "unregistered deviceUUID" to look up the user
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Unkonwn device, please link your device"

  Scenario: Client Fails to Enter a invalid OTP for PIN Reset Confirmation
    Given I send a POST request to "PIN_RESET_REQUEST_URL" with a "invalid  OTP" to look up the user
    When I send a POST request to "PIN_RESET_CONFIRM_URL" with a "invalid  OTP"  to approve the pin reset request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Incorrect Verification Code"

  Scenario: Client fails to set a weak PIN during PIN reset
    Given I send a POST request to "PIN_RESET_REQUEST_URL" with a "weak password" to look up the user
    When I send a POST request to "PIN_RESET_CONFIRM_URL" with a "weak password" valid OTP to approve the pin reset request
    And I send a POST request to "SET_PIN_URL" with a "weak password" and new password










