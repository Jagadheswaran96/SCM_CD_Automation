@inboundplanning
Feature: This is to test inbound planning on the main menu

	@inboundplanningoverview
	Scenario: Confirm the overview page loads
		Given user is on shipments page
		When user clicks on inbound planning option
		Then user should see the inbound planning overview page
		When the user click on the hyperlink
		Then the user should see the inbound planning details