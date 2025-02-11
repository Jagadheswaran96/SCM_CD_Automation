
package cds.scm.step_definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import cds.scm.page_objects.ContainerPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Container_Def {

	private static final Logger LOGGER = LogManager.getLogger(Container_Def.class);

	@Given("user is on shipment page")
	public void user_is_on_shipment_page() {

		try {
			ContainerPage.getInstance().shipmentDropDown();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}

	}

	@When("user clicks on containers option")
	public void user_clicks_on_containers_option() {

		try {
			ContainerPage.getInstance().selectContainers();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			//Assert.fail(e.getMessage());
		}

	}

	@Then("user should see the containers overview page")
	public void user_should_see_the_containers_overview_page() {

		try {
			ContainerPage.getInstance().containersPageOverview();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}

	}

	@When("the user click on container hyperlink to see the container details page")
	public void the_user_click_on_container_hyperlink_to_see_the_container_details_page() {

		try {
			ContainerPage.getInstance().tallyCards();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			//Assert.fail(e.getMessage());
		}
	}
}
