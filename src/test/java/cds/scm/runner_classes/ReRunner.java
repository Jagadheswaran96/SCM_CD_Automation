package cds.scm.runner_classes;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "@target/failed_scenarios.txt",
		glue = "cds.scm.step_definitions",
		dryRun = false,
		monochrome = true
		)
public class ReRunner {

}
