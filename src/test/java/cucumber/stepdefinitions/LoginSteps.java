package cucumber.stepdefinitions;

import com.automation.pages.zero.ZeroLandingPage;
import com.automation.pages.zero.ZeroLoginPage;
import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private ZeroLandingPage zeroLandingPage;
    private ZeroLoginPage zeroLoginPage;

    @Before
    public void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = Duration.ofSeconds(90).toMillis();
    }

    @Step("Open landing page")
    @Given("the user is on the landing page")
    public void userIsOnLandingPage() {
        zeroLandingPage = open(
                "http://zero.webappsecurity.com/index.html", ZeroLandingPage.class);

    }

    @When("the user click sign in")
    public void clickSignIn() {
        zeroLoginPage = zeroLandingPage.clickSignInButton();
    }

    @Step("Verify user is redirected to the login page")
    @Then("the user should be redirected to the login page")
    public void userIsRedirectedToLoginPage() {
        assertThat(url()).contains("/login.html");
    }

    @Step("Enter username {username} and password {password}")
    @When("the user enters {string} and {string}")
    public void userEntersCredentials(String username, String password) {
        zeroLoginPage.setLogin(username);
        zeroLoginPage.setPassword(password);
    }

    @Step("Click login button")
    @And("clicks the login button")
    public void userClicksLoginButton() {
        zeroLoginPage.clickSignIn();
    }

    @Step("Verify user is redirected to my account")
    @Then("the user should be redirected to the my account")
    public void userIsRedirectedToHomepage() {
        $("#myacc").shouldBe(visible);
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }
}
