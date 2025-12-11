Feature: Device Lookup API
  This feature verifies the functionality, error handling, and data validations of the /device/lookup endpoint.
  It covers scenarios such as successful device lookup with valid UUID, lookup with missing or invalid UUID, and handling of newly registered devices,
  ensuring the device lookup system behaves correctly under different conditions and returns accurate, consistent responses.

  Scenario: Successfully perform device lookup with valid data
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Valid header" to lookup user's device
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "device lookup returned successfully"
    And the response should contain a field named "data.user.devicestatus" with the value "LINKED"

  Scenario: The user prepares a device lookup request without a device UUID
    Given I send a request to "DEVICE_LOOKUP_URL" with a "empty device uuid" to lookup user's device
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


