Feature: Chat Money Request API

  Scenario: Successfully request money through chat and have the recipient accept the pending request:ACTIVE TO ACTIVE ACCOUNT
    # Step 1: Requester sends the money request via chat
    Given the "user_requester" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
# Step 2: Recipient accepts the pending money request
   Given the "user_sender" user logs in and obtains an access token
    When the client sends a POST request to "GET_FEES_URL" using test data "accept_valid_money_request" to accept the money request
    And the client sends a POST request to "TRANSACTION_VERIFY_METHOD_URL" using test data "valid_money_request_transaction" to verify the transaction for chat money request
    And the client sends a POST request to "PIN_VERIFY_METHOD_URL" with "correct_pin" to verify the PIN for the transaction
    And the client sends "CHAT_MONEY_REQUEST_ACCEPT_URL" request with "valid_chat_money_request_data" to perform chat money request
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully sent"
    And the response should contain a field named "data.payment_status" with the value "paid in full"


  Scenario: Successfully request money through chat and have the recipient reject the request: ACTVE TO ACTIVE ACCOUNT
        # Step 1: Requester sends the money request via chat
    Given the "user_requester" user logs in and obtains an access token
    When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Request sent successfully"
     # Step 2: Recipient rejects the money request
    Given the "user_sender" user logs in and obtains an access token
    When the recipient sends a POST request to "CHAT_REJECT_REQUEST_URL" using test data "reject_money_request"
    Then the response status code should be 201
    And the response should contain a field named "message" with the value "Successfully rejected money request"
    And the response should contain a field named "data.status" with the value "REJECTED"

    Scenario:Successfully cancel a pending money request sent through chat:FROM ACTIVE ACCOUNT
              # Step 1: Requester cancels the money request via chat
      Given the "user_requester" user logs in and obtains an access token
      When the client sends a POST request to "CHAT_MONEY_REQUEST_URL" using test data "valid_chat_money_request" to request money via chat
      And the requester sends a POST request to "CHAT_CANCEL_REQUEST_URL" using test data "cancel_pending_money_request"
      Then the response status code should be 200
      And the response should contain a field named "message" with the value "Request cancelled successfully"
      And the response should contain a field named "data.status" with the value "CANCELLED"




















