@pure_webdriver
Feature: Using WebDriver directly from cucumber steps
  As an agile team member
  I want to interact with web pages directly through Gherkin
  So I can write GUI Acceptance Tests without programming

  Scenario: POC - Creating a user
    Given there is a user with username "jbaxenom@test.com" and password "password" in the system
    And he opens the page "http://www.test.com/"
    When he types his username in the element with id "login_username"
	And he types his password in the element with id "login_password"
	And he clicks in the element with css "button"
    Then the element with css "a[data-reactid *= "forecast"]" should be visible

  Scenario: POC - without creating a user
    Given he opens the page "http://web.test.com/"
	When he types "chema.delbarco@test.com" in the element with id "login_username"
	And he types "test" in the element with id "login_password"
	And he clicks in the element with css "button"
	Then the element with css "a[data-reactid *= "forecast"]" should be visible