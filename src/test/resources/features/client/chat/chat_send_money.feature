Feature:Chat send_money API

  @ACTIVE_TO_OTHER_DIFFERENT_ACCOUNT_STATUS_TRANSFER
  Scenario: Successful chat send money from active to active account
    Given the "sender_user_active" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_active_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "active_receiver_account_chat_send_money_01" to verify the transaction for chat send money
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "CHAT_SEND_MONEY_URL" request with "active_chat_send_money_01" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"

  Scenario: Fail chat send money from active to frozen account
    Given the "sender_user_active" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_frozen_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "frozen_receiver_account_chat_send_money_01" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "frozen_chat_send_money_01" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"

  Scenario: successful chat send money from active to no_debit account
    Given the "sender_user_active" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_no_debit_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_debit_receiver_account_chat_send_money_01" to verify the transaction for chat send money
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "CHAT_SEND_MONEY_URL" request with "no_debit_chat_send_money_01" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"

  Scenario: successful chat send money from active to dormant account
    Given the "sender_user_active" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_dormant_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "dormant_receiver_account_chat_send_money_01" to verify the transaction for chat send money
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends "CHAT_SEND_MONEY_URL" request with "dormant_chat_send_money_01" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"


  Scenario: Fail chat send money from active to no credit account
    Given the "sender_user_active" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_no_credit_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_credit_receiver_account_chat_send_money_01" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "no_credit_chat_send_money_01" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"


  @FROZEN_TO_OTHER_DIFFERENT_ACCOUNT_STATUS_TRANSFER
  Scenario: Successful chat send money from frozen to active account
    Given the "sender_user_frozen" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "frozen_to_active_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "active_receiver_account_chat_send_money_02" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "active_chat_send_money_02" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

#
#  Scenario: Fail chat send money from frozen to frozen account
#    Given the "sender_user_frozen" user logs in and obtains an access token
#    When the client sends a POST request to "GET_FEES_URL" using test data "active_to_frozen_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "frozen_receiver_account_chat_send_money_01" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "frozen_chat_send_money_01" to perform chat send money

  Scenario: Fail chat send money from frozen to no_debit account
    Given the "sender_user_frozen" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "frozen_to_no_debit_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_debit_receiver_account_chat_send_money_02" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "no_debit_chat_send_money_02" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

  Scenario: Fail chat send money from frozen to dormant account
    Given the "sender_user_frozen" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "frozen_to_dormant_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "dormant_receiver_account_chat_send_money_02" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "dormant_chat_send_money_02" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"


  Scenario: Fail chat send money from a to frozen to no credit account
    Given the "sender_user_frozen" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "frozen_to_no_credit_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_credit_receiver_account_chat_send_money_02" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "frozen_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "no_credit_chat_send_money_02" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

  @NO_DEBIT_TO_OTHER_DIFFERENT_ACCOUNT_STATUS_TRANSFER
  Scenario: Successful chat send money from no debit to active account
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_debit_to_active_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "active_receiver_account_chat_send_money_03" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "active_chat_send_money_03" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario: Fail chat send money from no debit to frozen account
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_debit_to_frozen_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "frozen_receiver_account_chat_send_money_03" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "frozen_chat_send_money_03" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

#  Scenario: Fail chat send money from no debit to no_debit account
#    Given the "sender_user_frozen" user logs in and obtains an access token
#    When the client sends a POST request to "GET_FEES_URL" using test data "no_debit_to_no_debit_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_debit_receiver_account_chat_send_money_03" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "no_debit_chat_send_money_03" to perform chat send money

  Scenario: Fail chat send money from no debit to dormant account
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_debit_to_dormant_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "dormant_receiver_account_chat_send_money_03" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "dormant_chat_send_money_03" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  Scenario: Fail chat send money from no debit to no credit account
    Given the "Sender_user_no_debit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_debit_to_no_credit_account_chat_send_money"
#    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_credit_receiver_account_chat_send_money_03" to verify the transaction for chat send money
#    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "no_debit_account_correct_pin" to verify the PIN for the transaction
#    And the client sends "CHAT_SEND_MONEY_URL" request with "no_credit_chat_send_money_03" to perform chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

  @NO_CREDIT_TO_OTHER_DIFFERENT_ACCOUNT_STATUS_TRANSFER
  Scenario: Successful chat send money from no credit to active account
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_credit_to_active_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "active_receiver_account_chat_send_money_04" to verify the transaction for chat send money
    And the client sends "CHAT_SEND_MONEY_URL" request with "active_chat_send_money_04" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"

  Scenario: Fail chat send money from no credit to frozen account
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_credit_to_frozen_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "frozen_receiver_account_chat_send_money_04" to verify the transaction for chat send money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"

  Scenario: successful chat send money from no credit to no_debit account
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_credit_to_no_debit_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "no_debit_receiver_account_chat_send_money_04" to verify the transaction for chat send money
    And the client sends "CHAT_SEND_MONEY_URL" request with "no_debit_chat_send_money_04" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"

  Scenario: Fail chat send money from no credit to dormant account
    Given the "sender_user_no_credit" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "no_credit_to_dormant_account_chat_send_money"
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "dormant_receiver_account_chat_send_money_04" to verify the transaction for chat send money
    And the client sends "CHAT_SEND_MONEY_URL" request with "dormant_chat_send_money_04" to perform chat send money
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "PAID"

  @DORMANT_TO_OTHER_DIFFERENT_ACCOUNT_STATUS_TRANSFER
  Scenario: Fail chat send money from dormant to active account
    Given the "sender_user_dormant" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "dormant_to_active_account_chat_send_money"
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

  Scenario: Fail chat send money from dormant to frozen account
    Given the "sender_user_dormant" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "dormant_to_frozen_account_chat_send_money"
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

  Scenario: Fail chat send money from dormant to no_debit account
    Given the "sender_user_dormant" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "dormant_to_no_debit_account_chat_send_money"
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

  Scenario: Fail chat send money from dormant to no credit account
    Given the "sender_user_dormant" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "dormant_to_no_credit_account_chat_send_money"
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"











































