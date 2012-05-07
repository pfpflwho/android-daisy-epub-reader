Feature: Users should be able to change to another valid root folder.
	In order to allow users to limit the scope of the automatic search for books
	As a user
	I want to be able to change from the current folder to another valid folder.

  Scenario: As a user I can launch the Settings menu and set the root folder
    When I press "Settings"
    Then I see "Root Folder"
    Then I touch the "Root" text
    And I see "OK"
    And I see "Cancel"
    Then I see "/sdcard/" 
    Then I clear input field number 1
    Then I enter "/" into input field number 1
    Then I go back
    Then I press "OK"
    Then I wait for "New folder name saved" to appear
    Then I wait for 5 seconds
    Then I go back
    Then I wait for "Root Folder" to appear
    Then I touch the "Root" text
    And I see "OK"
    And I see "Cancel"
    Then I see "/"
    Then I wait for 2 seconds
    Then I clear input field number 1
    Then I enter "/sdcard/" into input field number 1
    Then I wait for 5 seconds
    Then I press "OK"
    Then I wait for "New folder name saved" to appear
    Then I go back
    Then I wait for "Root Folder" to appear
    Then I go back

