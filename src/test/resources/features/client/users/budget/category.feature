Feature: Category API
  Scenario: Client successfully creates category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "category created successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client successfully fetch All Category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send aa GET request to "FETCH_ALL_CATEGORY_URL" for user "user_02"
    And the response should contain a field named "message" with the value "Category fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client successfully fetch single Category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_SINGLE_URL" for user "user_02"
    And the response should contain a field named "message" with the value "Budget fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client update a budget Category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a PUT request to "UPDATE_CATEGORY-URL" with "updateCustomscatagory" to update a category on app
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Category updated successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client delete a budget Category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget
    And the response should contain a field named "message" with the value "Budget deleted successfully"


  Scenario: Client successfully fetch icons
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_ACTIVE_URL" for user "user_02"
    And the response should contain a field named "message" with the value "Budget fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget


  Scenario: Client successfully fetch colors
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a GET request to "FETCH_ACTIVE_URL" for user "user_02"
    And the response should contain a field named "message" with the value "Budget fetched successfully"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client fails to create category with empty name
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "emptycatagory" to create a category on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Input Validation Error"


  Scenario: Client fails to create category without budget id
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send aa POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Input Validation Error"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client fails to create duplicate category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "category name already exist"
    And I send a DELETE request to "DELETE_BUDGET_URL" with a "user_02" to delete a budget

  Scenario: Client delete category
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "CREATE_BUDGET_URL" with "Customs" to create a budget on app
    When I send a POST request to "CREATE_CATEGORY_URL" with "Customscatagory" to create a category on app
    And I send aa DELETE request to "DELETE_CATEGORY-URL" with a "user_02" to delete a budget
    And the response should contain a field named "message" with the value "Category deleted successfully"


