package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.SetUp;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeTest
    @Parameters("browser")
    public void setUp(String browser) {
        SetUp setUp = new SetUp();
        driver = setUp.getDriver(browser, false);
        loginPage = new LoginPage(driver);
        loginPage.navigateToSauceLab();
    }

    @Test
    public void successfulLoginTest() {
        loginPage.writeCredentials("standard_user", "secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getValidLoginElements().get("cart_icon").isDisplayed());
        Assert.assertTrue(loginPage.getValidLoginElements().get("drop_down").isDisplayed());
    }

    @Test(dependsOnMethods = "successfulLoginTest")
    public void invalidLoginTest() {
        loginPage.navigateToSauceLab();
        loginPage.writeCredentials("standard_use", "secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getInvalidLoginElements().get("login_button"));
        Assert.assertTrue(loginPage.getInvalidLoginElements().get("error_message"));
        Assert.assertEquals(loginPage.getErrorMessageText(), "Epic sadface: Username and password do not match " +
                "any user in this service");
    }

    @AfterClass
    public void tearDown() {
        SetUp.quitDriver();
    }
}
