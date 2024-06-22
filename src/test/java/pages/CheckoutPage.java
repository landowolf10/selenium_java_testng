package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import utils.BasePage;
import java.util.HashMap;

import static locators.CheckoutLocators.*;
import static locators.CheckoutLocators.finishButton;
import static locators.DashboardLocators.*;
import static pages.DashboardPage.getSelectedItemAccess;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void proceedWithCheckout() {
        clickElement(By.xpath(cartIcon), 10);
        clickElement(By.xpath(checkoutButton), 10);
        writeText(By.xpath(txtFirstName), "Orlando", 10);
        writeText(By.xpath(txtLastName), "Avila", 10);
        writeText(By.xpath(txtZipCode), "40880", 10);
        clickElement(By.xpath(continueButton), 10);
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

        for (Float selectedItemPrice : getSelectedItemAccess()) subTotal += selectedItemPrice;

        return "Item total: $" + subTotal;
    }

    public void clickFinishButton() {
        getSelectedItemAccess().clear();
        clickElement(By.xpath(finishButton), 10);
    }
}
