package com.jbaxenom.laget.interactions.webdriver;

import com.jbaxenom.laget.configuration.Browser;
import com.jbaxenom.laget.configuration.Configuration;
import com.saucelabs.common.SauceOnDemandAuthentication;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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
 * @author jbaxenom
 */
public class WebDriverBuilder {

    private Browser browser;
    private String version;
    private String os;
    private boolean useGrid = false;
    private URL gridUrl;
    private String app;
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
                case "NO_GRID":
                    break;
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

    public WebDriverBuilder withApp(String app) {
        this.app = app;
        return this;
    }

    public WebDriver build() {

        RemoteWebDriver driver;

        if (useGrid) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);

            if (version != null) {
                capabilities.setCapability(CapabilityType.VERSION, version);
            }

            if (os != null) {
                capabilities.setCapability(CapabilityType.PLATFORM, os);
            }

            capabilities.setCapability("name", System.getProperty("SCENARIO_NAME"));

            driver = new RemoteWebDriver(gridUrl, capabilities);
            this.sessionId.set(driver.getSessionId().toString());

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
                default:
                    throw new UnsupportedOperationException("The browser '" + browser + "' is not supported yet.");
            }
        }

        setDriverTimeout(driver);
        return driver;
    }

    public AppiumDriver buildMobile() {

        AppiumDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        String platformName = Configuration.platformName.get();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Configuration.platformVersion.get());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Configuration.deviceName.get());
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, Configuration.appiumVersion.get());

        capabilities.setCapability("deviceOrientation", Configuration.deviceOrientation.get());

        if (app != null) {  // if app specified browser needs to be ""
            capabilities.setCapability(MobileCapabilityType.APP, app);
        } else if (platformName.equals("iOS")) {
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        } else {
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
        }

        capabilities.setCapability("name", System.getProperty("SCENARIO_NAME"));

        switch (platformName) {
            case "iOS":
                driver = new IOSDriver(gridUrl, capabilities);
                break;
            case "Android":
                capabilities.setCapability("appPackage", Configuration.androidAppPackage.get());
                capabilities.setCapability("appActivity", Configuration.androidAppActivity.get());
                driver = new AndroidDriver(gridUrl, capabilities);
                break;
            default:
                throw new UnsupportedOperationException("The platform '" + platformName + "' is not supported");
        }

        this.sessionId.set(driver.getSessionId().toString());

        System.setProperty("SESSION_ID", this.getSessionId());

        setDriverTimeout(driver);

        return driver;
    }

    public String getSessionId() {
        return this.sessionId.get();
    }

    private void setDriverTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(
                Configuration.webDriverImplicitTimeout.getInt(),
                TimeUnit.MILLISECONDS
        );
    }

}
