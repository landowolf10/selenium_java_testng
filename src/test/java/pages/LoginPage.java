package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BasePage;
import utils.ConstantData;

import java.util.HashMap;

import static locators.DashboardLocators.cartIcon;
import static locators.DashboardLocators.sortDropDown;
import static locators.LoginLocators.*;
import static locators.LoginLocators.errorMessage;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToSauceLab() {
        navigateTo(ConstantData.URL);
    }

    public void clickLoginButton() {
        clickElement(By.xpath(loginButton), 10);
    }

    public void writeCredentials(String email, String password) {
        writeText(By.xpath(userTextbox), email, 10);
        writeText(By.xpath(passwordTextbox), password, 10);
    }

    public HashMap<String, WebElement> getValidLoginElements() {
        HashMap<String, WebElement> presentElements = new HashMap<>();

        presentElements.put("cart_icon", getElementBy(By.xpath(cartIcon), 10));
        presentElements.put("drop_down", getElementBy(By.xpath(sortDropDown), 10));

        return presentElements;
    }

    public HashMap<String, Boolean> getInvalidLoginElements()
    {
        HashMap<String, Boolean> presentElements = new HashMap<>();

        presentElements.put("login_button", elementIsDisplayed(By.xpath(loginButton), 10));
        presentElements.put("error_message", elementIsDisplayed(By.xpath(errorMessage), 10));

        return presentElements;
    }


    public String getErrorMessageText() {
        return getElementText(By.xpath(errorMessage), 10);
    }
}
