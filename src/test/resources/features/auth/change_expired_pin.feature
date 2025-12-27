Feature: Change Expired Pin API

  Scenario: User successfully changes an expired PIN
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
  When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
   And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
   And I send a POST request to "CHANGE_EXPIRED_PIN_URL" with a "valid header data" to change expired pin
   Then the response status code should be 200
   And the response should contain a field named "message" with the value "PIN changed successfully."

  Scenario: User fails to change the expired pin with weak password
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_EXPIRED_PIN_URL" with a "valid header data" to change expired pin with weak password
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."

  Scenario: User fails to change an expired PIN with weak password
    Given I send a POST request to "REGISTRATION_URL" with a "valid device uuid" and a valid phone number to register the user
    When I send a POST request to "OTP_VERIFICATION_FOR_REGISTRATION_URL" with a "valid device uuid" valid OTP to approve the signup request
    And I send a POST request to "SET_PIN_URL" with a "valid device uuid" and password
    And I send a POST request to "CHANGE_EXPIRED_PIN_URL" with a "valid header data" to change expired pin with empty field pin set
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Weak PIN used: please use a stronger combination."



