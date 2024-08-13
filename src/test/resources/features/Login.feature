@Saucedome
Feature: login to saucedemo

  Scenario: login to Sauce Demo and verify you logged in

    Given  user is on the log in page
    Then user provides a valid username
    And  user provides a valid password
    And user click on login button
    And  user logged in