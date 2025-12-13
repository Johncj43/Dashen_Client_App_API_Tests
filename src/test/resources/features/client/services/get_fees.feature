Feature: Service API

  Scenario: User create successfully get fees with access token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "GET_FEES_URL" with a "user_02" to create get fees
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Service fetched sucessfully.."

