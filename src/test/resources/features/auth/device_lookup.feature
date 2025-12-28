Feature: Device Lookup API
  This feature validates the Device Lookup functionality of the Dashen Client
  Application. It ensures that the /device/lookup endpoint correctly retrieves
  device information, enforces request validations, and handles different device
  states accurately.

  Scenario: Successfully perform device lookup with valid data
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a request to "DEVICE_LOOKUP_URL" with a "Valid header" to lookup user's deviceUUID
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "device lookup returned successfully"
    And the response should contain a field named "data.user.devicestatus" with the value "LINKED"

  Scenario: The user prepares a device lookup request without a device UUID
    Given I send a request to "DEVICE_LOOKUP_URL" with a "empty device uuid" to lookup device user
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "Invalid request"

  Scenario:The user prepares a device lookup request without an installation date
    Given I send a request to "DEVICE_LOOKUP_URL" with a "missing installation date" to lookup user's device
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "device lookup returned successfully"
    And the response should contain a field named "data.user.devicestatus" with the value ""

  Scenario: perform device lookup with a new device UUID
    Given I send a request to "DEVICE_LOOKUP_URL" with a "New device uuid" to lookup user's device
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "device lookup returned successfully"
    And the response should contain a field named "data.user.devicestatus" with the value ""


