Feature: PayBills
  As a registered user
  I want to fetch available payment methods
  So that I can complete FlyGate PayBills payment successfully


  Scenario: Successfully fetch available methods
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "FLYGATE_URL" for flyGate ticket lookup
    And device user "account_08" sends "HQ_URL" request for HQ
    And device user "account_02" sends "FETCH_METHODS_URL" for fetch methods
    And device user "account_02" sends "PIN_VERIFY" for the pin verify methods
    And device user "account_02" sends "PAY_BILLS_URL" for pay bills payment
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Transaction successful"


