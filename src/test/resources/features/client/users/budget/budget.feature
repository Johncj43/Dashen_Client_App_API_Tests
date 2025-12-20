Feature: Budget API
  Scenario: Client successfully creates budget
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Budget registered successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget
    And the response should contain a field named "message" with the value "Budget deleted successfully"

  Scenario: Client successfully fetch active budget
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_ACTIVE_URL" for user "user_02"
    And the response should contain a field named "message" with the value "Budget fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client successfully fetch history
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_HISTORY_URL" for user "user_02"

  Scenario: Client successfully fetch single
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_SINGLE_URL" for single user "user_02"
    And the response should contain a field named "message" with the value "Budget fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client fails to create a budget without deviceUUID
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customer" to create a budget on app
  Then the response status code should be 400
  And the response should contain a field named "message" with the value "invalid request"


  Scenario: user try to create a budget with a negative amount
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "customamountnegative" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "amount must be greater than 0"


  Scenario: user try to create a budget with a 0 amount
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "customzero" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "amount must be greater than 0"


  Scenario: user try to create budget startdate greater than enddate
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "customstartdate" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "customStartDate must not be greater than customEndDate"


  Scenario: user try to create budget amount with string value
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "customstring" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "amount must not be a string"

  Scenario: Client fails to create a budget without budgetType
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "missingBudgetType" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "budgetType is required"

  Scenario: user try to create a budget with a negative amount
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "customamountnegative" to create a budget on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "amount must be greater than 0"


  Scenario: Client successfully creates a weekly budget.
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "weeklytype" to create aa budget on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Budget registered successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: "Client successfully creates a monthly budget.
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "monthlytype" to create aa budget on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Budget registered successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client successfully creates a yearly budget.
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "yearlytype" to create aa budget on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Budget registered successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client delete a budget
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget
    And the response should contain a field named "message" with the value "Budget deleted successfully"

  Scenario: Client update a budget
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a PUT request to "UPDATE_BUDGET-URL" with "monthlytype" to update a budget on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Budget updated successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

