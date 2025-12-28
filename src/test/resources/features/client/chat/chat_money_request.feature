Feature: Chat Money Request API
  This feature validates the Chat Money Request functionality in the Dashen Client App.
  It ensures that customers can request money via chat and that the system correctly
  handles acceptance, rejection, and cancellation of requests across different
  account statuses.
  The scenarios cover money request flows between Active, Frozen, Dormant,
  No Debit, and No Credit accounts, verifying business rules, transaction validation,
  PIN verification, and appropriate error handling based on account restrictions.

 @ACTIVE_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#   ACTIVE TO ACTIVE M0NEY REQUEST
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

#    ACTIVE TO FROZEN MONEY REQUEST
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

# ACTIVE TO DORMANT MONEY REQUEST
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

#    ACTIVE TO NO DEBIT MONEY REQUEST
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
# ACTIVE TO NO CREDIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO NO CREDIT ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_04" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                # Step 2: Recipient accepts the pending money request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_04" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_04" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_04" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_04" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"


    @FROZEN_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#      FROZEN TO ACTIVE MONEY REQUEST
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
      And the response should contain a field named "message" with the value "Debit account is frozen"


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

#FROZEN TO DORMANT MONEY REQUEST
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


#    FROZEN TO NO DEBIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO NO DEBIT ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O5" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
        # Step 2: No debit recipient attempts to accept the request
    Given the "no_debit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_08" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

#    FROZEN TO NO CREDIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:FROZEN TO NO CREDIT ACCOUNT
    # Step 1: Frozen requester sends a money request via chat
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_O6" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
        # Step 2: No Credit recipient attempts to accept the request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_09" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_07" to verify the transaction for chat money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is frozen"

@DORMANT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#  DORMANT TO ACTIVE MONEY REQUEST
Scenario: Successfully request money through chat and have the recipient accept the pending request:DORMANT TO ACTIVE ACCOUNT
    # Step 1: dormant requester sends a money request via chat
  Given the "dormant_user_request_money" user logs in and obtains an access token
  When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_07" to request money via chat
  Then the response status code should be 201
  And the response should contain a field named "message" with the value "Request sent successfully"
          # Step 2: Active recipient attempts to accept the request
  Given the "active_user_accept_money_request" user logs in and obtains an access token
  When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
  And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_08" to verify the transaction for chat money request
  And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin" to verify the PIN for the transaction
  And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_06" to perform chat money request
  Then the response status code should be 201
  And the response should contain a field named "message" with the value "Successfully sent"
  And the response should contain a field named "data.payment_status" with the value "paid in full"

#  DORMANT TO FROZEN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:DORMANT TO FROZEN ACCOUNT
    # Step 1: dormant requester sends a money request via chat
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_08" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
              # Step 2: frozen recipient attempts to accept the request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_06" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

#    DORMANT TO NO DEBIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:DORMANT TO NO DEBIT ACCOUNT
    # Step 1: dormant requester sends a money request via chat
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_09" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                # Step 2: no debit recipient attempts to accept the request
    Given the "no_debit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_08" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

#DORMANT TO DORMANT OWN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:DORMANT TO DORMANT ACCOUNT
    # Step 1: dormant requester sends a money request via chat
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_10" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                    # Step 2: dormant recipient attempts to accept the request
    Given the "dormant_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_07" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

#    DORMANT TO NO CREDIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:DORMANT TO NO CREDIT ACCOUNT
    # Step 1: dormant requester sends a money request via chat
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_11" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
      # Step 2: no credit recipient attempts to accept the request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_09" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_09" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_04" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_07" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"

  @NO_DEBIT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#    NO DEBIT TO ACTIVE MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO DEBIT TO ACTIVE ACCOUNT
    # Step 1: no debit requester sends a money request via chat
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_12" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
          # Step 2: Active recipient attempts to accept the request
    Given the "active_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_10" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_05" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_08" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"

    #    NO DEBIT TO FROZEN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO DEBIT TO FROZEN ACCOUNT
    # Step 1: no debit requester sends a money request via chat
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_13" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
              # Step 2: Frozen recipient attempts to accept the request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_06" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"

        #    NO DEBIT TO NO DEBIT OWN  MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO DEBIT TO NO DEBIT ACCOUNT
    # Step 1: no debit requester sends a money request via chat
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_14" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                  # Step 2: No debit recipient attempts to accept the request
    Given the "no_debit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_08" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

            #    NO DEBIT TO DORMANT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO DEBIT TO DORMANT ACCOUNT
    # Step 1: no debit requester sends a money request via chat
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_15" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                      # Step 2: dormant recipient attempts to accept the request
    Given the "dormant_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_07" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

                #    NO DEBIT TO NO CREDIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO DEBIT TO NO CREDIT ACCOUNT
    # Step 1: no debit requester sends a money request via chat
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_16" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                          # Step 2: no credit recipient attempts to accept the request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_09" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_11" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_06" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data_07" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"

  @NO_CREDIT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
                  #    NO CREDIT TO ACTIVE MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO CREDIT TO ACTIVE ACCOUNT
    # Step 1: no credit requester sends a money request via chat
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_17" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                              # Step 2: Active recipient attempts to accept the request
    Given the "active_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_12" to verify the transaction for chat money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit Account is not allowed to credit"

                      #    NO CREDIT TO FROZEN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO CREDIT TO FROZEN ACCOUNT
    # Step 1: no credit requester sends a money request via chat
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_17" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                              # Step 2: Frozen recipient attempts to accept the request
    Given the "frozen_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_06" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is frozen"


                      #    NO CREDIT TO DORMANT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO CREDIT TO DORMANT ACCOUNT
    # Step 1: no credit requester sends a money request via chat
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_17" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                              # Step 2: dormant recipient attempts to accept the request
    Given the "dormant_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_07" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit account is dormant"

                          #    NO CREDIT TO NO DEBIT MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO CREDIT TO NO DEBIT ACCOUNT
    # Step 1: no credit requester sends a money request via chat
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_18" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                              # Step 2: no debit recipient attempts to accept the request
    Given the "no_debit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_08" to accept the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit Account is not allowed to debit"

                              #    NO CREDIT TO NO CREDIT OWN MONEY REQUEST
  Scenario: Successfully request money through chat and have the recipient accept the pending request:NO CREDIT TO NO CREDIT ACCOUNT
    # Step 1: no credit requester sends a money request via chat
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request_19" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
                              # Step 2: Frozen recipient attempts to accept the request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request_09" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction_14" to verify the transaction for chat money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Credit account and debit account can not be the same"

























