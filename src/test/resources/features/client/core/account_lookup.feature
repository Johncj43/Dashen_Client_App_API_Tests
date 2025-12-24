Feature: Account Lookup API
  As an authenticated client application
  Users can perform an account lookup by providing a bank account number and bank code
  To validate account details and determine the current state (active, dormant, frozen, debit/credit permissions) before proceeding with financial transactions such as transfers or payments

  Scenario Outline: Successful validation of an account number via account lookup for different account states
    Given the "user_02" user logs in and obtains an access token
    When the client sends "ACCOUNT_LOOKUP_URL" request with <account_type> to perform an account lookup
    Then the response status code should be <status_code>
    And the response should contain a field named "message" with the value <message_expected>
    And the response should contain a field named "data.debit_allowed" with the value <debit_allowed>
    And the response should contain a field named "data.credit_allowed" with the value <credit_allowed>
    And the response should contain a field named "data.account_dormant" with the value <account_dormant>
    And the response should contain a field named "data.account_frozen" with the value <account_frozen>
    And the response should contain a field named "data.active_account" with the value <active_account>

    Examples:
      | account_type               | debit_allowed | credit_allowed | account_dormant | account_frozen | active_account|status_code |message_expected|
      | "Active Account"           | "true"        | "true"         | "false"         | "false"        | "true"         | 200       |"Account Enquiry successful" |
      | "Dormant Account"          | "true"        | "true"         | "true"          | "false"        | "true"         | 200       |"Account Enquiry successful for dormant account"|
      | "Frozen Account"           | "true"        | "true"         | "false"         | "true"         | "true"         | 400       |"Account is frozen"                |
      | "Frozen and Dormant Account" | "true"      | "true"         | "true"          | "true"         | "true"         | 400       |  "Account is frozen"     |

    #  Scenario: Successful validation of an account number via account lookup:ACTIVE ACCOUNT
#    Given the "user_02" user logs in and obtains an access token
#    When the client sends "ACCOUNT_LOOKUP_URL" request with "Active Account" to perform an account lookup
#    Then the response status code should be 200
#    And the response should contain a field named "message" with the value "Account lookup fetched successfully"
#    And the response should contain a field named "data.debit_allowed" with the value "true"
#    And the response should contain a field named "data.credit_allowed" with the value "true"
#    And the response should contain a field named "data.active_account" with the value "true"
#    And the response should contain a field named "data.account_dormant" with the value "false"
#    And the response should contain a field named "data.account_frozen" with the value "false"
#
#  Scenario Outline: Successful validation of an account number via account lookup:DORMANT ACCOUNT
#    Given the "user_02" user logs in and obtains an access token
#    When the client sends "ACCOUNT_LOOKUP_URL" request with "Dormant Account" to perform an account lookup
#    Then the response status code should be 200
#    And the response should contain a field named "message" with the value "Account lookup fetched successfully"
#    And the response should contain a field named "<field>" with the value "<value>"
##    And the response should contain a field named "data.credit_allowed" with the value "true"
##    And the response should contain a field named "data.account_dormant" with the value "true"
##    And the response should contain a field named "data.account_frozen" with the value "false"
##    And the response should contain a field named "data.active_account" with the value "true"
#  Examples:
#    |field|value|
#    |data.debit_allowed|true|
#    |data.credit_allowed|true|
#    |data.account_dormant|true|
#    |data.account_frozen |false|
#    |data.active_account |true |
#
#
#
#
#
#  Scenario: Successful validation of an account number via account lookup:FROZEN ACCOUNT
#    Given the "user_02" user logs in and obtains an access token
#    When the client sends "ACCOUNT_LOOKUP_URL" request with "Frozen Account" to perform an account lookup
#    Then the response status code should be 200
#    And the response should contain a field named "message" with the value "Account lookup fetched successfully"
#    And the response should contain a field named "data.debit_allowed" with the value "true"
#    And the response should contain a field named "data.credit_allowed" with the value "true"
#    And the response should contain a field named "data.account_dormant" with the value "false"
#    And the response should contain a field named "data.account_frozen" with the value "true"
#    And the response should contain a field named "data.active_account" with the value "true"
#
#
#  Scenario: Successful validation of an account number via account lookup:DORMANT AND FROZEN ACCOUNT
#    Given the "user_02" user logs in and obtains an access token
#    When the client sends "ACCOUNT_LOOKUP_URL" request with "Frozen and Dormant Account" to perform an account lookup
#    Then the response status code should be 200
#    And the response should contain a field named "message" with the value "Account lookup fetched successfully"
#    And the response should contain a field named "data.debit_allowed" with the value "true"
#    And the response should contain a field named "data.credit_allowed" with the value "true"
#    And the response should contain a field named "data.account_dormant" with the value "true"
#    And the response should contain a field named "data.account_frozen" with the value "true"
#    And the response should contain a field named "data.active_account" with the value "true"









