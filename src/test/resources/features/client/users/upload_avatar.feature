Feature:Upload avatar or image API
  As an authenticated client application
  Users can upload a profile avatar or personal image
  To personalize their account with a custom profile picture and enhance their overall application experience

  Scenario: Client successfully uploads images to their profile
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "UPLOAD_AVATAR_URL" with a "user_02" to upload the profile avatar
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Profile picture uploaded successfully"

  Scenario: Client fails to uploads images to their profile with an invalid access token
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "UPLOAD_AVATAR_URL" with a "user_02" to upload the profile avatar with an invalid access token
    Then the response status code should be 401
    And the response should contain a field named "message" with the value "*** Use the Right Authentication ***"
