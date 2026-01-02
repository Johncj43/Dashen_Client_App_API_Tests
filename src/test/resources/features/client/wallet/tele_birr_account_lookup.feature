Feature:Tele Birr Wallet API

  Background:
    Given the "user_B" user logs in and obtains an access token

  Scenario Outline: Successful account lookup for Tele Birr
    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with <wallet_parameter> to perform account lookup for Tele Birr
    Then the response status code should be <status_code>
    And the response should contain a field named "message" with the value <message>

    Examples:
      | wallet_parameter         | status_code | message                                                |
      | "valid_telebirr_wallet"    | 200         | "Telebirr wallet account lookup completed successfully"  |
      | "non_existent_wallet"      | 400         | "Account doesn't exist"                                  |
      | "invalid_wallet_format"    | 400         | "The phone number is not a valid Telebirr wallet phone number" |
      | "missing_wallet_number"    | 400         | "Phone number cannot be empty"                          |

  Scenario: Account lookup request is rejected without valid authentication
    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with "valid_telebirr_wallet" without a valid access token
    Then the response status code should be 403
    And the response should contain a field named "message" with the value "Invalid request"


#  Scenario: Account lookup fails for a non-existent Tele Birr wallet number
#    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with "non_existent_wallet" to perform account lookup for Tele Birr
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "Account doesn't exist"
#
#  Scenario: Account lookup fails when the wallet number is invalid or malformed
#    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with "invalid_wallet_format" to perform account lookup for Tele Birr
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "The phone number is not a valid Telebirr wallet phone number"
#
#  Scenario: Account lookup fails when required fields are missing (empty wallet number)
#    When the client sends a POST request to "TELE_BIRR_ACCOUNT_LOOKUP_URL" with "missing_wallet_number" to perform account lookup for Tele Birr
#    Then the response status code should be 400
#    And the response should contain a field named "message" with the value "Phone number cannot be empty"

