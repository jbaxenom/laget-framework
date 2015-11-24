package com.jbaxenom.laget.configuration;

/**
 * Created by jbaxenom on 2014-06-25.
 */
public enum Browser {

    FIREFOX,
    CHROME,
    SAFARI,
    IE32,
    IE64,
    PHANTOMJS;

    /**
     * @param browser
     * @return the Environment enum with {@code environment} as name
     */
    public static Browser fromString(String browser) {
        if (browser.toString().equalsIgnoreCase("ie")) {
            return Browser.IE64;
        } else {
            for (Browser s : Browser.values()) {
                if (s.toString().equalsIgnoreCase(browser)) {
                    return s;
                }
            }
        }
        throw new NullPointerException(
                "Unknown browser '" + browser + "': choose between FIREFOX, CHROME, SAFARI, IE or PHANTOMJS"
        );
    }

    @Override
    public String toString() {
        switch (this) {
            case IE32:
            case IE64:
                return "internet explorer";
            case FIREFOX:
                return "firefox";
            case CHROME:
                return "chrome";
            case SAFARI:
                return "safari";
            case PHANTOMJS:
                return "phantomjs";
            default:
                throw new RuntimeException("Browser not supported");
        }
    }

}
