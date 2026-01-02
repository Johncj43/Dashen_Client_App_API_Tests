Feature: Query paid student payment for school fee
  As a registered user
  I want to query paid student school fee payments
  So that I can view the student’s payment history

  Scenario: Successfully query a student's lookup
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "QUERY_STUDENT" for student lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Student information fetched successfully"

