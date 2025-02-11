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

public class InboundPlanningPage {

	private static final Logger LOGGER = LogManager.getLogger(InboundPlanningPage.class);

	WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));

	WebDriver driver = DriverManager.getDriver();

	JavascriptExecutor executeScript = (JavascriptExecutor) driver;

	public static WebElement tallyCardThatHasCount;

	private static InboundPlanningPage InboundPlanningPageInstance;

	private InboundPlanningPage() {

	}

	public static InboundPlanningPage getInstance() {

		if (InboundPlanningPageInstance == null) {
			InboundPlanningPageInstance = new InboundPlanningPage();
		}
		return InboundPlanningPageInstance;
	}

	By SHIPMENT = By.xpath("//a[@role='button' and @id='Shipment']");

	public void shipmentDropDown() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENT));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENT));
			driver.findElement(SHIPMENT).click();
			LOGGER.info("Shipment Dropdown selected");

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SHIPMENT one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENT));
				driver.findElement(SHIPMENT).click();
				LOGGER.info("Shipment Dropdown selected");
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the SHIPMENT locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	By INBOUNDPLANNING = By.xpath("//a[@href='/ui/inbound-planning/overview' and text()='Inbound Planning']");

	public void selectInboundPlanning() throws InterruptedException {

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(INBOUNDPLANNING));
			CommonUtils.getInstance().highlightElement(driver.findElement(INBOUNDPLANNING));
			driver.findElement(INBOUNDPLANNING).click();
			LOGGER.info("Inbound Planning tab is clicked");
			Thread.sleep(3000);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find INBOUNDPLANNING one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(INBOUNDPLANNING));
				driver.findElement(INBOUNDPLANNING).click();
				LOGGER.info("Inbound Planning tab is clicked");
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the INBOUNDPLANNING locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	By INBOUNDPLANNINGOVERVIEW = By.xpath("//h1[text()='Inbound Planning']");

	public void inboundPlanningOverview() {

		try {
			String ipExpectedHeaderName = "Inbound Planning";
			wait.until(ExpectedConditions.presenceOfElementLocated(INBOUNDPLANNINGOVERVIEW));
			CommonUtils.getInstance().highlightElement(driver.findElement(INBOUNDPLANNINGOVERVIEW));
			String ipActualHeaderName = driver.findElement(INBOUNDPLANNINGOVERVIEW).getText();
			Assert.assertEquals("Inbound Planning Page is displayed", ipExpectedHeaderName, ipActualHeaderName);
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find INBOUNDPLANNINGOVERVIEW one more time using explicit wait");
			//Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(INBOUNDPLANNINGOVERVIEW));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the INBOUNDPLANNINGOVERVIEW locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}

	By TALLYCARDS = By.xpath("//div[contains(@class,'tracker-card')]");
	By TALLYCARDSHEAD = By.xpath("//div[contains(@class,'tracker-card')]/div/h5");
	By TALLYCARDSGRIDROW = By.xpath("//div[@class='ag-center-cols-container']/div[@row-index='0']");
	By TALLYCARDSGRID = By.xpath("//div[contains(@class,'ag-body-viewport')]/div/div[@row-index='0']/parent::div/..");
	By TALLYCARDSTOTALCOUNTS = By.xpath("//span[@ref='lbRecordCount']/../../span/span[@ref='lbRecordCount']");
	By IPHYPERLINK = By.xpath("(//a[@class='linkStyleStr'])[1]");

	List<String> tallyCardHeadText = List.of("Inbound to Port","At Port","Inbound to DC");

	public void tallyCards() throws InterruptedException {

		LOGGER.info("We are on tallyCards() method");

		try{
			List<WebElement> ipTallyCardsHead = driver.findElements(TALLYCARDSHEAD);
			LOGGER.info("header from tallycard stored in poTallyCardsHead");
			List<String> ipTallyCardsText = new ArrayList<>();
			LOGGER.info("tally card text stored in array list");
			for (int i = 0; i < ipTallyCardsHead.size(); i++) {
				LOGGER.info("ipTallyCardsHead loop is started");
				int size = ipTallyCardsHead.size();
				LOGGER.info(size);
				if(tallyCardHeadText.contains(ipTallyCardsHead.get(i).getText())) {
					LOGGER.info("head terxt is " + tallyCardHeadText);
					ipTallyCardsText.add(ipTallyCardsHead.get(i).getText());
					LOGGER.info(ipTallyCardsText);
				}else {
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(TALLYCARDSHEAD));
					} catch (NoSuchElementException e) {
						LOGGER.info("TALLYCARDSHEAD element is not found. trying to find TALLYCARDSHEAD one more time");
						Assert.fail(e.getMessage());
					}
				}
			}

			for(String text : ipTallyCardsText) {
				LOGGER.info("we are on for loop");
				LOGGER.info(text);
				By ipTallyCardPath = By.xpath("//h5[text()='"+text+"']/ancestor::div[contains(@class,'tracker-card')]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(ipTallyCardPath));
				LOGGER.info("waited until visibility of "+ ipTallyCardPath);
				driver.findElement(ipTallyCardPath).click();
				Thread.sleep(2000);
				LOGGER.info("tally card "+text+" clicked");
				By tallyCountPath = By.xpath("//h5[text()='"+text+"']/preceding::div[contains(@class,'card-header')][1]/h1");
				LOGGER.info(tallyCountPath);
				String tallyCount = driver.findElement(tallyCountPath).getText().trim().split(" ")[0];
				LOGGER.info(tallyCount);
				String tallyCardsTotalCounts = driver.findElement(TALLYCARDSTOTALCOUNTS).getText();
				LOGGER.info(tallyCardsTotalCounts);
				if(tallyCount.equals(tallyCardsTotalCounts)) {
					ExtentCucumberAdapter.addTestStepLog(text+" Count is matched. See below:");
					ExtentCucumberAdapter.addTestStepLog("Tallycard Count= "+tallyCount+", "+"Grid Total Count= "+tallyCardsTotalCounts);
					LOGGER.info("Count is matched");
					//executeScript.executeScript("javascript:window.scrollBy(0,100)");
					if (!(driver.findElements(TALLYCARDSGRIDROW).size()==0)) {
						LOGGER.info("Grid has count and loading");
						Thread.sleep(1000);
						executeScript.executeScript("javascript:window.scrollBy(0,100)");
						wait.until(ExpectedConditions.visibilityOfElementLocated(IPHYPERLINK));
						/*
						 * Actions actions = new Actions(driver);
						 * actions.moveToElement(driver.findElement(IPHYPERLINK)).click().build().
						 * perform();
						 */
						driver.findElement(IPHYPERLINK).click();
						Thread.sleep(2000);
						InboundPlanningPage.getInstance().inboundPlanningDetailsPage();
						Thread.sleep(1000);
						InboundPlanningPage.getInstance().shipmentDropDown();
						InboundPlanningPage.getInstance().selectInboundPlanning();
						Thread.sleep(1000);
						InboundPlanningPage.getInstance().inboundPlanningOverview();

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

	By INBOUNDPLANNINGDETAILSPAGE = By.xpath("//h1[contains(text(),'Container')]/../../../..");

	public void inboundPlanningDetailsPage() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(INBOUNDPLANNINGDETAILSPAGE));
			CommonUtils.getInstance().highlightElement(driver.findElement(INBOUNDPLANNINGDETAILSPAGE));
			if (driver.findElement(INBOUNDPLANNINGDETAILSPAGE).isDisplayed()) {
				LOGGER.info("Inbound Planning details page loaded successfully");
			} else {
				LOGGER.info("Inbound Planning details page not loaded");
			}
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find INBOUNDPLANNINGDETAILSPAGE one more time using explicit wait");
			//Assert.fail(e.getMessage());
			try {

				wait.until(ExpectedConditions.visibilityOfElementLocated(INBOUNDPLANNINGDETAILSPAGE));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the INBOUNDPLANNINGDETAILSPAGE locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}

}
