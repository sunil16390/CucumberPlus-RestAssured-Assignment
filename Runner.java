package RunnerFile;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)

@CucumberOptions(
        features = {
                "src/test/Feature/cucumber_assignment.feature"
               // "src/test/Feature/"
        } ,
        glue = "stepDef" ,
    //    tags = "@assignment004",
        plugin = {
                "pretty",
                "json:target/cucumber-report/cucumber.json",
                "html:target/cucumber-report/cucumber.html"}
)

public class Runner {

}
