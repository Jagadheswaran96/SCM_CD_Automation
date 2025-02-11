package cds.scm.webdriver_manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cds.scm.constants.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

	private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);

	public static WebDriver driver = null;

	public static void launchBrowser() {

		try {
			switch (Constants.BROWSER) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*","start-maximized","--headless=new");
				options.setExperimentalOption("excludeSwitches",
						new String[]{"enable-automation"});
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				LOGGER.info("Launching "+ Constants.BROWSER);
				driver = new ChromeDriver(options);
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				LOGGER.info("Launching "+ Constants.BROWSER);
				driver = new FirefoxDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setExperimentalOption("useAutomationExtension", false);
				edgeOptions.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
				edgeOptions.addArguments("--remote-allow-origins=*","start-maximized","-inprivate");
				Map<String, Object> pref = new HashMap<>();
				pref.put("credentials_enable_service", false);
				pref.put("profile.password_manager_enabled", false);
				edgeOptions.setExperimentalOption("prefs", pref);
				LOGGER.info("Launching "+ Constants.BROWSER);
				driver = new EdgeDriver(edgeOptions);
				break;
			default:
				WebDriverManager.iedriver().setup();
				LOGGER.info("Launching "+ Constants.BROWSER);
				driver = new InternetExplorerDriver();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}
}
