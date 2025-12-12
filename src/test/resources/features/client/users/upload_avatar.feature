Feature:Upload avatar API

  Scenario: Client successfully uploads images to their profile
    Given the "user_02" user logs in and obtains an access token
    When I send a POST request to "UPLOAD_AVATAR_URL" with a "user_02" to upload the profile avatar
