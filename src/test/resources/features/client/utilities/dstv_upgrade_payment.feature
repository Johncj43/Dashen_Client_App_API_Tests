Feature: DSTV Upgrade Payment
  As a registered user
  I want to upgrade my DSTV subscription
  So that I can complete the DSTV upgrade payment successfully

  Scenario: Successfully upgrade DSTV subscription
    Given the "account_11" user logs in and obtains an access token
    When device user "account_11" sends "DSTV_URL" for DSTV ticket lookup
    And device user "account_11" sends "HQ_URL" request for HQ
    And device user "account_03" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_03" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_03" sends "DSTV_UPGRADE_PAYMENT" for UPGRADE dstv payment
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Payment completed successfully"

