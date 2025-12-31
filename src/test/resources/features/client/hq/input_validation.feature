Feature: Input validation
  Verification requests are validated for missing, invalid, or malformed inputs

  Background:
    Given the "account_02" user logs in and obtains an access token

  Scenario Outline: Fetch input validation rules successfully for <entity>
    When device user "<id>" sends "INPUT_VALIDATION_URL" for input validation
    Then the response status code should be 200
    And the response should contain a field named "message" with the value "Input validation fetched successfully."
    And the response should contain a non-empty field named "data"

    Examples:
      | id                 | entity       |
      | bank_dashen        | Dashen Bank  |
      | bank_other_bank    | Other Bank   |
      | utility_school_fee | School Fee   |
      | utility_flygate    | Fly Gate     |
      | wallet-telebirr    | TeleBirr     |
      | topup_ethiotelecom | EthioTelecom |
      | utlility-dstv      | DSTV         |
      | wallet-mpesa       | M-Pesa       |
