@shipments
Feature: This is to test shipments on the main menu

	@shipmentsoverview
	Scenario: Confirm the overview page loads
		Given user is on purchase orders page
		When user clicks on shipments option
		Then user should see the shipments overview page
		When the user click on shipments hyperlink
		Then the user should see the shipments details