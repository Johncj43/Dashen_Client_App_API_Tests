Feature: Submit user feedback
  As a registered user
  I want to submit my feedback about the Dashen Super app
  So that the service quality can be improved

  Scenario: Successfully submit user feedback
    When device user "account_02" sends "SEND_FEEDBACK" for feedback
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Feedback sent successfully"

  Scenario: Successfully submit feedback with survey responses
    When device user "account_02" sends "SEND_FEEDBACK" for feedback
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Feedback sent successfully"

  Scenario: Fail to submit feedback without username
    When device user "account_07" sends "SEND_FEEDBACK" for feedback
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Username is required"

  Scenario: Fail to submit feedback without feedback message
    When device user "account_06" sends "SEND_FEEDBACK" for feedback
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Feedback message is required"

  Scenario: Fail to submit feedback with invalid rating
    When device user "account_04" sends "SEND_FEEDBACK" for feedback
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Rating must be a number between 1 and 5"
