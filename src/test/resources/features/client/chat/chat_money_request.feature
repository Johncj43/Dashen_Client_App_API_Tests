Feature: Chat Money Request API

 @ACTIVE_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO ACTIVE ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
# Step 2: Recipient accepts the pending money request
   Given the "active_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_01" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"


  Scenario: Successfully request money through chat and have the recipient reject the request: ACTVE TO ACTIVE ACCOUNT
        # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
     # Step 2: Recipient rejects the money request
    Given the "active_user_reject_money_request" user logs in and obtains an access token
    When the recipient sends a POST request to "CHAT_REJECT_REQUEST_URL" using test data "reject_money_request"
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully rejected money request"
    And the response should contain a field named "data.status" with the value "REJECTED"

    Scenario:Successfully cancel a pending money request sent through chat:FROM ACTIVE ACCOUNT
              # Step 1: Requester cancels the money request via chat
      Given the "active_user_request_money" user logs in and obtains an access token
      When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
      And the requester sends a POST request to "CHAT_CANCEL_REQUEST_URL" using test data "cancel_pending_money_request"
      Then the response status code should be 200
      And the response should contain a field named "message" with the value "Request cancelled successfully"
      And the response should contain a field named "data.status" with the value "CANCELLED"

#    ACTIVE TO FROZEN
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO FROZEN ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
    # Step 2: Recipient accepts the pending money request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_01" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

# ACTIVE TO DORMANT
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO DORMANT ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
        # Step 2: Recipient accepts the pending money request
    Given the "dormant_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_02" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

#    ACTIVE TO NO DEBIT
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO NO DEBIT ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
            # Step 2: Recipient accepts the pending money request
    Given the "no_debit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_03" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"
# ACTIVE TO NO CREDIT
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO NO CREDIT ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                # Step 2: Recipient accepts the pending money request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_04" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_04" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_04" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_04" to perform chat money request


    @FROZEN_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#      FROZEN TO ACTIVE
    Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO ACTIVE ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
      Given the "frozen_user_request_money" user logs in and obtains an access token
      When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O5" to request money via chat
      Then the response status code should be 201
      And the response should contain a field named "message" with the value "Request sent successfully"
    # Step 2: Active recipient attempts to accept the request
      Given the "active_user_accept_money_request" user logs in and obtains an access token
      When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
      And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_05" to verify the transaction for chat money request
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "Credit Account is frozen"


#    FROZEN TO FROZEN OWN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO FROZEN ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O5" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
    # Step 2: Frozen recipient attempts to accept the request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_06" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"

#FROZEN TO DORMANT
  Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO DORMANT ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O5" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
        # Step 2: Dormant recipient attempts to accept the request
    Given the "dormant_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_07" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"


#    FROZEN TO NO DEBIT
  Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO NO DEBIT ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O5" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
        # Step 2: No debit recipient attempts to accept the request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_06" to accept the money request

















