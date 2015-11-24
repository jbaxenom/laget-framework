package com.jbaxenom.laget.webdriver;

import com.jbaxenom.laget.configuration.Browser;
import com.jbaxenom.laget.configuration.Configuration;
import com.saucelabs.common.SauceOnDemandAuthentication;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by jbaxenom
 */
public class WebDriverBuilder {

    private Browser browser = Browser.FIREFOX;
    private String version;
    private String os;
    private boolean useGrid = false;
    private URL gridUrl;
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    public SauceOnDemandAuthentication authentication =
            new SauceOnDemandAuthentication(Configuration.sauceLabsUser.get(), Configuration.sauceLabsKey.get());

    public WebDriverBuilder withBrowser(Browser browser) {
        this.browser = browser;
        return this;
    }

    public WebDriverBuilder withGridUrl(String gridUrl) {
        try {
            switch (gridUrl) {
                case "LOCAL_GRID":
                    this.gridUrl = new URL("http://localhost:4444/wd/hub");
                    useGrid = true;
                    break;
                case "LOCAL_APPIUM":
                    this.gridUrl = new URL("http://localhost:4723/wd/hub");
                    useGrid = true;
                    break;
                case "SAUCE":
                    this.gridUrl = new URL(
                            "http://" + authentication.getUsername() + ":" + authentication.getAccessKey()
                                    + "@ondemand.saucelabs.com:80/wd/hub"
                    );
                    useGrid = true;
                    break;
                case "NO_GRID":
                    break;
                default:
                    this.gridUrl = new URL(gridUrl);
                    useGrid = true;
                    break;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Your grid choice or URL is invalid");
        }
        return this;
    }

    public WebDriverBuilder withOS(String os) {
        this.os = os;
        return this;
    }

    public WebDriverBuilder withBrowserVersion(String version) {
        this.version = version;
        return this;
    }

    public WebDriver build() {

        WebDriver driver = null;
        if (useGrid) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);

            if (gridUrl.equals("LOCAL_APPIUM")) {
                capabilities.setCapability("platformName", Configuration.platformName.get());
                capabilities.setCapability("deviceName", Configuration.deviceName.get());
            }

            if (version != null) {
                capabilities.setCapability(CapabilityType.VERSION, version);
            }

            if (os != null) {
                capabilities.setCapability(CapabilityType.PLATFORM, os);
            }

            capabilities.setCapability("name", System.getProperty("SCENARIO_NAME"));

            driver = new RemoteWebDriver(gridUrl, capabilities);
            this.sessionId.set(((RemoteWebDriver) driver).getSessionId().toString());

            System.setProperty("SESSION_ID", this.getSessionId());
        } else {
            switch (browser) {
                case CHROME:
                    System.setProperty("webdriver.chrome.driver", Configuration.webDriverChromeDriverBinPath.get());
                    driver = new ChromeDriver();
                    break;
                case SAFARI:
                    driver = new SafariDriver();
                    break;
                case IE64:
                    System.setProperty("webdriver.ie.driver", Configuration.webDriverIEDriver64BinPath.get());
                    driver = new InternetExplorerDriver();
                    break;
                case IE32:
                    System.setProperty("webdriver.ie.driver", Configuration.webDriverIEDriver32BinPath.get());
                    driver = new InternetExplorerDriver();
                    break;
                case PHANTOMJS:
                    System.setProperty("phantomjs.binary.path", Configuration.webDriverPhantomJSBinPath.get());
                    driver = new PhantomJSDriver();
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver();
                    break;
            }
        }

        driver.manage().timeouts().implicitlyWait(
                Configuration.webDriverImplicitTimeout.getInt(),
                TimeUnit.MILLISECONDS
        );

        return driver;
    }

    public String getSessionId() {
        return this.sessionId.get();
    }

}
