package cds.scm.step_definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import cds.scm.page_objects.ShipmentsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Shipments_Def {

	private static final Logger LOGGER = LogManager.getLogger(Shipments_Def.class);

	@When("user clicks on shipments option")
	public void user_clicks_on_shipments_option() {

		try {
			ShipmentsPage.getInstance().selectShipments();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
	@Then("user should see the shipments overview page")
	public void user_should_see_the_shipments_overview_page() {

		try {
			ShipmentsPage.getInstance().shipmentsOverview();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
	@When("the user click on shipments hyperlink to see the shipments details page")
	public void the_user_click_on_shipments_hyperlink_to_see_the_shipments_details_page() {

		try {
			ShipmentsPage.getInstance().tallyCards();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}
}
