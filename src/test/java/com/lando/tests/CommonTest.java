package com.lando.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class CommonTest {
    WebDriver driver;
    LoginPage loginPage;

    public LoginPage init(String browser) {

        SetUp setUp = new SetUp();
        driver = setUp.getDriver(browser,
                Boolean.parseBoolean(System.getenv("SELENIUM_GRID_ENABLED")));

        loginPage = new LoginPage(driver);
        return loginPage;
    }

    public void navigate() {
        loginPage.navigateToSauceLab();
    }

    public void successfulLogin() {
        loginPage.writeCredentials("standard_user", "secret_sauce");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.getValidLoginElements().get("cart_icon").isDisplayed());
        Assert.assertTrue(loginPage.getValidLoginElements().get("drop_down").isDisplayed());
    }
}