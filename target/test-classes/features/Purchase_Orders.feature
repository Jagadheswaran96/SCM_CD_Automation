@purchaseorders
Feature: This is to test purchase order on the main menu

	@purchaseordersoverview
	Scenario: Confirm the overview page loads
		Given user is on login page 
		And user enters username and password
		Then user should see the home page
		When user clicks on profile icon
		And selects ghost user option
		And user enters ghostusername and ghostpassword
		Then user should see the overview page and display of PO detail