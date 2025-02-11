@feature
Feature: This is to test VIZIV application

	@scenario
	Scenario: Test the features
		Given user is logged in 
		And ghost the user
		Then the user click on purchase orders hyperlink to see the purchase orders details page
		When user clicks on shipments option
		Then user should see the shipments overview page
		When the user click on shipments hyperlink to see the shipments details page
		Given user is on shipment page
		When user clicks on containers option
		Then user should see the containers overview page
		When the user click on container hyperlink to see the container details page
		Given user is on shipments page
		When user clicks on inbound planning option
		Then user should see the inbound planning overview page
		When the user click on the hyperlink to see the inboundplanning details page