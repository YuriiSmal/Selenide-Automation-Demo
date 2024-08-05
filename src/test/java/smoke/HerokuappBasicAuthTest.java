package smoke;

import com.automation.pages.herokuapp.HerokuappMainPage;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import utils.DarkMode;

import static com.codeborne.selenide.Selenide.$;

public class HerokuappBasicAuthTest {
    @Test
    @DarkMode
    public void herokuappBasicAuthTest() {
        var basicAuthPage = new HerokuappMainPage().navigateToMainPage().openBasicAuthPage();
        basicAuthPage.acceptBasicAuth("admin", "admin");
        $("h3").shouldBe(Condition.visible);
    }
}
