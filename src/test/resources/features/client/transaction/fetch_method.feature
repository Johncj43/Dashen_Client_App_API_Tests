Feature: Fetch methods

  Scenario: Successfully fetch available methods
    Given the "account_02" user logs in and obtains an access token
    When device user "account_05" sends "ACCOUNT_LOOKUP_URL" for account lookup check
    And device user "account_02" sends "HQ_URL" request for HQ
    And device user "account_02" sends "FETCH_METHODS_URL" for fetch methods
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "successfully submitted"