package com.jbaxenom.laget.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author jbaxenom on 3/1/15
 */
public enum Configuration {

    devAPIEndpoint("devAPIEndpoint"),
    integrationAPIEndpoint("integrationAPIEndpoint"),
    stagingAPIEndpoint("stagingAPIEndpoint"),
    testdriveAPIEndpoint("testdriveAPIEndpoint"),
    productionAPIEndpoint("productionAPIEndpoint"),

    devWebUrl("devWebUrl"),
    integrationWebUrl("integrationWebUrl"),
    stagingWebUrl("stagingWebUrl"),
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

    apiUsername("APIUsername"),
    apiPassword("APIPassword"),
    apiSpecialHeaderName("APISpecialHeaderName"),
    apiSpecialHeaderContent("APISpecialHeaderContent"),

    sqlDriver("SQLDriver"),
    sqlDatabaseUrl("SQLDatabaseUrl"),
    sqlUsername("SQLUsername"),
    sqlPassword("SQLPassword"),

    appiumGridUrl("appiumGridUrl"),
    platformName("platformName"),
    platformVersion("platformVersion"),
    deviceName("deviceName"),
    deviceOrientation("deviceOrientation"),
    androidAppPackage("androidAppPackage"),
    androidAppActivity("androidAppActivity"),
    mobileApp("app"),
    appiumVersion("appiumVersion");

    private final String key;
    private final static String CONFIG_FILE = "environment.properties";
    private final static Map<Configuration, String> configuration = new EnumMap<>(Configuration.class);

    Configuration(String key) {
        this.key = key;
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
        if ((System.getenv("ENVIRONMENT") == null) && (environment.get().equals(""))) {
            throw new UnsupportedOperationException("An environment has not been specified. Please specify it either" +
                    " in ENVIRONMENT env variable or in environment.properties file");
        } else {
            return (System.getenv("ENVIRONMENT") != null)
                    ? Environment.fromString(System.getenv("ENVIRONMENT"))
                    : Environment.fromString(environment.get());
        }
    }

    /**
     * @return the Browser where tests will be run, set in either environmental variable or the properties file
     * (variable takes preference)
     */
    public static Browser getBrowser() {
        if (System.getenv("BROWSER") == null && webDriverBrowser.get().equals("")) {
            throw new UnsupportedOperationException("A browser has not been specified. Please specify it either" +
                    " in BROWSER env variable or in environment.properties file");
        } else {
            return (System.getenv("BROWSER") != null)
                    ? Browser.fromString(System.getenv("BROWSER"))
                    : Browser.fromString(webDriverBrowser.get());
        }
    }

    public static String getBrowserVersion() { // Browser version is an optional parameter so we default to ""
        return (System.getenv("BROWSER_VERSION") != null)
                ? System.getenv("BROWSER_VERSION")
                : webDriverBrowserVersion.get();
    }

    public static String getOS() {
        if (System.getenv("OS") != null) {    // Windows 8.1 already has a OS variable that defaults to Windows_NT for some reason
            switch (System.getenv("OS")) {
                case "Windows_NT":
                    if (webDriverOS.get().equals("")) {
                        return "Windows 8.1";
                    } else {
                        return webDriverOS.get();
                    }
                default:
                    return System.getenv("OS");
            }
        } else if (webDriverOS.get().equals("")) {
            throw new UnsupportedOperationException("A OS has not been specified. Please specify it either" +
                    " in OS env variable or in environment.properties file");
        } else {
            return webDriverOS.get();
        }
    }

    /**
     * @return the Selenium Grid HUB URL where tests will be run, set in either environmental variable or the properties
     * file (variable takes preference)
     */
    public static String getGridUrl() {
        if (System.getenv("GRID") == null && webDriverGridUrl.get().equals("")) {
            throw new UnsupportedOperationException("A grid mode has not been specified. Please specify it either" +
                    " in GRID env variable or in environment.properties file");
        } else {
            return (System.getenv("GRID") != null)
                    ? System.getenv("GRID")
                    : webDriverGridUrl.get();
        }
    }

    /**
     * @return the Selenium Grid HUB URL where tests will be run, set in either environmental variable or the properties
     * file (variable takes preference)
     */
    public static String getAppPath() {
        if (System.getenv("APP") == null && mobileApp.get().equals("")) {
            throw new UnsupportedOperationException("An app path has not been specified. Please specify it either" +
                    " in APP env variable or in environment.properties file");
        } else {
            return (System.getenv("APP") != null)
                    ? System.getenv("APP")
                    : mobileApp.get();
        }
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
            configuration.put(c, "");
        }
    }

}
