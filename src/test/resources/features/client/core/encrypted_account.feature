Feature: Encrypted Account Lookup
  As a registered user
  I want to perform an account lookup with an encrypted account number
  So that I can securely verify account details

  Scenario: Successfully lookup an account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "ENCRYPTED_ACCOUNT_URL" for encrypted account lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Account Enquiry successful"


  Scenario:  lookup an with invalid qr value account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_04" sends "ENCRYPTED_ACCOUNT_URL" for encrypted account lookup
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Invalid or corrupted QR code"


  Scenario:  lookup an with empty qr value account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_05" sends "ENCRYPTED_ACCOUNT_URL" for encrypted account lookup
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Missing or empty QR value"
