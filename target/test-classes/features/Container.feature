@containers
Feature: This is to test shipments container on the main menu

	@containersoverview
	Scenario: Confirm the overview page loads
		Given user is on shipment page
		When user clicks on containers option
		Then user should see the containers overview page
		When the user click on container hyperlink
		Then the user should see the container details