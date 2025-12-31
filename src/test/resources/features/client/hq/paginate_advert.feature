Feature: advert
  As a registered user
  I want to perform an account lookup with an encrypted account number
  So that I can securely verify account details

  Scenario: Successfully paginate advert
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "PAGINATE_ADVERT_URL" for paginate advert
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Advert fetched successfully."

