package cds.scm.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import cds.scm.constants.Constants;
import cds.scm.page_objects.ContainerPage;
import cds.scm.page_objects.HomePage;
import cds.scm.page_objects.InboundPlanningPage;
import cds.scm.page_objects.LoginPage;
import cds.scm.page_objects.PurchaseOrdersPage;
import cds.scm.page_objects.ShipmentsPage;
import cds.scm.step_definitions.Common_Step_Def;
import cds.scm.webdriver_manager.DriverManager;

public class CommonUtils {

	private static final Logger LOGGER = LogManager.getLogger(CommonUtils.class);

	private static CommonUtils commonUtilsInstance = null;

	private CommonUtils() {

	}

	public static CommonUtils getInstance() {
		if (commonUtilsInstance==null) {
			commonUtilsInstance = new CommonUtils();
		}
		return commonUtilsInstance;
	}

	//config file loader
	public void loadProperties() {

		Properties properties = new Properties();

		try {
			properties.load(getClass().getResourceAsStream("/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Constants.APP_URL = properties.getProperty("APP_URL");
		Constants.BROWSER = properties.getProperty("BROWSER");
		/*
		 * Constants.UserName = properties.getProperty("UserName"); Constants.Password =
		 * properties.getProperty("Password");
		 */
		Constants.ExpectedURL = properties.getProperty("ExpectedURL");
		Constants.GhostUserName = properties.getProperty("GhostUserName");
		Constants.GhostPassword = properties.getProperty("GhostPassword");
	}

	public void initWebElements() {

		PageFactory.initElements(DriverManager.getDriver(), LoginPage.getInstance());
		PageFactory.initElements(DriverManager.getDriver(), HomePage.getInstance());
		PageFactory.initElements(DriverManager.getDriver(), PurchaseOrdersPage.getInstance());
		PageFactory.initElements(DriverManager.getDriver(), ShipmentsPage.getInstance());
		PageFactory.initElements(DriverManager.getDriver(), ContainerPage.getInstance());
		PageFactory.initElements(DriverManager.getDriver(), InboundPlanningPage.getInstance());
	}

	public void takeScreenshot() {
		File screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(Common_Step_Def.getScenarioName()+".png"));
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}
	}

	public void highlightElement(WebElement element) {
		JavascriptExecutor executor=(JavascriptExecutor) DriverManager.getDriver();
		executor.executeScript("arguments[0].setAttribute('style','border: 3px solid blue');", element);
	}

	public StringBuffer splitString(String string) {
		StringBuffer alphabeticals = new StringBuffer(),
				numbers = new StringBuffer(),
				specialCharacters = new StringBuffer();
		for (int i=0; i<string.length(); i++)
		{
			if (Character.isDigit(string.charAt(i)))
				numbers.append(string.charAt(i));
			else if(Character.isAlphabetic(string.charAt(i)))
				alphabeticals.append(string.charAt(i));
			else
				specialCharacters.append(string.charAt(i));
		}
		return numbers;
	}
}
