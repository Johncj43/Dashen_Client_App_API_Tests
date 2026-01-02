Feature: HQ API
  As a registered user
  I want to perform HQ operations
  So that I can verify account details, mini statements, and transaction information

  Scenario: Successfully perform HQ operation for a valid ACTIVE account
    Given the "account_02" user logs in and obtains an access token
    When device user "account_030" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "account_02" sends "HQ_URL" request for HQ
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Service fetched sucessfully.."


  Scenario:Perform HQ when debit and credit accounts are the same
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "account_02" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit account and debit account can not be the same"


  Scenario:Perform HQ with frozen account
    Given the "frozen" user logs in and obtains an access token
    When device user "frozen_hq" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "frozen" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"


  Scenario:Perform HQ with dormant account
    Given the "dormant" user logs in and obtains an access token
    When device user "dormant_hq" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "dormant" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

  Scenario:Perform HQ with no-debit account
    Given the "no-debit" user logs in and obtains an access token
    When device user "no-debit_hq" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "no-debit" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario:Perform HQ with no-credit account
    Given the "no-credit" user logs in and obtains an access token
    When device user "no-credit_hq" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    When device user "no-credit" sends "HQ_URL" request for HQ
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Service fetched sucessfully.."

