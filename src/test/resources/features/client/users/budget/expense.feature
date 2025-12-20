Feature: Expense API

  Scenario: Client successfully creates an expense
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_EXPENSE_URL" with "CustomExpense" to create an expense on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "expense added successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client successfully fetch one category expense
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_EXPENSE_URL" with "CustomExpense" to create an expense on app
    When I send a GET request to "FETCH_EXPENSE_URL" for user "user_02"
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Expenses fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget




  Scenario: Client updates an expense
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_EXPENSE_URL" with "CustomExpense" to create an expense on app
    When I send a PUT request to "UPDATE_EXPENSE-URL" with "UpdateExpense" to update an expense on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "expense updated successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client deletes an expense
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_EXPENSE_URL" with "CustomExpense" to create an expense on app
    When I send a DELETE request to "DELETE_EXPENSE_URL" with a "user_02" to delete an expense
    Then the response should contain a field named "message" with the value "Expense deleted successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget



  Scenario: Client fails to create expense without category id
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_EXPENSE_URL" with "CustomExpense" to create an expense on app without category id
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "category_id is required"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget




