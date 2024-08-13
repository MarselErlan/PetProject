package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumberReport.html", "json:target/testReport.json"},
        features = "/Users/macbookpro/Desktop/HelenProject/PetProject/src/test/resources/features",
        glue = "steps",
        tags = "@Saucedome",
        dryRun = false

)



public class RunnerClass {
}
