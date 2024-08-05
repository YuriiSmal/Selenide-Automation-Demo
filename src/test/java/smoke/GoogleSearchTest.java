package smoke;

import com.automation.pages.google.GoogleLandingPage;
import common.BaseUITest;
import org.testng.annotations.Test;
import utils.DarkMode;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleSearchTest extends BaseUITest {
    @Test
    @DarkMode
    public void googleSearchTest() {
        var googleLandingPage = new GoogleLandingPage().navigateToLandingPage();
        var urlBefore = url();
        googleLandingPage.searchFor("Who am I?");
        var urlAfter = url();

        assertThat(urlBefore)
                .as("Just check that link was changed...")
                .isNotEqualTo(urlAfter);
    }
}
