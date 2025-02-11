package cds.scm.step_definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import cds.scm.page_objects.InboundPlanningPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Inbound_Planning_Def {

	private static final Logger LOGGER = LogManager.getLogger(Inbound_Planning_Def.class);

	@Given("user is on shipments page")
	public void user_is_on_shipments_page() {

		try {
			InboundPlanningPage.getInstance().shipmentDropDown();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}

	@When("user clicks on inbound planning option")
	public void user_clicks_on_inbound_planning_option() {

		try {
			InboundPlanningPage.getInstance().selectInboundPlanning();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
	@Then("user should see the inbound planning overview page")
	public void user_should_see_the_inbound_planning_overview_page() {

		try {
			InboundPlanningPage.getInstance().inboundPlanningOverview();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
	@When("the user click on the hyperlink to see the inboundplanning details page")
	public void the_user_click_on_the_hyperlink_to_see_the_inboundplanning_details_page() {

		try {
			InboundPlanningPage.getInstance().tallyCards();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
}
