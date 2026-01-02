Feature: Verify Qr
  As a registered user
  I want to perform an account lookup with an encrypted account number
  So that I can securely verify account details

  Scenario: Successfully lookup an account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "VERIFY_QR" for Qr verification
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "QR code verified"

