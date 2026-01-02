Feature: Get DSTV ticket
  As a registered user
  I want to perform a DSTV ticket

  Scenario: Successfully lookup an account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "DSTV_URL" for DSTV ticket lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "DSTV smart card lookup completed successfully"


  Scenario: Fail to lookup DSTV products with invalid smartcard
    Given the "account_02" user logs in and obtains an access token
    When device user "account_05" sends "DSTV_URL" for DSTV product lookup with invalid card
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Smartcard number not found"
