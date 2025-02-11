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

import cds.scm.utilities.CommonUtils;
import cds.scm.webdriver_manager.DriverManager;

public class LoginPage {

	private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

	WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));

	private static LoginPage LoginInstance;

	private LoginPage() {

	}

	public static LoginPage getInstance() {

		if (LoginInstance==null) {
			LoginInstance=new LoginPage();
		}
		return LoginInstance;
	}

	//using By locators
	By USERNAME=By.id("username");
	By PASSWORD=By.id("password");
	By LOGIN_BUTTON=By.id("btnLogin");
	By INPUT_OTP=By.id("code");
	By VERIFY_BUTTON=By.xpath("//input[@value='VERIFY']");
	By REMEMBER_DEVICE=By.id("rememberDevice");

	WebDriver driver=DriverManager.getDriver();

	public void enterUserName(String userName) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
			CommonUtils.getInstance().highlightElement(driver.findElement(USERNAME));
			driver.findElement(USERNAME).sendKeys(userName);

		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find USERNAME one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {

				wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the USERNAME locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void enterPassWord(String passWord) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
			CommonUtils.getInstance().highlightElement(driver.findElement(PASSWORD));
			driver.findElement(PASSWORD).sendKeys(passWord);
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find PASSWORD one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
				wait.until(ExpectedConditions.presenceOfElementLocated(PASSWORD));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the PASSWORD locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void loginButton() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
			CommonUtils.getInstance().highlightElement(driver.findElement(LOGIN_BUTTON));

			driver.findElement(LOGIN_BUTTON).click();
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find LOGIN_BUTTON one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {
				WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the LOGIN_BUTTON button locator used");
				Assert.fail(e2.getMessage());
			}
		}
	}

	public void twoFactorAuthentication() throws InterruptedException {

		try {
			wait.until(ExpectedConditions.urlToBe("https://auth.testcenturyvms.com/Http2Fa/Index"));
			String title=driver.getTitle();
			Assert.assertEquals(title, "Century Distribution Systems");
			CommonUtils.getInstance().highlightElement(driver.findElement(INPUT_OTP));
			Thread.sleep(18000);
			driver.findElement(REMEMBER_DEVICE).click();
			driver.findElement(VERIFY_BUTTON).click();
			/*
			 * String otp=driver.findElement(INPUT_OTP).getText(); if (!(otp.length()==0)) {
			 * driver.findElement(REMEMBER_DEVICE).click();
			 * driver.findElement(VERIFY_BUTTON).click(); }
			 */
		} catch (NoSuchElementException e) {
			LOGGER.info("element is not found. trying to find OTP one more time using explicit wait");
			Assert.fail(e.getMessage());
			try {

				wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME));
			} catch (Exception e2) {
				LOGGER.info("element not found. please check the OTP");
				Assert.fail(e2.getMessage());
			}
		}
	}
}
