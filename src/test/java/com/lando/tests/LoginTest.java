package com.lando.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class LoginTest {
    CommonTest commonTest;
    LoginPage loginPage;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        //System.out.println("Thread ID: " + Thread.currentThread().getId());

        commonTest = new CommonTest();
        loginPage = commonTest.init(browser);
        commonTest.navigate();
    }

    @Test
    public void successfulLoginTest() {
        commonTest.successfulLogin();
    }

    @Test()
    public void invalidLoginTest() {
        loginPage.writeCredentials("standard_use", "secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getInvalidLoginElements().get("login_button"));
        Assert.assertTrue(loginPage.getInvalidLoginElements().get("error_message"));
        Assert.assertEquals(loginPage.getErrorMessageText(), "Epic sadface: Username and password do not match " +
                "any user in this service");
    }

    @AfterMethod
    public void tearDown() {
        SetUp.quitDriver();
    }
}
