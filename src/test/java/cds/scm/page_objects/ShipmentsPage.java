package cds.scm.page_objects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import cds.scm.utilities.CommonUtils;
import cds.scm.webdriver_manager.DriverManager;

public class ShipmentsPage {

	private static final Logger LOGGER = LogManager.getLogger(ShipmentsPage.class);

	WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));

	WebDriver driver = DriverManager.getDriver();

	JavascriptExecutor executeScript = (JavascriptExecutor) driver;

	public static WebElement tallyCardThatHasCount;

	private static ShipmentsPage ShipmentsPageInstance;

	private ShipmentsPage() {

	}

	public static ShipmentsPage getInstance() {

		if (ShipmentsPageInstance == null) {
			ShipmentsPageInstance = new ShipmentsPage();
		}
		return ShipmentsPageInstance;
	}

	By SHIPMENT = By.xpath("//a[@role='button' and @id='Shipment']");
	By SHIPMENTS = By.xpath("//a[@href='/ui/shipping/overview' and text()='Shipments']");

	public void selectShipments() throws InterruptedException {

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENT));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENT));
			driver.findElement(SHIPMENT).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENTS));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENTS));
			driver.findElement(SHIPMENTS).click();
			LOGGER.info("shipments tab is clicked");
			//Thread.sleep(1000);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SHIPMENTS one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENTS));
				driver.findElement(SHIPMENTS).click();
				LOGGER.info("shipments tab is clicked");
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the SHIPMENTS locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	By SHIPMENTSOVERVIEW = By.xpath("//h1[text()='Shipments']");

	public void shipmentsOverview() {

		try {
			String shipmentsExpectedHeaderName = "Shipments";
			wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENTSOVERVIEW));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENTSOVERVIEW));
			String shipmentsActualHeaderName = driver.findElement(SHIPMENTSOVERVIEW).getText();
			Assert.assertEquals("Shipments Page is displayed", shipmentsExpectedHeaderName, shipmentsActualHeaderName);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SHIPMENTSOVERVIEW one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENTSOVERVIEW));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the SHIPMENTSOVERVIEW locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	By POPUP = By.xpath("//div[@id=\"pendo-guide-container\"]");
	By CLOSEPOPUPBUTTON = By.xpath("//button[text()='×' and @class='_pendo-close-guide']");

	public void popupHandle() {

		try {
			if (wait.until(ExpectedConditions.presenceOfElementLocated(POPUP)).isDisplayed()) {
				LOGGER.info("pop-up is displayed");
				wait.until(ExpectedConditions.visibilityOfElementLocated(CLOSEPOPUPBUTTON));
				driver.findElement(CLOSEPOPUPBUTTON).click();
				LOGGER.info("pop-up has been closed");
			} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENTSOVERVIEW));
					CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENTSOVERVIEW));
					driver.findElement(SHIPMENTSOVERVIEW).isDisplayed();
						LOGGER.info("PURCHASEORDERSOVERVIEW is verified");
					}
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SHIPMENTSOVERVIEW one more time using explicit wait");
			Assert.fail(e.getMessage());
		}
	}


	By TALLYCARDS = By.xpath("//div[contains(@class,'tracker-card')]");
	By TALLYCARDSHEAD = By.xpath("//div[contains(@class,'tracker-card')]/div/h5");
	By TALLYCARDSGRIDROW = By.xpath("//div[@class='ag-center-cols-container']/div[@row-index='0']");
	By TALLYCARDSGRID = By.xpath("//div[contains(@class,'ag-body-viewport')]/div/div[@row-index='0']/parent::div/..");
	By TALLYCARDSTOTALCOUNTS = By.xpath("//span[@ref='lbRecordCount']/../../span/span[@ref='lbRecordCount']");
	By SHIPMENTHYPERLINK = By.xpath("(//a[@class='linkStyleStr'])[1]");

	List<String> tallyCardHeadText = List.of("Updated ETA","New Advices","Scanned Documents");

	public void tallyCards() throws InterruptedException {

		LOGGER.info("We are on tallyCards() method");

		try{
			List<WebElement> shipmentTallyCardsHead = driver.findElements(TALLYCARDSHEAD);
			LOGGER.info("header from tallycard stored in shipmentTallyCardsHead");
			List<String> shipmentTallyCardsText = new ArrayList<>();
			LOGGER.info("tally card text stored in array list");
			for (int i = 0; i < shipmentTallyCardsHead.size(); i++) {
				LOGGER.info("shipmentTallyCardsHead loop is started");
				int size = shipmentTallyCardsHead.size();
				LOGGER.info(size);
				if(tallyCardHeadText.contains(shipmentTallyCardsHead.get(i).getText())) {
					LOGGER.info("head terxt is " + tallyCardHeadText);
					shipmentTallyCardsText.add(shipmentTallyCardsHead.get(i).getText());
					LOGGER.info(shipmentTallyCardsText);
				}else {
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(TALLYCARDSHEAD));
					} catch (NoSuchElementException e) {
						LOGGER.info("TALLYCARDSHEAD element is not found. trying to find TALLYCARDSHEAD one more time");
						Assert.fail(e.getMessage());
					}
				}
			}

			for(String text : shipmentTallyCardsText) {
				LOGGER.info(text);
				By shipmentTallyCardPath = By.xpath("//h5[text()='"+text+"']/ancestor::div[contains(@class,'tracker-card')]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(shipmentTallyCardPath));
				driver.findElement(shipmentTallyCardPath).click();
				Thread.sleep(3000);
				LOGGER.info("tally card "+text+" selected");
				By tallyCountPath = By.xpath("//h5[text()='"+text+"']/preceding::div[contains(@class,'card-header')][1]/h1");
				LOGGER.info(tallyCountPath);
				String tallyCount = driver.findElement(tallyCountPath).getText().trim().split(" ")[0];
				LOGGER.info(tallyCount);
				wait.until(ExpectedConditions.visibilityOfElementLocated(TALLYCARDSTOTALCOUNTS));
				Thread.sleep(1000);
				String tallyCardsTotalCounts = driver.findElement(TALLYCARDSTOTALCOUNTS).getText();
				LOGGER.info(tallyCardsTotalCounts);
				if(tallyCount.equals(tallyCardsTotalCounts)) {
					ExtentCucumberAdapter.addTestStepLog(text+" Count is matched. See below:");
					ExtentCucumberAdapter.addTestStepLog("Tallycard Count= "+tallyCount+", "+"Grid Total Count= "+tallyCardsTotalCounts);
					LOGGER.info("Count is matched");
					if (!(driver.findElements(TALLYCARDSGRIDROW).size()==0)) {
						LOGGER.info("Grid has count and loading");
						wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENTHYPERLINK));
						driver.findElement(SHIPMENTHYPERLINK).click();
						Thread.sleep(2000);
						ShipmentsPage.getInstance().shipmentsDetailsPage();
						Thread.sleep(1000);
						ShipmentsPage.getInstance().selectShipments();
						Thread.sleep(1000);
						/*
						 * ShipmentsPage.getInstance().popupHandle(); Thread.sleep(1000);
						 * ShipmentsPage.getInstance().shipmentsOverview();
						 */

					} else {
						try {
							By GridData = By.xpath("//span[text()='No Rows To Show']");
							wait.until(ExpectedConditions.presenceOfElementLocated(GridData));
							if(driver.findElement(GridData).isDisplayed() && tallyCount.equals("0")) {
								LOGGER.info("Tally has no count, No rows to show Hence moving to next tally card");
							} else {
								LOGGER.info("Tally has count");
							}
						} catch (NoSuchElementException e) {
							LOGGER.info("element is not found. trying to find Grid Data one more time using explicit wait");
							Assert.fail(e.getMessage());
						}
					}
				}else {
					LOGGER.info("Count is not matched!");
					ExtentCucumberAdapter.addTestStepLog("<font color=red>"+text+" Count is mismatched. See below:"+"</font>");
					ExtentCucumberAdapter.addTestStepLog("<font color=red>"+"Tallycard Count= "+"<b><i><u>"+tallyCount+"</u></i></b>"+", "+"Grid Total Count= "+"<b><i><u>"+tallyCardsTotalCounts+"</u></i></b>"+"</font>");
				}
			}
		}catch (Exception e) {
			LOGGER.error(e);
			//Assert.fail(e.getMessage());
		}
	}

	By SHIPMENTSDETAILSPAGE = By.xpath("//h1[contains(text(),'Shipment')]/../../../..");

	public void shipmentsDetailsPage() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENTSDETAILSPAGE));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENTSDETAILSPAGE));
			if (driver.findElement(SHIPMENTSDETAILSPAGE).isDisplayed()) {
				LOGGER.info("Shipments details page loaded & Verified");
			} else {
				LOGGER.info("Shipments details page not loaded");
			}
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find Shipments one more time using explicit wait");
			//Assert.fail(e.getMessage());
			try {

				wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENTSDETAILSPAGE));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the Shipments locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}
}
