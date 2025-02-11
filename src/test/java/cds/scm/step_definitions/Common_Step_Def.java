package cds.scm.step_definitions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import cds.scm.constants.Constants;
import cds.scm.page_objects.LoginPage;
import cds.scm.utilities.CommonUtils;
import cds.scm.webdriver_manager.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Common_Step_Def {

	private static String scenarioName=null;

	public static String getScenarioName() {
		return scenarioName;
	}

	private static final Logger LOGGER = LogManager.getLogger(Common_Step_Def.class);

	@Before
	public void beforeScenario(Scenario scenario) {
		LOGGER.info("Execution started");

		try {

			scenarioName=scenario.getName();
			LOGGER.info("Instantiating the common utils");

			LOGGER.info("Loading the properties file");

			CommonUtils.getInstance().loadProperties();

			LOGGER.info("Checking the driver is null or not");

			if(DriverManager.getDriver()==null)

				LOGGER.info("Driver is null. Instantiating it!");

			DriverManager.launchBrowser();
			//DriverManager.getDriver().manage().deleteAllCookies();
			CommonUtils.getInstance().initWebElements();

			login();

		} catch (Exception e) {
			LOGGER.error(e);
			Assert.fail(e.getMessage());
		}

	}

	@After
	public void afterScenario() {

		try {
			//DriverManager.getDriver().manage().deleteAllCookies();
			DriverManager.getDriver().close();
		} catch (Exception e) {
			LOGGER.error(e);
			Assert.fail(e.getMessage());
		}
	}

	private void login() {

		try {
			DriverManager.getDriver().get(Constants.APP_URL);
			Map<String, String> values = credentials();
			LoginPage.getInstance().enterUserName(values.get("username"));
			LoginPage.getInstance().enterPassWord(encryptDecrypt(values.get("password")));
			LoginPage.getInstance().loginButton();
			LoginPage.getInstance().twoFactorAuthentication();
			LOGGER.info("The user is logged in");
		} catch (Exception e) {
			LOGGER.error(e);
			CommonUtils.getInstance().takeScreenshot();
			Assert.fail(e.getMessage());
		}
	}

	public Map<String, String> credentials() {

		Map<String, String> values = new HashMap<>();
		try {
			String str = null;
			FileReader fileReader = new FileReader("src/test/resources/TestData.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((str = bufferedReader.readLine()) != null) {
				//System.out.println(str);
				String[] arrOfStr = str.split("=");
				values.put(arrOfStr[0],arrOfStr[1]);
			}
			System.out.println("Username is "+values.get("username"));
			bufferedReader.close();
		} catch (IOException e) {
			LOGGER.error(e);
			Assert.fail(e.getMessage());
		}
		return values;

	}

	public String encryptDecrypt(String password) {

		byte[] encrypt = Base64.getEncoder().encode(password.getBytes());
		System.out.println("Password is "+ new String(encrypt));
		byte[] decrypt = Base64.getDecoder().decode(encrypt);
		//System.out.println("decrypted password is " + new String(decrypt));
		return password;
	}

	@AfterStep
	public void attachScreenshot(Scenario scenario) {

		if (scenario.isFailed()) {
			byte[] screenshotTaken = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshotTaken, "image/png", "errorscreen");
		}
	}
}
