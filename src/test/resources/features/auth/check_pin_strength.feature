Feature: Check PIN Strength

  Scenario: Client check pin strength
    Given I send a request to "DEVICE_LOOKUP_URL" with a "valid user" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "valid user" to access client app
    And I send a POST request to "CHECK_PIN_STRENGTH" using a "Strong PIN" to verify the PIN strength
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "PIN validated successfully."

  Scenario: Client fails PIN strength validation due to weak PIN
    Given I send a request to "DEVICE_LOOKUP_URL" with a "valid user" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "valid user" to access client app
    And I send a POST request to "CHECK_PIN_STRENGTH" using a "Weak PIN" to verify the PIN strength
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."

  Scenario: Client fails PIN strength validation using an invalid PIN format
    Given I send a request to "DEVICE_LOOKUP_URL" with a "valid user" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "valid user" to access client app
    And I send a POST request to "CHECK_PIN_STRENGTH" using a "invalid PIN format" to verify the PIN strength
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "newpin must be a string"

  Scenario: Client fails PIN strength validation using a PIN below the minimum requirement
    Given I send a request to "DEVICE_LOOKUP_URL" with a "valid user" to lookup user's device
    When I send a POST request to "PIN_LOGIN_URL" with a "valid user" to access client app
    And I send a POST request to "CHECK_PIN_STRENGTH" using a "PIN below the required length" to verify the PIN strength
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "newpin length must be 6 characters long"











