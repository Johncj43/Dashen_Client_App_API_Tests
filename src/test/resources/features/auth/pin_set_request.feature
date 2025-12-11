Feature: Set Pin API

  Scenario: Client successfully set the PIN
    Given I send a POST request to "PIN_SET_REQUEST_URL" with a "New deviceUUID" to to set a PIN for the user’s registered phone number
    When I send a POST request to "PIN_SET_CONFIRM_URL" with a "New deviceUUID" valid OTP to approve the pin set request
    And I send a POST request to "SET_PIN_URL" with a "New deviceUUID" and new password with new device UUID
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN set successfully."

#  Scenario: Client fails to set PIN an already unregistered phone number
#    Given I send a POST request to "PIN_SET_REQUEST_URL" with a "unregister phone number" to to set a PIN for the user’s registered phone number
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "Unkonwn device, please link your device"

  Scenario: Client Fails to Enter a invalid OTP for PIN set Confirmation
    Given I send a POST request to "PIN_SET_REQUEST_URL" with a "Wrong OTP" to to set a PIN for the user’s registered phone number
    When I send a POST request to "PIN_SET_CONFIRM_URL" with a "Wrong OTP" valid OTP to approve the pin set requests
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Incorrect Verification Code"

  Scenario: Client fails to set a weak PIN during PIN set
    Given I send a POST request to "PIN_SET_REQUEST_URL" with a "Weak Password" to to set a PIN for the user’s registered phone number
    When I send a POST request to "PIN_SET_CONFIRM_URL" with a "Weak Password" valid OTP to approve the pin set request
    And I send a POST request to "SET_PIN_URL" with a "Weak Password" and new password with new device UUID
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."

  Scenario: Client fails to set a new PIN due to entering an invalid token
    Given I send a POST request to "PIN_SET_REQUEST_URL" with a "New deviceUUID" to to set a PIN for the user’s registered phone number
    When I send a POST request to "PIN_SET_CONFIRM_URL" with a "New deviceUUID" valid OTP to approve the pin set request
    And I send a POST request to "SET_PIN_URL" with a "invalid token" and new password with new device UUIDs
    Then the response status code should be 403
    And the response should contain a field named "message" with the value "invalid request"

