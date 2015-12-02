package com.jbaxenom.laget.configuration;

/**
 * @author jbaxenom on 6/25/14.
 */
public enum Environment {

    DEV(
            Configuration.devAPIEndpoint.get(),
            Configuration.devWebUrl.get()
    ),
    INTEGRATION(
            Configuration.integrationAPIEndpoint.get(),
            Configuration.integrationWebUrl.get()
    ),
    STAGING(
            Configuration.stagingAPIEndpoint.get(),
            Configuration.stagingWebUrl.get()
    ),
    TESTDRIVE(
            Configuration.testdriveAPIEndpoint.get(),
            Configuration.testdriveWebUrl.get()
    ),
    PRODUCTION(
            Configuration.productionAPIEndpoint.get(),
            Configuration.productionWebUrl.get()
    );

    private final String apiEndpoint;
    private final String appURL;

    private Environment(String apiEndpoint, String appURL) {
        this.apiEndpoint = apiEndpoint;
        this.appURL = appURL;
    }

    /**
     * @param environment
     * @return the Environment enum with {@code environment} as name
     */
    public static Environment fromString(String environment) {
        for (Environment s : Environment.values()) {
            if (s.toString().equals(environment)) {
                return s;
            }
        }
        throw new NullPointerException(
                "Unknown environment, choose between DEV, INTEGRATION, STAGING, TESTDRIVE or PRODUCTION"
        );
    }

    /**
     * @return the API apiEndpoint associated to the environment
     */
    public String apiEndpoint() {
        return apiEndpoint;
    }

    public String appURL() {
        return appURL;
    }

}
