Feature: Login API

  Scenario: User successfully logs in with valid credentials
    Given I send a request to "DEVICE_LOOKUP_URL" with a "registered device uuid and PIN" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "registered device uuid and PIN" to access client app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Login successful."

  Scenario: User fails to log in with an unregistered device UUID
    Given I send a request to "DEVICE_LOOKUP_URL" with a "unregistered device UUID" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "unregistered device UUID" to access client app
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

  Scenario: User fails to log in with an incorrect PIN
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Wrong PIN" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Wrong PIN" to access client app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Incorrect PIN. 4 tries left."

  Scenario: Login fails when the PIN is not provided
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Empty PIN" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Empty PIN" to access client app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "pincode is not allowed to be empty"

  Scenario: Login fails when the device UUID is missing
    Given I send a request to "DEVICE_LOOKUP_URL" with a "Missing device UUID" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "Missing device UUID" to access client app
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"







