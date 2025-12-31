Feature: Fetch IPs List

  As a device user
  I want to fetch the list of IPs
  So that I can verify the available IP addresses

  Scenario: Successfully fetch IPs list
    Given the "account_02" user logs in and obtains an access token
    When device user "user_02" sends "FETCH_IPS_URL" request to fetch IPs list
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Banks fetched successfully"


