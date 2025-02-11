package cds.scm.runner_classes;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "cds.scm.step_definitions",
		dryRun = false,
		monochrome = true,
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread/",
				"rerun:target/failed_scenarios.txt",
				"html:target/cucumber-reports/report.html",
				"json:target/cucumber-reports/report.json",
				"junit:target/cucumber-reports/report.xml"}
		//tags = {["@feature","@shipments"]}

		)
public class TestRunner {

}
