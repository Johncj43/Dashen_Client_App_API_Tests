Feature: Transfer to Own Account

  As a logged-in Dashen user
  I want to transfer money between my own accounts
  So that I can easily manage my balances


  Scenario: Successfully transfer to own account
    Given the "account_02" user logs in and obtains an access token
    When device user "own_account" sends "ACCOUNT_LOOKUP_URL" for account lookup to transfer own account
    And device user "account_02" sends "HQ_URL" request for HQ
    And device user "account_02" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_02" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_02" sends "TRANSFER_TO_OWN_URL" for dashen user own
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"

  Scenario: Successfully transfer to another account number
    Given the "account_02" user logs in and obtains an access token
    When device user "another_account" sends "ACCOUNT_LOOKUP_URL" for account lookup to transfer own account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "No linked account found for this account number"


  Scenario: Successfully transfer to the same  account number
    Given the "account_02" user logs in and obtains an access token
    When device user "the_same_account" sends "ACCOUNT_LOOKUP_URL" for account lookup to transfer own account
    And device user "account_02" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_02" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_02" sends "TRANSFER_TO_OWN_URL" for dashen user own
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "No linked account found for this account number"