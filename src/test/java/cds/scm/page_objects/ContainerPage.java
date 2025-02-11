package cds.scm.page_objects;

import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
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

public class ContainerPage {

	private static final Logger LOGGER = LogManager.getLogger(ContainerPage.class);

	WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));

	WebDriver driver = DriverManager.getDriver();

	JavascriptExecutor executeScript = (JavascriptExecutor) driver;

	public static WebElement tallyCardThatHasCount;

	private static ContainerPage ShipmentContainerInstance;

	private ContainerPage() {

	}

	public static ContainerPage getInstance() {

		if (ShipmentContainerInstance==null) {
			ShipmentContainerInstance=new ContainerPage();
		}
		return ShipmentContainerInstance;
	}

	//using By locators
	By SHIPMENT = By.xpath("//a[@role='button' and @id='Shipment']");

	public void shipmentDropDown() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(SHIPMENT));
			CommonUtils.getInstance().highlightElement(driver.findElement(SHIPMENT));
			driver.findElement(SHIPMENT).click();
			LOGGER.info("Shipment drop down is selected");

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SHIPMENT one more time using explicit wait");

			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(SHIPMENT));
				driver.findElement(SHIPMENT).click();
				LOGGER.info("Shipment drop down is selected");
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the SHIPMENT locator used");
			}
		}
	}

	By CONTAINERS = By.xpath("//a[@href='/ui/container/overview' and text()='Containers']");

	public void selectContainers() throws InterruptedException {

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(CONTAINERS));
			CommonUtils.getInstance().highlightElement(driver.findElement(CONTAINERS));
			driver.findElement(CONTAINERS).click();
			LOGGER.info("Containers tab is clicked");
			Thread.sleep(1000);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find CONTAINERS one more time using explicit wait");

			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(CONTAINERS));
				driver.findElement(CONTAINERS).click();
				LOGGER.info("purchase order tab is clicked");
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the CONTAINERS locator used");
			}
		}
	}

	By CONTAINERSOVERVIEW = By.xpath("//h1[text()='Containers']");

	public void containersPageOverview() {

		try {
			String ContainersExpectedHeaderName = "Containers";
			wait.until(ExpectedConditions.presenceOfElementLocated(CONTAINERSOVERVIEW));
			CommonUtils.getInstance().highlightElement(driver.findElement(CONTAINERSOVERVIEW));
			String ContainersActualHeaderName = driver.findElement(CONTAINERSOVERVIEW).getText();
			Assert.assertEquals("Containers Page is displayed", ContainersExpectedHeaderName, ContainersActualHeaderName);
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find CONTAINERSOVERVIEW one more time using explicit wait");
			//Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(CONTAINERSOVERVIEW));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the CONTAINERSOVERVIEW locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}

	By TALLYCARDS = By.xpath("//div[contains(@class,'tracker-card')]");
	By TALLYCARDSHEAD = By.xpath("//div[contains(@class,'tracker-card')]/div/h5");
	By TALLYCARDSGRIDROW = By.xpath("//div[@class='ag-center-cols-container']/div[@row-index='0']");
	By TALLYCARDSGRID = By.xpath("//div[contains(@class,'ag-body-viewport')]/div/div[@row-index='0']/parent::div/..");
	By TALLYCARDSTOTALCOUNTS = By.xpath("//span[@ref='lbRecordCount']/../../span/span[@ref='lbRecordCount']");
	By CONTAINERHYPERLINK = By.xpath("(//a[@class='linkStyleStr'])[1]");

	List<String> tallyCardHeadText = List.of("At Origin Port","On Water","At Discharge Port","Final Mile","Indeterminate");

	public void tallyCards() throws InterruptedException {

		LOGGER.info("We are on tallyCards() method");

		try{
			List<WebElement> containerTallyCardsHead = driver.findElements(TALLYCARDSHEAD);
			LOGGER.info("header from tallycard stored in poTallyCardsHead");
			List<String> containerTallyCardsText = new ArrayList<>();
			LOGGER.info("tally card text stored in array list");
			for (int i = 0; i < containerTallyCardsHead.size(); i++) {
				LOGGER.info("containerTallyCardsHead loop is started");
				int size = containerTallyCardsHead.size();
				LOGGER.info(size);
				if(tallyCardHeadText.contains(containerTallyCardsHead.get(i).getText())) {
					LOGGER.info("head terxt is " + tallyCardHeadText);
					containerTallyCardsText.add(containerTallyCardsHead.get(i).getText());
					LOGGER.info(containerTallyCardsText);
				}else {
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(TALLYCARDSHEAD));
					} catch (NoSuchElementException e) {
						LOGGER.info("TALLYCARDSHEAD element is not found. trying to find TALLYCARDSHEAD one more time");
						Assert.fail(e.getMessage());
					}
				}
			}

			for(String text : containerTallyCardsText) {
				LOGGER.info(text);
				By containerTallyCardPath = By.xpath("//h5[text()='"+text+"']/ancestor::div[contains(@class,'tracker-card')]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(containerTallyCardPath));
				Thread.sleep(1000);
				driver.findElement(containerTallyCardPath).click();
				Thread.sleep(2000);
				LOGGER.info("tally card "+text+" clicked");
				By tallyCountPath = By.xpath("//h5[text()='"+text+"']/preceding::div[contains(@class,'card-header')][1]/h1");
				LOGGER.info(text);
				String tallyCount = driver.findElement(tallyCountPath).getText().trim().split(" ")[0];
				LOGGER.info(tallyCount);
				String tallyCardsTotalCounts = driver.findElement(TALLYCARDSTOTALCOUNTS).getText();
				LOGGER.info(tallyCardsTotalCounts);
				if(tallyCount.equals(tallyCardsTotalCounts)) {
					ExtentCucumberAdapter.addTestStepLog(text+" Count is matched. See below:");
					ExtentCucumberAdapter.addTestStepLog("Tallycard Count= "+tallyCount+", "+"Grid Total Count= "+tallyCardsTotalCounts);
					LOGGER.info("Count is matched");
					//executeScript.executeScript("javascript:window.scrollBy(0,50)");
					if (!(driver.findElements(TALLYCARDSGRIDROW).size()==0)) {
						LOGGER.info("Grid has count and loading");
						Thread.sleep(1000);
						executeScript.executeScript("javascript:window.scrollBy(0,100)");
						Thread.sleep(1000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(CONTAINERHYPERLINK));
						driver.findElement(CONTAINERHYPERLINK).click();
						Thread.sleep(2000);
						ContainerPage.getInstance().containersDetailsPage();
						Thread.sleep(1000);
						ContainerPage.getInstance().shipmentDropDown();
						ContainerPage.getInstance().selectContainers();
						Thread.sleep(1000);
						ContainerPage.getInstance().containersPageOverview();
						Thread.sleep(500);

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
							//Assert.fail(e.getMessage());
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

	By CONTAINERSDETAILSPAGE = By.xpath("//h1[contains(text(),'Container')]/../../../..");

	public void containersDetailsPage() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(CONTAINERSDETAILSPAGE));
			CommonUtils.getInstance().highlightElement(driver.findElement(CONTAINERSDETAILSPAGE));
			if (driver.findElement(CONTAINERSDETAILSPAGE).isDisplayed()) {
				LOGGER.info("Containers Orders details page loaded & Verified");
			} else {
				LOGGER.info("Containers Orders details page not loaded");
			}
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find CONTAINERSDETAILSPAGE one more time using explicit wait");
			//Assert.fail(e.getMessage());

			try {

				wait.until(ExpectedConditions.visibilityOfElementLocated(CONTAINERSDETAILSPAGE));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the CONTAINERSDETAILSPAGE locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}

}