Feature: Query paid student payment for school fee
  As a registered user
  I want to query paid student school fee payments
  So that I can view the student’s payment history

  Scenario: Successfully query a student's paid payment
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "QUERY_PAID_PAYMENT_DETAILS" for paid payment lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Student payment history fetched successfully"

  Scenario: Query paid payment with an invalid student ID
    Given the "account_02" user logs in and obtains an access token
    When device user "account_03" sends "QUERY_PAID_PAYMENT_DETAILS" for paid payment lookup
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Not Found"

  Scenario: Query paid payment with an empty student ID
    Given the "account_02" user logs in and obtains an access token
    When device user "account_04" sends "QUERY_PAID_PAYMENT_DETAILS" for paid payment lookup
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid request payload - student_id is not allowed to be empty"
