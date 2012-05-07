Feature: Launch Help
  Scenario: As a user I can launch the Help and capture the output
    When I press "Help"
    Then I see "Instructions for the DAISY player"
    And I see "and sections"
    Then I press "Close Instructions"
    And I see "Help"
    And I see "Settings"
