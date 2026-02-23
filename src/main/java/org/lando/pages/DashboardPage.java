package org.lando.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.lando.utils.BasePage;

import java.util.ArrayList;
import java.util.List;

import static org.lando.locators.DashboardLocators.*;

public class DashboardPage extends BasePage {
    private final ThreadLocal<List<Float>> selectedItemPrices =
            ThreadLocal.withInitial(ArrayList::new);

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void sortDropdown() {
        selectFromDropDownByText(By.xpath(sortDropDown), "Price (high to low)", 10);
    }

    public void addProduct() {
        List<WebElement> addToCartButtons = getAllElementsBy(By.xpath(addToCartButton));
        List<Float> prices = getPrices();

        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i) < 20)
            {
                clickElementFromList(addToCartButtons.get(i));
                selectedItemPrices.get().add(prices.get(i));
                prices.remove(i);
                addToCartButtons.remove(i);
                break;
            }
        }
    }

    private List<Float> getPrices() {
        List<WebElement> priceElements = getAllElementsBy(By.xpath(productPrice));
        List<Float> prices = new ArrayList<>();

        for (WebElement element : priceElements) {
            prices.add(Float.parseFloat(element.getText().substring(1)));
        }

        return prices;
    }

    public List<Float> getSelectedItemPrices() {
        System.out.println("selectedItemPrices: " + selectedItemPrices.get());

        return selectedItemPrices.get();
    }

    public void clearSelectedItems() {
        selectedItemPrices.get().clear();
        selectedItemPrices.remove();
    }
}
