Feature: Link Own Account
  As a registered user
  I want to link my own account
  So that I can access it in the application

  Scenario: Successfully link own account
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "LINK_OWN_ACCOUNT" for linking own account
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Account already linked Successfully"

  Scenario:  Attempt to link an already linked account
    Given the "account_02" user logs in and obtains an access token
    When device user "account_02" sends "LINK_OWN_ACCOUNT" for linking own account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "Account already linked"


  Scenario: Attempt to link account belonging to another user
    Given the "account_02" user logs in and obtains an access token
    When device user "account_03" sends "LINK_OWN_ACCOUNT" for linking own account
    Then the response status code should be 400
    And the response should contain a field named "message" with the value "This account belongs to another member. Linking is not allowed."

