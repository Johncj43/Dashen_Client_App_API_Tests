Feature: Change PIN API
  This feature verifies the Change PIN functionality in the Dashen Client Application.
  It ensures that users can successfully update their PIN and that the system enforces
  validation rules, authentication requirements, and security constraints.

  Scenario: User successfully changes new PIN
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_PIN_URL" with a "Registered device UUID and old PIN" payload
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN changed successfully."

 Scenario:User fails to change PIN with a weak PIN
   Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
   When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
   And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_PIN_URL" with a "Weak PIN" to the strength of new password set
    Then the response status code should be 400
   And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."

  Scenario: User fails to change the old PIN with an incorrect old PIN
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_PIN_URL" with an "incorrect old PIN" payload
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Old PIN is incorrect. Please try again."

  Scenario: User fails to change PIN with a missing token
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_PIN_URL" with  "missing token" payload
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to change PIN without a device UUID
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_PIN_URL" with  a "none device UUID" to change PIN
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

















