package smoke;

import com.automation.pages.letcode.LetCodeTestPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.AlertNotFoundError;
import common.BaseUITest;
import org.openqa.selenium.Alert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DarkMode;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LetCodeAlertsTest extends BaseUITest {

    @Test(dataProvider = "alerts", dataProviderClass = LetCodeAlertsTest.class)
    @DarkMode
    public void alertsTest(AlertType alertsType) {
        var letCodeAlertsPage = new LetCodeTestPage()
                .navigateToTestPage()
                .goToAlertsPage();

        switch (alertsType) {
            case SIMPLE -> letCodeAlertsPage.openSimpleAlert();
            case CONFIRM -> letCodeAlertsPage.openConfirmAlert();
            case PROMPT -> letCodeAlertsPage.openPromptAlert();
            case MODERN -> letCodeAlertsPage.openModernAlert();
        }

        //Just for example!!!
        sleep(3000);

        if (alertsType == AlertType.MODERN) {
            $(byText("Modern Alert - Some people address me as sweet alert as well"))
                    .shouldBe(Condition.visible);
            letCodeAlertsPage.closeModernAlert();
        } else {
            var isPresent = false;
            Alert alert = null;
            try {
                alert = switchTo().alert();
                isPresent = true;
            } catch (AlertNotFoundError ignored) {
            }

            assertThat(isPresent)
                    .as("Alert should be visible")
                    .isTrue();

            if (alert != null) {
                alert.accept();
            }
        }
    }

    @DataProvider(name = "alerts", parallel = true)
    public Object[][] alerts() {
        return new Object[][]{
                {AlertType.SIMPLE},
                {AlertType.CONFIRM},
                {AlertType.PROMPT},
                {AlertType.MODERN}
        };
    }

    enum AlertType {
        SIMPLE,
        CONFIRM,
        PROMPT,
        MODERN
    }
}
