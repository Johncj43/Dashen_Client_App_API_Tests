Feature: Query unpaid student payment for school fee
  As a registered user
  I want to query unpaid student school fee payments
  So that I can view the student’s pending payment history

  Scenario: Successfully query a student's unpaid payment
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "QUERY_STUDENT" for student lookup
    And device user "account_02" sends "QUERY_UNPAID_PAYMNET_DETAILS" for unpaid payment lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Student payment history fetched successfully"
