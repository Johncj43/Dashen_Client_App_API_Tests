Feature: Change PIN API

  Scenario: User successfully changes PIN
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Registered device UUID and old PIN" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Registered device UUID and old PIN" to access client app
    And I send a POST request to "CHANGE_PIN_URL" with a "Registered device UUID and old PIN" payload
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN changed successfully."

 Scenario:User fails to change PIN with a weak PIN
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Registered device UUIDs" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Registered device UUIDs" to access client app
    And I send a POST request to "CHANGE_PIN_URL" with a "Weak PIN" payloads
    Then the response status code should be 400
   And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."

  Scenario: User fails to change PIN with an incorrect old PIN
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Registered device UUIDs" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Registered device UUIDs" to access client app
    And I send a POST request to "CHANGE_PIN_URL" with an "incorrect old PIN" payload
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Old PIN is incorrect. Please try again."

  Scenario: User fails to change PIN with a missing token
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Registered device UUIDs" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Registered device UUIDs" to access client app
    And I send a POST request to "CHANGE_PIN_URL" with  "missing token" payload
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: Client fails to change PIN without a device UUID
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Registered device UUIDs" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Registered device UUIDs" to access client app
    And I send a POST request to "CHANGE_PIN_URL" with  a "none device UUID" to change PIN
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

















