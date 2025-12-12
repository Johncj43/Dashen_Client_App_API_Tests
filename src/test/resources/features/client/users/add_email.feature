Feature: Add email API

  Scenario: Client fails to add an email if it is already added
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "ADD_EMAIL_URL" with "user_02" to add an email to the app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "email set. please visit your nearest branch to change your email"

    Scenario: User fails to add an email with empty field
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "ADD_EMAIL_URL" with "user_10" to add an email to the app
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "invalid request"

      Scenario: User fails to add an email with an invalid access token
        Given the "user_02" user logs in and obtains an access token
        When I send a POST request to "ADD_EMAIL_URL" with "user_04" to add an email with an invalid access token
        Then the response status code should be 401
        And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"

  Scenario: User fails to add an email with an unregistered deviceUUID
    Given the "user_02" user logs in and obtains an access token
        When I send a POST request to "ADD_EMAIL_URL" with "user_03" to add an email to the app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "invalid request"








