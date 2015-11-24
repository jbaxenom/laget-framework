@pure_webdriver
Feature: BDD-WebDriver abstraction
  As a developer
  I want to use WebDriver through Gherkin
  So it is easier and faster to write GUI tests

  Scenario: POC - Creating a user
	Given there is a user with username "chema.delbarco@test.com" and password "test" in the system
	And the user opens the page "http://www.test.com/"
    When he types his username in the element with id "login_username"
	And he types his password in the element with id "login_password"
	And he clicks in the element with css "button"
    Then the element with css "a[data-reactid *= "forecast"]" should be visible

  Scenario: POC - without creating a user
	Given the user opens the page "http://web.test.com/"
	When he types "chema.delbarco@test.com" in the element with id "login_username"
	And he types "test" in the element with id "login_password"
	And he clicks in the element with css "button"
	Then the element with css "a[data-reactid *= "forecast"]" should be visible