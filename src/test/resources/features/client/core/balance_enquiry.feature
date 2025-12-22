Feature: Balance Enquiry API
As an authenticated client application
I want to retrieve the current balance and account details of a linked bank account
So that I can display accurate wallet or account balance information to the user and enable informed financial decisions

  Scenario Outline: Successful balance enquiry for different account states

    Given the "user_02" user logs in and obtains an access token
    When the client sends "BALANCE_ENQUIRY_URL" request with <request_payload> to perform a balance enquiry
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Balance retrieved successfully"
    And the response should contain a field named "data.debit_allowed" with the value <debit_allowed>
    And the response should contain a field named "data.credit_allowed" with the value <credit_allowed>
    And the response should contain a field named "data.account_dormant" with the value <account_dormant>
    And the response should contain a field named "data.account_frozen" with the value <account_frozen>
    Examples:
      | request_payload                  | debit_allowed | credit_allowed | account_dormant | account_frozen |
      | "Balance_enquiry_active_account" | "true"        | "true"         | "false"         | "false"        |
#
#  Scenario: Successful balance enquiry for an active account
#    Given the "user_02" user logs in and obtains an access token
#    When the client sends "BALANCE_ENQUIRY_URL" request with "Balance_enquiry_active_account" to perform a balance enquiry
#    Then the response status code should be 200
#    And the response should contain a field named "message" with the value "Balance retrieved successfully"
#    And the response should contain a field named "data.debit_allowed" with the value "true"
#    And the response should contain a field named "data.credit_allowed" with the value "true"
#    And the response should contain a field named "data.account_dormant" with the value "false"
#    And the response should contain a field named "data.account_frozen" with the value "false"

  Scenario: User cannot perform balance enquiry on a frozen account
    Given the "user_02" user logs in and obtains an access token
    When the client sends "BALANCE_ENQUIRY_URL" request with "Balance_enquiry_frozen_account" to perform a balance enquiry
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "No linked account found for this account number."

  Scenario: User cannot perform balance enquiry on a dormant account
    Given the "user_02" user logs in and obtains an access token
    When the client sends "BALANCE_ENQUIRY_URL" request with "Balance_enquiry_dormant_account" to perform a balance enquiry
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "No linked account found for this account number."







