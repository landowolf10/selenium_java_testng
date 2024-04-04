package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SetUp {
    WebDriver driver;
    private static boolean driverInstanceExists = false;
    private static WebDriver driverInstance = null;
    private final String os = System.getProperty("os.name");

    public WebDriver getDriver(String browser, boolean isSeleniumGridEnabled) {
        if (driverInstanceExists)
            driver = driverInstance;
        else
            if (isSeleniumGridEnabled)
                driver = createRemoteDriver(browser);
            else
                driver = createDriver(browser);

        driverInstanceExists = true;
        driverInstance = driver;

        return driver;
    }

    private WebDriver createRemoteDriver(String browser) {
        Capabilities capabilities = null;

        System.out.println("OS: " + os);

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            //chromeOptions.addArguments("--remote-allow-origins=*");
            //chromeOptions.addArguments("--headless=new");
            capabilities = new ChromeOptions();
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            //firefoxOptions.addArguments("--headless");
            capabilities = new FirefoxOptions();
        }

        try {
            assert capabilities != null;
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private WebDriver createDriver(String browser)
    {
        System.out.println("OS: " + os);

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless");
            driver = new FirefoxDriver(firefoxOptions);
        }

        driver.manage().window().maximize();

        return driver;
    }

    public static void quitDriver() {
        WebDriver currentDriver;

        if (driverInstanceExists)
        {
            currentDriver = driverInstance;
            currentDriver.quit();
        }

        driverInstanceExists = false;
        driverInstance = null;
    }
}
