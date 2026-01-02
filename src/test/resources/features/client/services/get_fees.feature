Feature: Service API
  As an authenticated client application,
  users can retrieve a list of available services (e.g., airtime top-up, bill payments, transfers)
  to display supported services and enable selection for performing transactions.

  This feature tests the "Get Fees" functionality of the Service API, covering scenarios for:
  - Successfully fetching fees with a valid access token
  - Handling invalid or expired access tokens
  - Validating required fields and returning proper error messages
  - Ensuring correct HTTP status codes are returned for each scenario

  Scenario: User create successfully get fees with access token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to create get fees
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Service fetched sucessfully.."

  Scenario: Failed attempt to fetch fees using an invalid access token
      Given the "user_02" user logs in and obtains an access token
      When I send a POST request to "GET_FEES_URL" with a "linked account" to get fees with an invalid access token
      Then the response status code should be 401
      And the response should contain a field named "message" with the value "invalid request"

  Scenario: Failed attempt to fetch fees using an invalid access token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "linked account" to get fees with expired session id
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "invalid request"

  Scenario Outline: User attempts to fetch fees with an empty required field – returns 400 Bad Request
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "<empty_field>" to create get fees
    Then the response status code should be 400
    And the response should contain a field named "message" with the value <message_field>

    @fee_validation
    Examples:
      | empty_field                   | message_field|
      | empty debit account field     |"Invalid request payload - debit_accountnumber is not allowed to be empty"|
      | empty credit account field    |"Invalid request payload - credit_accountnumber is not allowed to be empty" |
      | empty service name            |"Invalid request payload - service_name is not allowed to be empty"        |

# Scenario:User attempts to fetch fees for an empty account field
#   Given the "user_02" user logs in and obtains an access token
#   When I send a POST request to "GET_FEES_URL" with a "empty debit account field" to create get fees
#   Then the response status code should be 400
#   And the response should contain a field named "message" with the value "invalid request"

#  Scenario:User attempts to fetch fees for an empty credit account field
#    Given the "user_02" user logs in and obtains an access token
#    When I send a POST request to "GET_FEES_URL" with a "empty credit account field" to create get fees
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "invalid request"
#
#  Scenario:User attempts to fetch fees for an empty service name
#    Given the "user_02" user logs in and obtains an access token
#    When I send a POST request to "GET_FEES_URL" with a "empty service name" to create get fees
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "invalid request"















