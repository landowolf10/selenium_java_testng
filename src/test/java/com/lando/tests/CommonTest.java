package com.lando.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class CommonTest {
    WebDriver driver;
    LoginPage loginPage;

    public WebDriver setUp(String browser) {
        SetUp setUp = new SetUp();

        System.out.println("SELENIUM_GRID_ENABLED: " + System.getenv("SELENIUM_GRID_ENABLED"));
        return setUp.getDriver(browser, Boolean.parseBoolean(System.getenv("SELENIUM_GRID_ENABLED")));
    }



    public LoginPage getLoginPage(String browser) {
        driver = setUp(browser);
        loginPage = new LoginPage(driver);

        return loginPage;
    }

    public void navigate(String browser) {
        driver = setUp(browser);
        loginPage = new LoginPage(driver);
        loginPage.navigateToSauceLab();
    }

    public void successfulLogin(String browser) {
        getLoginPage(browser).writeCredentials("standard_user", "secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getValidLoginElements().get("cart_icon").isDisplayed());
        Assert.assertTrue(loginPage.getValidLoginElements().get("drop_down").isDisplayed());
    }
}
