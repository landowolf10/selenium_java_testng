package org.lando.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.lando.utils.BasePage;
import java.util.HashMap;

import static org.lando.locators.CheckoutLocators.*;
import static org.lando.locators.CheckoutLocators.finishButton;
import static org.lando.locators.DashboardLocators.*;

public class CheckoutPage extends BasePage {
    private final DashboardPage dashboardPage;

    public CheckoutPage(WebDriver driver, DashboardPage dashboardPage) {
        super(driver);
        this.dashboardPage = dashboardPage;
    }

    public void proceedWithCheckout() {
        clickElement(By.xpath(cartIcon), 10);
        clickElement(By.id(checkoutButton), 10);
        writeText(By.id(txtFirstName), "Orlando", 10);
        writeText(By.id(txtLastName), "Avila", 10);
        writeText(By.id(txtZipCode), "40880", 10);
        clickElement(By.id(continueButton), 10);
    }

    public String getSubtotal() {
        return getElementText(By.xpath(subtotal), 10);
    }

    public HashMap<String, Boolean> getCheckoutElements() {
        HashMap<String, Boolean> presentElements = new HashMap<>();

        try {
            presentElements.put("order_title", elementIsDisplayed(By.xpath(orderTitle), 10));
            presentElements.put("order_message", elementIsDisplayed(By.xpath(orderMessage), 10));
            presentElements.put("home_button", elementIsDisplayed(By.xpath(backToHomeButton), 10));
        }
        catch (TimeoutException e) {
            presentElements.put("order_title", false);
            presentElements.put("order_message", false);
            presentElements.put("home_button", false);
            e.printStackTrace();
        }

        return presentElements;
    }

    public String getItemsSum() {
        float subTotal = 0;

        for (Float selectedItemPrice : dashboardPage.getSelectedItemPrices()) subTotal += selectedItemPrice;

        System.out.println("Subtotal: " + subTotal);

        return "Item total: $" + subTotal;
    }

    public void clickFinishButton() {
        dashboardPage.clearSelectedItems();
        clickElement(By.id(finishButton), 10);
    }
}
