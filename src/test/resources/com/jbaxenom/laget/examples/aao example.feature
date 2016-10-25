Feature: Testing with actors and actions
  As an agile team member
  I want to test using actors and actions
  So that I don't need to know the actual implementation

  Scenario: Test using Actors and Actions
    Given there is a user with username "chema" and password "pass" in the system
    When he performs the example action
    Then it is successful

