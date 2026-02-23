package org.lando.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SetUp {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver(String browser, boolean isSeleniumGridEnabled) {

        if (driver.get() == null) {

            if (isSeleniumGridEnabled)
                driver.set(createRemoteDriver(browser));
            else
                driver.set(createLocalDriver(browser));
        }

        return driver.get();
    }

    public static WebDriver getCurrentDriver() {
        return driver.get();
    }

    private WebDriver createRemoteDriver(String browser) {
        Capabilities capabilities = null;
        String ip = System.getenv("GRID_HUB_HOST");
        String gridHubHost = "http://" + ip + ":4444/wd/hub";

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            capabilities = options;
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            capabilities = options;
        }

        try {
            return new RemoteWebDriver(new URL(gridHubHost), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL malformed", e);
        }
    }

    private WebDriver createLocalDriver(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");

            driver.set(new ChromeDriver(options));
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");

            driver.set(new FirefoxDriver(options));
        }

        driver.get().manage().window().maximize();

        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}