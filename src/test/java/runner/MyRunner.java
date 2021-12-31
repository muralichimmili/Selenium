package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        //features = "@rerun/failed_scenarios.txt",
		 features = "src/test/resources/features",
         glue = {"teststeps"},
         plugin = { "html:target/cucumber-reports.html","rerun:rerun/failed_scenarios.txt"},
         tags = "@Mail",
         monochrome = false,
         dryRun = false
)
public class MyRunner extends AbstractTestNGCucumberTests {
	
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
