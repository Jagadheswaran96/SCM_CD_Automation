package cds.scm.step_definitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import cds.scm.constants.Constants;
import cds.scm.page_objects.HomePage;
import cds.scm.page_objects.PurchaseOrdersPage;
import cds.scm.webdriver_manager.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Purchase_Orders_Def {

	private static final Logger LOGGER = LogManager.getLogger(Purchase_Orders_Def.class);

	@Given("user is logged in")
	public void user_is_logged_in() {

		try {

			String currentURL = DriverManager.getDriver().getCurrentUrl();
			if (currentURL.contains(Constants.ExpectedURL)) {
				LOGGER.info("The user is on home page");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}

	@And("ghost the user")
	public void ghost_the_user() {

		try {

			HomePage.getInstance().clickProfileIcon();
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}

		try {

			HomePage.getInstance().clickUserIcon();
			LOGGER.info("Ghost user is selected");
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}

		try {

			HomePage.getInstance().enterGhostUserName(Constants.GhostUserName);
			HomePage.getInstance().enterGhostPassword(Constants.GhostPassword);
			HomePage.getInstance().submitButton();
			LOGGER.info("The user MDarling is ghosted successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}

	@Then("the user click on purchase orders hyperlink to see the purchase orders details page")

	public void the_user_click_on_purchase_orders_hyperlink_to_see_the_purchase_orders_details_page() {

		try {

			try {
				HomePage.getInstance().overViewPage();
			} catch (Exception e) {
				LOGGER.error(e);
				//CommonUtils.getInstance().takeScreenshot();
				//Assert.fail(e.getMessage());
			}

			try {
				PurchaseOrdersPage.getInstance().purchaseOrders();
			} catch (Exception e) {
				LOGGER.error(e);
				//CommonUtils.getInstance().takeScreenshot();
				Assert.fail(e.getMessage());
			}


			try {
				PurchaseOrdersPage.getInstance().purchaseOrdersOverview();
			} catch (Exception e) {
				LOGGER.error(e);
				//CommonUtils.getInstance().takeScreenshot();
				//Assert.fail(e.getMessage());
			}


			try {
				PurchaseOrdersPage.getInstance().tallyCards();
			} catch (Exception e) {
				LOGGER.error(e);
				//CommonUtils.getInstance().takeScreenshot();
				Assert.fail(e.getMessage());
			}


		} catch (Exception e) {
			LOGGER.error(e);
			//CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}

	}
}
