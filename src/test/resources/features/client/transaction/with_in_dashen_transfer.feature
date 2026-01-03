Feature: Fetch methods and transfer validation

  #
  # ACTIVE → ACTIVE
  #
  Scenario: Active account transfers successfully to active account
    Given the "account_03" user logs in and obtains an access token
    When device user "account_03" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "account_03" sends "HQ_URL" request for HQ
    And device user "account_03" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_03" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_03" sends "TRANSFER_WITH_IN_DASHEN_URL" for dashen user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"


  # ACTIVE → FROZEN
  #
  Scenario: Active account attempts transfer to frozen account
    Given the "account_03" user logs in and obtains an access token
    When device user "frozen_01" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"



    #
  # ACTIVE → DORMANT
  #
  Scenario: Active account transfers successfully to dormant account
    Given the "account_03" user logs in and obtains an access token
    When device user "dormant_01" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "account_03" sends "HQ_URL" request for HQ
    And device user "account_03" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_03" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_03" sends "TRANSFER_WITH_IN_DASHEN_URL" for dashen user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"


  # ACTIVE → NO-DEBIT

  Scenario: Active account transfers successfully to no-debit account
    Given the "account_03" user logs in and obtains an access token
    When device user "active_to_nodebit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "account_03" sends "HQ_URL" request for HQ
    And device user "account_03" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_03" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_03" sends "TRANSFER_WITH_IN_DASHEN_URL" for dashen user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"



     #
  # ACTIVE → NO CREDIT
  #
  Scenario: Active account attempts transfer to no-credit account
    Given the "account_03" user logs in and obtains an access token
    When device user "no-credit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"



  #
  # FROZEN → ACTIVE
  #
  Scenario: Frozen account attempts transfer to active account
    Given the "frozen" user logs in and obtains an access token
    When device user "account_011" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "frozen" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"


  #
  # FROZEN → FROZEN
  #
  Scenario: Frozen account attempts transfer to frozen account
    Given the "frozen" user logs in and obtains an access token
    When device user "frozen" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"



  #
  # FROZEN → DORMANT
  #
  Scenario: Frozen account attempts transfer to Dormant account
    Given the "frozen" user logs in and obtains an access token
    When device user "frozen_to_dormant" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "frozen" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"



     #
  # FROZEN → NO-CREDIT
  #
  Scenario: Frozen account attempts transfer to No-Credit account
    Given the "frozen" user logs in and obtains an access token
    When device user "frozen_to_no_credit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"



    #
  # FROZEN → NO-DEBIT
  #
  Scenario: Frozen account attempts transfer to No-Debit account
    Given the "frozen" user logs in and obtains an access token
    When device user "frozen_to_no_debit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "frozen" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"




  #
  # DORMANT → ACTIVE
  #
  Scenario: Dormant account transfers successfully to active account
    Given the "dormant" user logs in and obtains an access token
    When device user "account_012" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "dormant" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"


  #
  # DORMANT → DORMANT
  #
  Scenario: Dormant account transfers successfully to dormant account
    Given the "dormant" user logs in and obtains an access token
    When device user "dormant" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "dormant" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"



  #
  # DORMANT → FROZEN
  #
  Scenario: Dormant account transfers successfully to Frozen account
    Given the "dormant" user logs in and obtains an access token
    When device user "dormant_to_frozen" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"



  #
  # DORMANT → NO-CREDIT
  #
  Scenario: Dormant account transfers successfully to No-Credit account
    Given the "dormant" user logs in and obtains an access token
    When device user "dormant_to_no_credit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"



    #
  # DORMANT → NO-DEBIT
  #
  Scenario: Dormant account transfers successfully to No-debit account
    Given the "dormant" user logs in and obtains an access token
    When device user "dormant_to_no_debit" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "dormant" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"


  #
  # NO CREDIT → ACTIVE
  #
  Scenario: No-credit account attempts transfer to active account
    Given the "no-credit" user logs in and obtains an access token
    When device user "account_031" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "no-credit" sends "HQ_URL" request for HQ
    And device user "no-credit1" sends "FETCH_METHODS_URL" for fetch methods
    And device user "no-credit" sends "PIN_VERIFY" for the pin verify methods
    And device user "no-credit" sends "TRANSFER_WITH_IN_DASHEN_URL" for dashen user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"



  #
  # NO CREDIT → NO CREDIT
  #
  Scenario: No-credit account attempts transfer to no-credit
    Given the "no-credit" user logs in and obtains an access token
    When device user "no-credit1" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"



    #
  # NO CREDIT → NO DEBIT
  #
  Scenario: No-credit account attempts transfer to no-debit
    Given the "no-credit" user logs in and obtains an access token
    When device user "no-debit1" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "no-credit" sends "HQ_URL" request for HQ
    And device user "no-credit1" sends "FETCH_METHODS_URL" for fetch methods
    And device user "no-credit" sends "PIN_VERIFY" for the pin verify methods
    And device user "no-credit" sends "TRANSFER_WITH_IN_DASHEN_URL" for dashen user
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"




    #
  # NO CREDIT → FROZEN
  #

  Scenario: No-credit account attempts transfer to frozen
    Given the "no-credit" user logs in and obtains an access token
    When device user "no_credit_to_frozen" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"



  #
  # NO DEBIT → ACTIVE
  #
  Scenario: No-debit account attempts transfer
    Given the "no-debit" user logs in and obtains an access token
    When device user "no-debit_01" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "no-debit" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"


 #
  # NO DEBIT → NO-CREDIT
  #


  Scenario: No-debit account attempts transfer to no-credit
    Given the "no-debit" user logs in and obtains an access token
    When device user "no-debit_02" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"


    #
  # NO DEBIT → DORMANT
  #

  Scenario: No-debit account attempts transfer to Dormant
    Given the "no-debit" user logs in and obtains an access token
    When device user "no-debit_03" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "no-debit" sends "HQ_URL" request for HQ
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"



