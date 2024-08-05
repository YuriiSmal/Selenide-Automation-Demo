Feature: Login functionality

  Scenario Outline: Successful login with valid credentials
    Given the user is on the landing page
    When the user click sign in
    Then the user should be redirected to the login page
    When the user enters "<username>" and "<password>"
    And clicks the login button
    Then the user should be redirected to the my account

    Examples:
      | username       | password |
      | super_user     | 12345    |
      | not_super_user | 54321    |
