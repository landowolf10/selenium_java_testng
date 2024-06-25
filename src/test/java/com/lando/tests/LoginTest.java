package com.lando.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class LoginTest {
    CommonTest commonTest = new CommonTest();
    LoginPage loginPage;
    String selectedBrowser;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        selectedBrowser = browser;
        loginPage = commonTest.getLoginPage(browser);
        commonTest.navigate(browser);
    }

    @Test
    public void successfulLoginTest() {
        commonTest.successfulLogin(selectedBrowser);
    }

    @Test(dependsOnMethods = "successfulLoginTest")
    public void invalidLoginTest() {
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
