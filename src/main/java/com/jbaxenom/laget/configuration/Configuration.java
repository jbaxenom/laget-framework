package com.jbaxenom.laget.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jbaxenom on 03/01/15
 */
public enum Configuration {

    devAPIEndpoint("devAPIEndpoint"),
    integrationAPIEndpoint("integrationAPIEndpoint"),
    stagingAPIEndpoint("stagingAPIEndpoint"),
    perftestAPIEndpoint("perftestAPIEndpoint"),
    testdriveAPIEndpoint("testdriveAPIEndpoint"),
    productionAPIEndpoint("productionAPIEndpoint"),

    devWebUrl("devWebUrl"),
    integrationWebUrl("integrationWebUrl"),
    stagingWebUrl("stagingWebUrl"),
    perftestWebUrl("perftestWebUrl"),
    testdriveWebUrl("testdriveWebUrl"),
    productionWebUrl("productionWebUrl"),

    environment("environment"),

    webDriverGridUrl("webDriverGridUrl"),
    webDriverBrowser("webDriverBrowser"),
    webDriverBrowserVersion("webDriverBrowserVersion"),
    webDriverOS("webDriverOS"),
    webDriverImplicitTimeout("webDriverImplicitTimeout"),
    webDriverChromeDriverBinPath("webDriverChromeDriverBinPath"),
    webDriverIEDriver64BinPath("webDriverIEDriver64BinPath"),
    webDriverIEDriver32BinPath("webDriverIEDriver32BinPath"),
    webDriverPhantomJSBinPath("webDriverPhantomJSBinPath"),

    sauceLabsUser("sauceLabsUser"),
    sauceLabsKey("sauceLabsKey"),

    appiumGridUrl("appiumGridUrl"),
    platformName("platformName"),
    deviceName("deviceName"),
    androidAppPath("androidAppPath"),
    androidAppName("androidAppName"),
    androidAppPackage("androidAppPackage"),
    androidAppActivity("androidAppActivity"),
    iosAppPath("iosAppPath"),
    iosAppName("iosAppName");


    private String defaultValue;
    private final String key;
    private final static String CONFIG_FILE = "environment.properties";
    private final static Map<Configuration, String> configuration = new EnumMap<>(Configuration.class);

    Configuration(String key) {
        this(key, "N/A");
    }

    Configuration(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    static {
        readConfigurationFrom(CONFIG_FILE);
    }

    /**
     * @return the String property corresponding to the key, or null if not found
     */
    public String get() {
        return configuration.get(this);
    }

    /**
     * @return the Integer property corresponding to the key, or null if not found
     */
    public long getInt() {
        return Integer.parseInt(configuration.get(this));
    }

    /**
     * @return the Environment where tests will be run, set in either environmental variable or the properties file
     * (variable takes preference)
     */
    public static Environment getEnvironment() {
        return (System.getenv("ENVIRONMENT") != null)
                ? Environment.fromString(System.getenv("ENVIRONMENT"))
                : Environment.fromString(environment.get());
    }

    /**
     * @return the Browser where tests will be run, set in either environmental variable or the properties file
     * (variable takes preference)
     */
    public static Browser getBrowser() {
        return (System.getenv("BROWSER") != null)
                ? Browser.fromString(System.getenv("BROWSER"))
                : Browser.fromString(webDriverBrowser.get());
    }

    public static String getBrowserVersion() {
        return (System.getenv("BROWSER_VERSION") != null)
                ? System.getenv("BROWSER_VERSION")
                : webDriverBrowserVersion.get();
    }

    public static String getOS() {
        return (System.getenv("OS") != null) ? System.getenv("OS") : webDriverOS.get();
    }

    /**
     * @return the Selenium Grid HUB URL where tests will be run, set in either environmental variable or the properties
     * file (variable takes preference)
     */
    public static String getGridUrl() {
        return (System.getenv("GRID") != null)
                ? System.getenv("GRID")
                : webDriverGridUrl.get();
    }

    private static void readConfigurationFrom(String fileName) {

        try {
            InputStream resource = Configuration.class.getClassLoader().getResourceAsStream(fileName);
            Properties properties = new Properties();
            properties.load(resource); //throws a NPE if resource not founds
            for (String key : properties.stringPropertyNames()) {
                configuration.put(getConfigurationKey(key), properties.getProperty(key));
            }
        } catch (IllegalArgumentException | IOException | NullPointerException e) {
            populateDefaultValues();
        }
    }

    private static Configuration getConfigurationKey(String key) {
        for (Configuration config : values()) {
            if (config.key.equals(key)) {
                return config;
            }
        }
        throw new IllegalArgumentException("Not a valid property key: " + key);
    }

    private static void populateDefaultValues() {
        for (Configuration c : values()) {
            configuration.put(c, c.defaultValue);
        }
    }

}
