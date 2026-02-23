package com.lando.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.lando.pages.CheckoutPage;
import org.lando.pages.DashboardPage;
import org.lando.pages.LoginPage;
import org.lando.utils.SetUp;

public class CheckoutTest {

    CommonTest commonTest;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {

        System.out.println("Thread ID: " + Thread.currentThread().getId());

        commonTest = new CommonTest();
        loginPage = commonTest.init(browser);

        dashboardPage = new DashboardPage(SetUp.getCurrentDriver());
        checkoutPage = new CheckoutPage(SetUp.getCurrentDriver(), dashboardPage);

        commonTest.navigate();
        commonTest.successfulLogin();
    }

    @Test
    public void checkoutTest() {
        dashboardPage.sortDropdown();
        dashboardPage.addProduct();
        dashboardPage.addProduct();

        checkoutPage.proceedWithCheckout();

        Assert.assertEquals(
                checkoutPage.getItemsSum(),
                checkoutPage.getSubtotal()
        );

        checkoutPage.clickFinishButton();

        Assert.assertTrue(checkoutPage.getCheckoutElements().get("order_title"));
        Assert.assertTrue(checkoutPage.getCheckoutElements().get("order_message"));
        Assert.assertTrue(checkoutPage.getCheckoutElements().get("home_button"));
    }

    @AfterMethod
    public void tearDown() {
        SetUp.quitDriver();
    }
}