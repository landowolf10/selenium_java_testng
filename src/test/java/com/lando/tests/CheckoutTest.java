package com.lando.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.lando.pages.CheckoutPage;
import org.lando.pages.DashboardPage;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class CheckoutTest {
    WebDriver driver;
    CommonTest commonTest = new CommonTest();
    LoginPage loginPage;
    DashboardPage dashboardPage;
    CheckoutPage checkoutPage;
    String selectedBrowser;

    @BeforeTest
    @Parameters("browser")
    public void setUp(String browser) {
        driver = commonTest.setUp(browser);
        selectedBrowser = browser;
        loginPage = commonTest.getLoginPage(browser);
        dashboardPage = new DashboardPage(driver);
        checkoutPage = new CheckoutPage(driver);
        commonTest.navigate(browser);
    }

    @Test
    public void successfulLoginTest() {
        commonTest.successfulLogin(selectedBrowser);
    }

    @Test(dependsOnMethods = "successfulLoginTest")
    public void checkoutTest() {
        dashboardPage.sortDropdown();
        dashboardPage.addProduct();
        dashboardPage.addProduct();
        checkoutPage.proceedWithCheckout();
        Assert.assertEquals(checkoutPage.getItemsSum(), checkoutPage.getSubtotal());
        checkoutPage.clickFinishButton();
    }

    @Test(dependsOnMethods = "checkoutTest")
    public void finishCheckoutTest() {
        Assert.assertTrue(checkoutPage.getCheckoutElements().get("order_title"));
        Assert.assertTrue(checkoutPage.getCheckoutElements().get("order_message"));
        Assert.assertTrue(checkoutPage.getCheckoutElements().get("home_button"));
    }

    @AfterClass
    public void tearDown() {
        SetUp.quitDriver();
    }
}
