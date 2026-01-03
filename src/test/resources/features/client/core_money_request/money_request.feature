Feature: Core Money request API

  This feature validates the Dashen Client App Money Request APIs,
  ensuring that money requests between different types of accounts
  comply with business rules, account restrictions, and system workflows.

  The scenarios cover the following account statuses:
  - ACTIVE
  - FROZEN
  - DORMANT
  - NO-DEBIT
  - NO-CREDIT
  The feature ensures that:
  - Requesters can successfully send money requests to eligible accounts.
  - Recipients can accept or reject pending money requests according to account rules.
  - Requesters can cancel pending money requests when allowed.
  - Requests are blocked for accounts with restrictions (frozen, dormant, no-debit, or no-credit).
  - Proper HTTP response codes and messages are returned for each scenario.
  - Fees, PIN verification, and transaction validations are performed correctly.

  This comprehensive test suite guarantees reliability, compliance, and correct
  behavior of money request operations in the Dashen Client App.

  @ACTIVE_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST_TRANSFER
#   ACTIVE TO ACTIVE M0NEY REQUEST
  Scenario: Successful money request from an active account to another active account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_01" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_01" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    And the response should contain a field named "data.status" with the value "PENDING"

#     Step 2: Recipient accepts the pending money request
    Given the "active_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_01" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_01" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_07" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_01" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"

  Scenario: Successfully cancel a pending money request by the requester
  # Step 1: The requester cancels the pending money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_01" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_01" to request money
    And the client sends a DELETE request to "CANCEL_REQUEST_MONEY_URL" with payload "cancel_money_request_data_01" to cancel the pending action
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request cancelled"


  Scenario: Successfully request money and have the recipient reject the request (Active to Active Account)
        # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_01" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_01" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    And the response should contain a field named "data.status" with the value "PENDING"
         # Step 2: Recipient rejects the money request
    Given the "active_user_reject_money_request" user logs in and obtains an access token
    When the recipient sends a DELETE request to "REJECT_REQUEST_MONEY_URL" with "reject_request_money_01" to reject the request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request rejected"

  Scenario: Successfully fetch beneficiaries of a money request
    Given the "active_user_request_money" user logs in and obtains an access token
    And the client sends a GET request to "FETCH_BENEFICIARIES_URL" with test data "fetch_beneficiaries_01" to fetch the list of beneficiaries
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Requests retrieved"

  Scenario: Successfully fetch a sent money request
    Given the "active_user_request_money" user logs in and obtains an access token
    And the client sends a GET request to "FETCH_SENT_REQUESTS_URL" with test data "requests_01" to get the list of sent requests
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Requests retrieved"

  Scenario: Successfully fetch a received money request
    Given the "active_user_request_money" user logs in and obtains an access token
    And the client sends a GET request to "FETCH_RECEIVED_REQUESTS_URL" with test data "requests_01" to get the list of received requests
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Requests retrieved"

#    ACTIVE TO FROZEN MONEY REQUEST
  Scenario: Successful money request from an active account to frozen account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_02" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is frozen"

#ACTIVE TO DORMANT MONEY REQUEST
  Scenario: Successful money request from an active account to Dormant account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_03" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is dormant"

    #ACTIVE TO NO DEBIT MONEY REQUEST
  Scenario: Successful money request from an active account to no debit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_04" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"

# ACTIVE TO NO CREDIT MONEY REQUEST
  Scenario: Successful money request from an active account to no credit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "active_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_05" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_05" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    And the response should contain a field named "data.status" with the value "PENDING"
#  Step 2: Recipient accepts the pending money request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_05" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_02" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_08" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_02" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"

    @FROZEN_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST
    # FROZEN TO ACTIVE MONEY REQUEST
    Scenario: Successful money request from an frozen account to active account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
      Given the "frozen_user_request_money" user logs in and obtains an access token
      When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_06" to lookup the recipient account for the money request
      And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_06" to request money
      Then the response status code should be 400
      And the response should contain a field named "message" with the value "Request sender account is frozen"

#      FROZEN TO FROZEN MONEY REQUEST
  Scenario: Successful money request from an frozen account to frozen account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_07" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is frozen"

#    FROZEN TO DORMANT MONEY REQUEST
  Scenario: Successful money request from an frozen account to dormant account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_08" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is dormant"

#    FROZEN TO NO DEBIT MONEY REQUEST
  Scenario: Successful money request from an frozen account to no debit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_09" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"

#    FROZEN TO NO CREDIT MONEY REQUEST
  Scenario: Successful money request from an frozen account to no credit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "frozen_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_10" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"

  @DORMANT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST

#    DORMANT TO ACTIVE MONEY REQUEST
  Scenario: Successful money request from an dormant account to active account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_11" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_08" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    #     Step 2: Recipient accepts the pending money request
    Given the "active_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_01" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_05" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_07" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_01" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"

#    DORMANT TO FROZEN MONEY REQUEST
  Scenario: Successful money request from a dormant account to frozen account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_12" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is frozen"

#DORMANT TO NO DEBIT MONEY REQUEST
  Scenario: Successful money request from an dormant account to no debit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_13" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"

#    DORMANT TO NO CREDIT MONEY REQUEST
  Scenario: Successful money request from an dormant account to no credit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "dormant_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_14" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_08" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    #  Step 2: Recipient accepts the pending money request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_05" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_06" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_08" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_02" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"



  @NO_DEBIT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST
#NO DEBIT TO ACTIVE MONEY REQUEST
  Scenario: Successful money request from an no debit account to active account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_15" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_09" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    And the response should contain a field named "data.status" with the value "PENDING"

#     Step 2: Recipient accepts the pending money request
    Given the "active_user_accept_money_request_01" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_06" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_03" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct pin" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_03" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"

#    NO DEBIT TO FROZEN MONEY REQUEST
  Scenario: Successful money request from an no debit account to frozen account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_16" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is frozen"

#    NO DEBIT TO DORMANT MONEY REQUEST
  Scenario: Successful money request from an no debit account to dormant account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_17" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is dormant"

#NO DEBIT TO NO DEBIT MONEY REQUEST
  Scenario: Successful money request from an no debit account to no debit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_18" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"


#    NO DEBIT TO NO CREDIT MONEY REQUEST
  Scenario: Successful money request from an no debit account to no credit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_debit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_19" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_09" to request money
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request successfully sent"
    And the response should contain a field named "data.status" with the value "PENDING"

#     Step 2: Recipient accepts the pending money request
    Given the "no_credit_user_accept_money_request" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using the payload "accept_money_request_07" to retrieve fees for accepting the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using the payload "accept_money_request_transaction_04" to verify the transaction
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin_08" to verify the PIN for the transaction
    And the client sends a POST request to "MONEY_REQUEST_ACCEPT_URL" with payload "accept_money_request_data_04" to accept the money request
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Request accepted successfully"
    And the response should contain a field named "data.isFullyPaid" with the value "PAID IN FULL"

  @NO_CREDIT_TO_OTHER_ACCOUNT_STATUS_MONEY_REQUEST

#    NO CREDIT TO ACTIVE MONEY TRANSFER
  Scenario: Successful money request from an no credit account to active account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_20" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_10" to request money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Request sender account is not allowed to credit"

#    NO CREDIT  TO FROZEN MONEY REQUEST
  Scenario: Successful money request from an no credit account to frozen account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_21" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is frozen"

#    NO CREDIT TO NO DEBIT MONEY REQUEST
  Scenario: Successful money request from an no credit account to no debit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_22" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is not allowed to debit"

#    NO CREDIT TO DORMANT MONEY REQUEST
  Scenario: Successful money request from an no credit account to dormant account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_23" to lookup the recipient account for the money request
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Receiver account is dormant"

#    NO CREDIT TO NO CREDIT MONEY REQUEST
  Scenario: Successful money request from an no credit account to no credit account, with recipient accepting the pending request
    # Step 1: Requester sends the money request
    Given the "no_credit_user_request_money" user logs in and obtains an access token
    When the client sends a POST request to "ACCOUNT_LOOKUP_URL" using the payload "account_24" to lookup the recipient account for the money request
    And the client sends a POST request to "MONEY_REQUEST_URL" using the payload "money_request_10" to request money
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Debit and credit account can not be the same"




































