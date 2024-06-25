package org.lando.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    private static Actions action;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public WebElement getElementBy(By elementLocator, int maxWaitSec)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSec));

        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public List<WebElement> getAllElementsBy(By elementLocator)
    {
        return driver.findElements(elementLocator);
    }

    public void goToLinkText(String linkText) {
        driver.findElement(By.linkText(linkText));
    }

    public void clickElement(By elementLocator, int maxWaitSec) {
        getElementBy(elementLocator, maxWaitSec).click();
    }

    public void clickElementFromList(WebElement element)
    {
        element.click();
    }

    public void writeText(By elementLocator, String text, int maxWaitSec) {
        getElementBy(elementLocator, maxWaitSec).clear();
        getElementBy(elementLocator, maxWaitSec).sendKeys(text);
    }

    public String getElementText(By elementLocator, int maxWaitSec) {
        return getElementBy(elementLocator, maxWaitSec).getText();
    }

    public void selectFromDropDownByValue(By elementLocator, String valueToSelect, int maxWaitSec) {
        Select dropdown = new Select(getElementBy(elementLocator, maxWaitSec));
        dropdown.selectByValue(valueToSelect);
    }

    public void selectFromDropDownByIndex(By elementLocator, int valueToSelect, int maxWaitSec) {
        Select dropdown = new Select(getElementBy(elementLocator, maxWaitSec));
        dropdown.selectByIndex(valueToSelect);
    }

    public void selectFromDropDownByText(By elementLocator, String valueToSelect, int maxWaitSec) {
        Select dropdown = new Select(getElementBy(elementLocator, maxWaitSec));
        dropdown.selectByVisibleText(valueToSelect);
    }

    public void hoverOverElement(By elementLocator, int maxWaitSec) {
        action.moveToElement(getElementBy(elementLocator, maxWaitSec));
    }

    public void doubleClick(By elementLocator, int maxWaitSec) {
        action.doubleClick(getElementBy(elementLocator, maxWaitSec));
    }

    public void rightClick(By elementLocator, int maxWaitSec) {
        action.contextClick(getElementBy(elementLocator, maxWaitSec));
    }

    public String getValueFromTable(By elementLocator, int row, int column, int maxWaitSec) {
        String cell = elementLocator + "/table/tbody/tr[" + row + "]/td[" + column + "]";

        System.out.print("Cell locator: " + cell);

        return getElementBy(By.xpath(cell), maxWaitSec).getText();
    }

    public void setValueOnTable(By elementLocator, int row, int column, String value, int maxWaitSec) {
        String cell = elementLocator + "/table/tbody/tr[" + row + "]/td[" + column + "]";

        getElementBy(By.xpath(cell), maxWaitSec).sendKeys(value);
    }

    public void switchToiFrame(int iFrameIndex) {
        driver.switchTo().frame(iFrameIndex);
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public void waitUntilElementLocated(By locatorType, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSec));

        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorType));
    }

    public boolean elementIsDisplayed(By locatorType, int maxWaitSec) {
        return getElementBy(locatorType, maxWaitSec).isDisplayed();
    }

    public boolean elementIsSelected(By locatorType, int maxWaitSec) {
        return getElementBy(locatorType, maxWaitSec).isSelected();
    }

    public boolean elementIsEnabled(By locatorType, int maxWaitSec) {
        return getElementBy(locatorType, maxWaitSec).isEnabled();
    }
}
