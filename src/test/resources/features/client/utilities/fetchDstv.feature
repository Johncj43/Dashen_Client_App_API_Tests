Feature: fetch DSTV  products
  As a registered user
  I want to perform a fetch dstv products ticket

  Scenario: Successfully lookup an account with encryption
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "FETCH_AVALABLE_DSTV_PRODUCTS" for fetch DSTV product lookup
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Available products retrieved successfully."


  Scenario: Fail to fetch DSTV products with invalid smartcard
    Given the "account_02" user logs in and obtains an access token
    When device user "account_03" sends "FETCH_AVALABLE_DSTV_PRODUCTS" for fetch DSTV product lookup with invalid card
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "No Product found."
