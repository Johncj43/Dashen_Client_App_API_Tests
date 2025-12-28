Feature: Chat topup Ethiotelcom and safaricom API
  This feature tests the Chat Top-Up functionality in the Dashen Super App for both Ethiotelecom and Safaricom users.
  It covers various scenarios of sending airtime/top-up between users with different account statuses,
  including Active, Frozen, No Debit, No Credit, and Dormant accounts.

  The scenarios verify:
  - Successful chat top-up transactions between valid Super App users
  - Failure scenarios when the recipient is not a registered Super App user
  - Transactions initiated from accounts with restricted statuses (Frozen, No Debit, Dormant)
  - Proper calculation and retrieval of transaction fees
  - Verification of transactions and PINs before performing the top-up
  - Correct response messages and status codes for each scenario


    # ======================ETHIOTELECOM CHAT TOP-UP ======================
  Scenario:Successful chat top-up Ethiotelecom between Super App users with active accounts:FROM ACTIVE ACCOUNT
  Given the "sender_user_active" user logs in and obtains an access token
  When I send a POST request to "GET_FEES_URL" with a "ethio_topup_with_active_account" to get fees
  And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "ethio_chat_topup_data"
  And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
  And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "ethio_chat_topup" to perform chat topup with ethiotelecom
  Then the response status code should be 200
  And the response should contain a field named "message" with the value "Successfully sent"

  Scenario: Chat top-up fails when recipient is not a registered Super App user
    Given the "sender_user_active" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "chat_topup_super_user_to_non_super_user" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "ethio_chat_topup_to_non_super_user"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "ethio_chat_topup" to perform chat topup with ethiotelecom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver not found"

    Scenario:Chat top-up fails when initiated from a frozen account:FROZEN ACCOUNT
      Given the "sender_user_frozen" user logs in and obtains an access token
      When I send a POST request to "GET_FEES_URL" with a "frozen_account" to get fees
#      And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "frozen_account_data"
#      And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#      And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "frozen_account" to perform chat topup with ethiotelecom
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "Debit account is frozen"

     Scenario:Chat top-up fails when initiated from a no debit account:FROM NO_DEBIT ACCOUNT
       Given the "Sender_user_no_debit" user logs in and obtains an access token
       When I send a POST request to "GET_FEES_URL" with a "no_debit_account" to get fees
#       And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "no_debit_account_data"
#       And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#       And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "no_debit_account" to perform chat topup with ethiotelecom
       Then the response status code should be 400
       And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"


  Scenario: Successfully perform chat top-up when initiated from a no-credit account:FROM NO_CREDIT ACCOUNT
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_credit_account" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "no_credit_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_credit_account_correct_pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "no_credit_account" to perform chat topup with ethiotelecom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Successfully sent"


  Scenario:Chat top-up fails when initiated from a dormant account:FROM DORMANT ACCOUNT
    Given the "sender_user_dormant" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "dormant_account" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "dormant_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "dormant_account_correct_pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_CHAT_TOPUP_URL" request with "dormant_account" to perform chat topup with ethiotelecom










  #======================= SAFARICOM CHAT TOP_UP=================
  Scenario:Successful chat top-up Safaricom between Super App users with active accounts:ACTIVE ACCOUNT
    Given the "sender_user_active" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "safari_topup_with_active_account" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "safari_chat_topup_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "safari_chat_topup" to perform chat topup with safaricom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Successfully sent"


  Scenario: Chat top-up safaricom fails when recipient is not a registered Super App user
    Given the "sender_user_active" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "chat_topup_to_non_super_user" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "chat_topup_to_non_super_user"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "safari_chat_topup" to perform chat topup with safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver not found"

  Scenario: Chat top-up for Safaricom fails when initiated from a frozen account:FROM FROZEN ACCOUNT
    Given the "sender_user_frozen" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "frozen_accounts" to get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "frozen_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "frozen_account" to perform chat topup with safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

  Scenario: Chat top-up for Safaricom fails when initiated from a no debit account:FROM NO_DEBIT ACCOUNT
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_debit_accounts" to get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "no_debit_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "no_debit_account" to perform chat topup with safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"


  Scenario: Successfully perform Safaricom chat top-up when initiated from a no-credit account:FROM NO_CREDIT ACCOUNT
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_credit_accounts" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "no_credit_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_credit_account_correct_pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "no_credit_account" to perform chat topup with safaricom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Successfully sent"

  Scenario: Chat top-up for Safaricom fails when initiated from a no dormant account:FROM DORMANT ACCOUNT
    Given the "sender_user_dormant" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "dormant_accounts" to get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with "dormant_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "dormant_account_correct_pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_CHAT_TOPUP_URL" request with "dormant_account" to perform chat topup with safaricom




