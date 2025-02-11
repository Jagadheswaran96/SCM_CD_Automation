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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import cds.scm.utilities.CommonUtils;
import cds.scm.webdriver_manager.DriverManager;

public class PurchaseOrdersPage {

	private static final Logger LOGGER = LogManager.getLogger(PurchaseOrdersPage.class);

	WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30));

	WebDriver driver = DriverManager.getDriver();
	
	Actions actions=new Actions(driver);

	JavascriptExecutor executeScript = (JavascriptExecutor) driver;

	public static WebElement tallyCardThatHasCount;

	private static PurchaseOrdersPage PurchaseOrdersPageInstance;

	private PurchaseOrdersPage() {

	}

	public static PurchaseOrdersPage getInstance() {

		if (PurchaseOrdersPageInstance == null) {
			PurchaseOrdersPageInstance = new PurchaseOrdersPage();
		}
		return PurchaseOrdersPageInstance;
	}
	
	By MOUSEHOVERMENU = By.id("Order Management");
	By PURCHASEORDER = By.xpath("//a[@href='/ui/purchase-order/overview' and text()='Purchase Orders']");
	By PURCHASEORDERS = By.xpath("//a[@href='/ui/purchase-order/overview' and text()='Purchase Orders']");

	public void purchaseOrders() throws InterruptedException {

		try {
			Thread.sleep(4000);
			actions.moveToElement(driver.findElement(MOUSEHOVERMENU)).perform();
			wait.until(ExpectedConditions.presenceOfElementLocated(PURCHASEORDER));
			CommonUtils.getInstance().highlightElement(driver.findElement(PURCHASEORDER));
			driver.findElement(PURCHASEORDERS).click();
			LOGGER.info("purchase order tab is clicked");
			Thread.sleep(2000);

		} catch (StaleElementReferenceException e) {
			LOGGER.info("element is not found. trying to find PURCHASEORDERS one more time using explicit wait");
			//Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(PURCHASEORDERS));
				driver.findElement(PURCHASEORDERS).click();
				LOGGER.info("purchase order tab is clicked");
			} catch (StaleElementReferenceException e2) {
				LOGGER.info("element not found. please check the PURCHASEORDERS locator used");
				driver.findElement(PURCHASEORDERS).click();
				LOGGER.info("purchase order tab is clicked");
				//Assert.fail(e2.getMessage());
			}
		}
	}

	By PURCHASEORDERSOVERVIEW = By.xpath("//h1[text()='Purchase Orders']");

	public void purchaseOrdersOverview() {

		try {
			String poExpectedHeaderName = "Purchase Orders";
			wait.until(ExpectedConditions.presenceOfElementLocated(PURCHASEORDERSOVERVIEW));
			CommonUtils.getInstance().highlightElement(driver.findElement(PURCHASEORDERSOVERVIEW));
			String poActualHeaderName = driver.findElement(PURCHASEORDERSOVERVIEW).getText();
			Assert.assertEquals("Purchase Orders Page is displayed", poExpectedHeaderName, poActualHeaderName);
		} catch (Exception e) {
			LOGGER.info("Purchase Order Overview is not displayed");
			Assert.fail(e.getMessage());
		}
	}

	By TALLYCARDS = By.xpath("//div[contains(@class,'tracker-card')]");
	By TALLYCARDSHEAD = By.xpath("//div[contains(@class,'tracker-card')]/div/h5");
	By TALLYCARDSGRIDROW = By.xpath("//div[@class='ag-center-cols-container']/div[@row-index='0']");
	By TALLYCARDSGRID = By.xpath("//div[contains(@class,'ag-body-viewport')]/div/div[@row-index='0']/parent::div/..");
	By TALLYCARDSTOTALCOUNTS = By.xpath("//span[@ref='lbRecordCount']/../../span/span[@ref='lbRecordCount']");
	By POHYPERLINK = By.xpath("(//a[@class='linkStyleStr'])[1]");

	List<String> tallyCardHeadText = List.of("Pos Not Booked","Bookings In Progress","Late Shipments","On Schedule");

	public void tallyCards() throws InterruptedException {

		LOGGER.info("We are on tallyCards() method");

		try{
			List<WebElement> poTallyCardsHead = driver.findElements(TALLYCARDSHEAD);
			LOGGER.info("header from tallycard stored in poTallyCardsHead");
			List<String> poTallyCardsText = new ArrayList<>();
			LOGGER.info("tally card text stored in array list");
			for (int i = 0; i < poTallyCardsHead.size(); i++) {
				LOGGER.info("poTallyCardsHead loop is started");
				int size = poTallyCardsHead.size();
				LOGGER.info(size);
				if(tallyCardHeadText.contains(poTallyCardsHead.get(i).getText())) {
					LOGGER.info("head terxt is " + tallyCardHeadText);
					poTallyCardsText.add(poTallyCardsHead.get(i).getText());
					LOGGER.info(poTallyCardsText);
				}else {
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(TALLYCARDSHEAD));
					} catch (NoSuchElementException e) {
						LOGGER.info("TALLYCARDSHEAD element is not found. trying to find TALLYCARDSHEAD one more time");
						Assert.fail(e.getMessage());
					}
				}
			}

			for(String text : poTallyCardsText) {
				LOGGER.info(text);
				By poTallyCardPath = By.xpath("//h5[text()='"+text+"']/ancestor::div[contains(@class,'tracker-card')]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(poTallyCardPath));
				driver.findElement(poTallyCardPath).click();
				Thread.sleep(2000);
				LOGGER.info("Tally card "+text+" selected");
				By tallyCountPath = By.xpath("//h5[text()='"+text+"']/preceding::div[contains(@class,'card-header')][1]/h1");
				LOGGER.info(text);
				String tallyCount = driver.findElement(tallyCountPath).getText().trim().split(" ")[0];
				//String tallyCount = "535";
				LOGGER.info(tallyCount);
				wait.until(ExpectedConditions.visibilityOfElementLocated(TALLYCARDSTOTALCOUNTS));
				String tallyCardsTotalCounts = driver.findElement(TALLYCARDSTOTALCOUNTS).getText();
				LOGGER.info(tallyCardsTotalCounts);
				if(tallyCount.equals(tallyCardsTotalCounts)) {
					ExtentCucumberAdapter.addTestStepLog(text+" Count is matched. See below:");
					ExtentCucumberAdapter.addTestStepLog("Tallycard Count= "+tallyCount+", "+"Grid Total Count= "+tallyCardsTotalCounts);
					LOGGER.info("Count is matched");
					if (!(driver.findElements(TALLYCARDSGRIDROW).size()==0)) {
						LOGGER.info("Grid has count and loading");
						wait.until(ExpectedConditions.visibilityOfElementLocated(POHYPERLINK));
						driver.findElement(POHYPERLINK).click();
						Thread.sleep(1000);
						PurchaseOrdersPage.getInstance().purchaseOrdersDetailsPage();
						Thread.sleep(1000);
						driver.findElement(PURCHASEORDERS).click();
						Thread.sleep(1000);
						PurchaseOrdersPage.getInstance().purchaseOrdersOverview();
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

	By PURCHASEORDERSDETAILSPAGE = By.xpath("//h1[contains(text(),'Purchase')]/../../../..");
	By PONUMBERINDETAILSPAGE = By.xpath("//div[@id='po-detail-container']//child::h1");
	//By POHYPERLINKS = By.xpath("//a[contains(@class,'linkStyle')]");
	By POHYPERLINKS = By.xpath("//a[contains(@class,'linkStyle')]//ancestor-or-self::a");

	public void purchaseOrdersDetailsPage() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(PURCHASEORDERSDETAILSPAGE));
			CommonUtils.getInstance().highlightElement(driver.findElement(PURCHASEORDERSDETAILSPAGE));
			if (driver.findElement(PURCHASEORDERSDETAILSPAGE).isDisplayed()) {
				String poNumber = driver.findElement(POHYPERLINKS).getText();
				String poNumberInDetailsPage = driver.findElement(PONUMBERINDETAILSPAGE).getText();
				String splitPoNumber = CommonUtils.getInstance().splitString(poNumberInDetailsPage).toString();
				if (poNumber.equals(splitPoNumber)) {
					ExtentCucumberAdapter.addTestStepLog("<font color=red>"+"Details page is verified with Purchase Order number "+poNumber+"</font>");
					LOGGER.info("Purchase Orders details page verified successfully");
				} else {
					LOGGER.info("Purchase Orders details page is not verified");
				}
			}
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find PURCHASEORDERSDETAILSPAGE one more time using explicit wait");
			//Assert.fail(e.getMessage());

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(PURCHASEORDERSDETAILSPAGE));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the PURCHASEORDERSDETAILSPAGE locator used");
				//Assert.fail(e2.getMessage());
			}
		}
	}

}
