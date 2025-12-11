Feature: Registration API
  The purpose of this feature is to validate the end-to-end behavior, data integrity, and error-handling mechanisms of the /registration endpoint.
  It includes test scenarios that verify successful user registration using valid input data, enforcement of mandatory field validations, handling of duplicate or already-registered users, and system responses to malformed or incomplete requests.
  This feature ensures that the Registration API adheres to business rules, maintains consistent response structures, prevents invalid data entry, and reliably supports the user onboarding workflow across various edge cases and operational conditions.

  Scenario: Successful register with valid data
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN set successfully."
    And the response should contain a field named "data.virtualAccount" with the value "true"

  Scenario: User fails to registration with incorrect format of pin
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and an incorrectly formatted PIN
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "pincode length must be 6 characters long"

  Scenario:Registration  fails with already registered device uuid
    When I send a POST request to "REGISTRATION_URL" with a "Already registered device uuid" and a valid phone number to register the client app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Member already exists: please use a different phone number, account number"

  Scenario: Registration fails when the device UUID is missing
    When I send a POST request to "REGISTRATION_URL" with a "missing device uuid" and a registered phone number to register the client app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Member already exists: please use a different phone number, account number"

  Scenario: Registration fails with missing token
    Given I send a POST request to "REGISTRATION_URL" with a "missing token for otp verification" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "missing token for otp verification" valid OTP to approve the signup request of user
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Registration fails when an invalid OTP is entered
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" using an "invalid OTP" to approve the user’s signup request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "incorrect Verification Code"







