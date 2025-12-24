Feature: EthioTelecom and Safaricom Airtime Top-up API
  As an authenticated client application
  Users can purchase airtime top-ups for Ethio Telecom and Safaricom mobile numbers
  To enable convenient recharging of prepaid balances for themselves or beneficiaries directly within the application


  # ======================ETHIOTELECOM TOP-UP ======================

   @topup @Ethiotelecom
  Scenario: Successful Ethiotelecom airtime Topup:  FROM ACTIVE ACCOUNT
    Given the "sender_user_active" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "active_account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_TOPUP_URL" request with "topup_self_01" to perform topup with ethiotelecom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Recharge ethio-telecom topup successful"
    And the response should contain a field named "data.status" with the value "PAID"

  Scenario: Airtime top-up request is rejected when initiated from a frozen account: FROM FROZEN ACCOUNT
    Given the "sender_user_frozen" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "frozen_account" to create get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "frozen_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "ETHIOTELECOM_TOPUP_URL" request with "frozen_account" to perform topup with ethiotelecom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

  Scenario: Airtime top-up request is rejected when the account does not allow debits: FROM NO DEBIT ACCOUNT
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_debit_account" to create get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "no_debit_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "ETHIOTELECOM_TOPUP_URL" request with "no_debit_account" to perform topup with ethiotelecom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario: Successful Ethiotelecom airtime Topup: NO CREDIT ACCOUNT
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_credit_account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "no_credit_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_credit_account_correct_pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_TOPUP_URL" request with "no_credit_account" to perform topup with ethiotelecom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Recharge ethio-telecom topup successful"
    And the response should contain a field named "data.status" with the value "PAID"

  Scenario: Airtime top-up request is rejected when initiated from a dormant account: DORMANT ACCOUNT
    Given the "sender_user_dormant" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "dormant_account" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "dormant_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "dormant_account_correct_pin" to verify the PIN for the transaction
    And the client sends "ETHIOTELECOM_TOPUP_URL" request with "dormant_account" to perform topup with ethiotelecom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Account is frozen or dormant"






  #=================SAFARICOM_TOPUP=====================

  @topup @safaricom
  Scenario: Successful Safaricom airtime top-up: ACTIVE ACCOUNT
    Given the "sender_user_active" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "Saraficom_phone_number" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "valid data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_TOPUP_URL" request with "topup_safaricom" to perform topup with Safaricom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Recharge Safaricom topup successful"
    And the response should contain a field named "data.status" with the value "PAID"


  Scenario: Airtime top-up for safaricom request is rejected when initiated from a frozen account: FROM FROZEN ACCOUNT
    Given the "sender_user_frozen" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "frozen_accounts" to create get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "frozen_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "SAFARICOM_TOPUP_URL" request with "frozen_account" to perform topup with Safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario: Airtime top-up for safaricom request is rejected when the account does not allow debits: FROM NO DEBIT ACCOUNT
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_debit_accounts" to create get fees
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "no_debit_account_data"
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "SAFARICOM_TOPUP_URL" request with "no_debit_account" to perform topup with Safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario: Successful safaricom airtime Topup: NO CREDIT ACCOUNT
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "no_credit_accounts" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "no_credit_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_credit_account_correct_pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_TOPUP_URL" request with "no_credit_account" to perform topup with Safaricom
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Recharge Safaricom topup successful"
    And the response should contain a field named "data.status" with the value "PAID"

  Scenario: Safaricom Airtime top-up request is rejected when initiated from a dormant account: DORMANT ACCOUNT
    Given the "sender_user_dormant" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "dormant_accounts" to create get fees
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" to verify the transaction with a "dormant_account_data"
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "dormant_account_correct_pin" to verify the PIN for the transaction
    And the client sends "SAFARICOM_TOPUP_URL" request with "dormant_account" to perform topup with Safaricom
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Account is frozen or dormant"











