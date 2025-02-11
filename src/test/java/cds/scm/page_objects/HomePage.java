package cds.scm.page_objects;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cds.scm.constants.Constants;
import cds.scm.utilities.CommonUtils;
import cds.scm.webdriver_manager.DriverManager;

public class HomePage {

	private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

	WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));

	private static HomePage HomePageInstance;

	private HomePage() {

	}

	public static HomePage getInstance() {

		if (HomePageInstance==null) {
			HomePageInstance=new HomePage();
		}
		return HomePageInstance;
	}

	By PROFILE_ICON = By.xpath("//button[@id='accountDropdown']");
	By USER_ICON = By.xpath("//a[text()='Ghost User']");
	By GHOSTUSERNAME = By.xpath("//label[text()='UserID']/..//input");
	By GHOSTPASSWORD = By.xpath("//label[text()='Subgroup']/..//input");
	By SUBMIT_BUTTON = By.xpath("//button[text()='Ghost User']");
	By OVERVIEW_PAGE = By.xpath("//span[text()='"+Constants.GhostUserName+" - "+Constants.GhostPassword+"']");

	WebDriver driver =DriverManager.getDriver();

	public void clickProfileIcon() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_ICON));
			CommonUtils.getInstance().highlightElement(driver.findElement(PROFILE_ICON));
			driver.findElement(PROFILE_ICON).click();

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find PROFILE_ICON one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {

				wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_ICON));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the PROFILE_ICON locator used");
				Assert.fail(e.getMessage());
			}
		}
	}

	public void clickUserIcon() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(USER_ICON));
			CommonUtils.getInstance().highlightElement(driver.findElement(USER_ICON));
			driver.findElement(USER_ICON).click();

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find USER_ICON one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(USER_ICON));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the USER_ICON locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void enterGhostUserName(String ghostUserName) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(GHOSTUSERNAME));
			CommonUtils.getInstance().highlightElement(driver.findElement(GHOSTUSERNAME));
			driver.findElement(GHOSTUSERNAME).sendKeys(ghostUserName);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find GHOSTUSERNAME one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(GHOSTUSERNAME));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the GHOSTUSERNAME locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void enterGhostPassword(String ghostPassword) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(GHOSTPASSWORD));
			CommonUtils.getInstance().highlightElement(driver.findElement(GHOSTPASSWORD));

			driver.findElement(GHOSTPASSWORD).sendKeys(ghostPassword);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find GHOSTPASSWORD one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(GHOSTPASSWORD));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the GHOSTPASSWORD locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void submitButton() throws InterruptedException {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_BUTTON));
			CommonUtils.getInstance().highlightElement(driver.findElement(SUBMIT_BUTTON));
			driver.findElement(SUBMIT_BUTTON).click();
			Thread.sleep(2000);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find SUBMIT_BUTTON one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_BUTTON));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the SUBMIT_BUTTON locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}
	public boolean overViewPage() {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(OVERVIEW_PAGE));
			CommonUtils.getInstance().highlightElement(driver.findElement(OVERVIEW_PAGE));

			boolean ghostSuccessful =driver.findElement(OVERVIEW_PAGE).isDisplayed();
			if(ghostSuccessful) {
				LOGGER.info("Ghosting user is successful"); }


		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find OVERVIEW_PAGE one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(OVERVIEW_PAGE));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the OVERVIEW_PAGE locator used");
				Assert.fail(e2.getMessage());
			}
		}
		return true;
	}
}
