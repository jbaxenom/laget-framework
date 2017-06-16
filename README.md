# L.A.G.E.T (Layered API and GUI End-to-end Testing) Framework

The "team approach" is one of the pillars of Agile Development, but us testers often do not take it into account when 
creating test frameworks (as I have discussed in some blog posts [here] (https://www.linkedin.com/pulse/what-test-automation-framework-should-building-chema-del-barco) 
and [there] (https://www.linkedin.com/pulse/writing-test-framework-help-team-build-quality-chema-del-barco). For 
instance, most test frameworks are aimed to be used only by testers and thus do not provide an easy way for non-technical 
team members to create test cases, which means they'll have a hard time being able to help. 

The LAGET Framework aims to solve this by providing an adaptive approach for creating test cases, using different 
abstraction layers that adapt to the different technical skillsets that agile team members have and taking special 
consideration towards non-technical users. It is important to understand that LAGET is not a test library but a 
compendium of core settings, methodologies, best practices and tools that will make a great start point to create GUI 
tests for Desktop Web, Mobile Web and Mobile apps as well as API tests for RESTfull webservices.

LAGET defines 3 different but complementary layers: "Behavior", "Domain" and "Interactions", which are explained below. 
Thanks to this adaptative approach, LAGET has successfully been used to teach non-technical testers how to use Cucumber,
Selenium, Appium, Cloud Testing, Continuous Integration and several other tools and methodologies related to Agile
Testing. 

So as good Agile testers we are, give it a try, see if you like it, ask questions and feel free to experiment, change and
improve it!

# Features and Capabilities

LAGET was designed to provide tools to test: 
- Websites in both desktop and mobile browsers,
- Native and Hybrid mobile apps, and
- RESTful webservices

And as most frameworks, it aims to make your testing work a lot easier by giving you a head start in many areas we 
testers always have to cover:

- It supports **parameterized test runs** through both a configuration file (environment.properties) and/or using 
  environmental variables, which allow for easy integration with CI tools.
- It supports both **local and remote test runs** via Selenium Grid. When creating GUI tests the framework will 
  automatically generate the right instance of WebDriver/AppiumDriver depending on the parameters set for the test run.
- As an extension to the previous point, LAGET has out-of-the-box **integration with SauceLabs**, including sending the 
  proper test name and its result. You just need to specify your username and key in the configuration file. 
- It **supports Chrome, Firefox, IE, Safari and PhantomJS** desktop browsers and provides standard configuration for 
  both **iOS and Android apps** with Appium. It also includes parameters to setup the browser servers (whose last 
  versions are included in the repository)
- It understands the concept of **"test environments"**: by setting the different environment's URLs in the 
  environment.properties file, LAGET will automatically point to the ones corresponding to the ENVIRONMENT parameter. 
  This means you can run your tests in different environments just by changing one parameter/environmental variable. 
- It implements the most common **Page Object helpers** for both WebDriver and Appium, which can be used via extending 
  the Abstract pages provided
- It provides an out-of-the-box solution to **run cucumber-jvm parallel tests** and aggregate the reports into one 
  nice-looking HTML
- It provides **test data tools** in the form of entities, actors and actions that can process and parse **JSON data** 
  and **SQL queries**
- It provides the option to **automatically retry failing tests**, in different levels of execution
- It **will (hopefully soon) integrate with JIRA and Zephyr plugin for JIRA** so that your test runs can be fully 
  managed directly from these famous project and test management tools. 


# LAGET Layers

Please go to the [LAGET Layers Explained](https://github.com/jbaxenom/laget-framework/wiki/LAGET-Layers-Explained)
page in the wiki for an extensive explanation about how LAGET can help you learn test automation whilst providing great 
tools for it.


# Package Structure
The structure of the framework is the following:

     e2e_test
        |_src
        |   |_main
        |   |   |_java
        |   |       |_com.jbaxenom.laget
        |   |           |
        |   |           |_aao                               # AAO Layer
        |   |           |   |_actions                       # Example actions
        |   |           |   |_actors                        # Example actors
        |   |           |   |_core                          # LAGET core actions and actors 
        |   |           |       |_actions
        |   |           |       |_actors                               
        |   |           |
        |   |           |_api                               # API Layer
        |   |           |   |_entities                  
        |   |           |   |_json_serializers
        |   |           |   |_message_calls                 # REST messages classes
        |   |           |   |_payloads                      # JSON Payload class
        |   |           |   |_test_data                     # Tools for managing test data in JSON format
        |   |           |       |_string_handling       
        |   |           |                                  
        |   |           |_configuration                     # Core framework static classes for interpreting the
        |   |           |                                   #   environment.properties configuration file
        |   |           |_cucumber                          # Cucumber extensions for better AAO integration and 
        |   |           |                                   #   reporting
        |   |           |_webdriver                         # WebDriver Layer
        |   |               |_pages                         # Page Objects (representation of the GUI interfaces)
        |   |               |   |_AbstractMobilePage.java   # Tools common to all mobile pages
        |   |               |   |_AbstractWebPage.java      # Tools common to all web pages
        |   |               |_WebDriverBuilder.java         # WebDriver initialization based on test run setup     
        |   |_test
        |       |_java
        |       |   |_com.jbaxenom.laget
        |       |       |_PureWebDriverSteps.java           # Cucumber step files for abstracting WebDriver layer
        |       |       |_RunCucumberTest.java              # Cucumber test runner setup
        |       |_resources
        |           |_com.jbaxenom.laget                    # Cucumber features for the different apps
        |           |_environment.properties                # Configuration file for setting the test run properties
        |
        |_build.xml                                         # Ant build to launch concurrent test runs with Selenium Grid 
        |_pom.xml                                           # Maven project setup
        |_README.md                                         # This file
        

# Installation

The project requires `maven` and/or `ant` to run, depending if you want to run parallel tests using Selenium Grid or not,
but having both installed is recommended.

If you want to run your tests locally using a specific web browser you need to have it installed before (D'ough!). 
Additionally, if you want to use Google Chrome, the headless PhantomJS or IE browsers in anything than Windows you will 
need to download their webdriver servers. If you use Windows, these servers are already included in the `lib` folder. 
You can find Google Chrome's webdriver server (chromedriver) 
[here](https://sites.google.com/a/chromium.org/chromedriver/downloads). Just download the latest version and unpack it 
in your preferred folder. PhantomJS can be found [here](http://phantomjs.org/download.html). Their website offers 
installers for all OS's and shows the path to the binary that is required by the test framework. If you need to run 
local IE tests, the latest version of the IE driver server can be downloaded from 
[Selenium's official website](http://www.seleniumhq.org/download/), which also provides instructions for its 
installation. For Chrome and PhantomJS you will need to set the path to the servers/binaries in the respective 
parameter of the `environment.properties` file.

The default values of `environment.properties` will run the tests sequentially and individually in your local 
Firefox browser, in the "test" environment for the respective application.


# Usage

The `environment.properties` file in the `test/resources` package is used to set up the test run. The file explains the
different options available for the different parameters. Additionally you can use environmental variables to set the
most important attributes: `ENVIRONMENT`, `GRID`, `BROWSER`, `BROWSER_VERSION` (only if `GRID=SAUCE`) and `OS` (also 
only if `GRID=SAUCE`).

Here's a short explanation of what these variables do:

## ENVIRONMENT

Sets the test environment where the tests will run. The endpoints need to be set in `environment.properties`. Options:

    DEV             # Local dev environment
    INTEGRATION     # Environment used for verifying the integration of different components of the system
    STAGING         # The Staging environment, normally used for regression tests
    TESTDRIVE       # Testdrives are usually production test environments
    PRODUCTION      # The live environment

## GRID

Selenium GRID allows concurrent, cross-browser testing by creating a farm of cloud clients where to run tests. Options 
supported:

    NO_GRID (or empty) # Runs the test in your local WebDriver browser instead of Selenium Grid (default value)
    LOCAL_GRID         # Use the default "http://localhost:4444/wd/hub" local Grid (which needs to be previously 
                       # started in your local machine)
    LOCAL_APPIUM       # Uses the default Appium local server at "http://localhost:4273/wd/hub"
    SAUCE              # Use the SAUCE LABS cloud (with the settings in `environment.properties`)
    <URL>              # Point to a remote Selenium Grid HUB hosted in that URL

Example:

    GRID=NO_GRID mvn clean test
    GRID=http://www.mycloudgrid.com:4444/wd/hub mvn clean test
    
## BROWSER

The browser where the test will be run. List of browsers supported:

    FIREFOX     # Mozilla's baby (default value)
    CHROME      # Google's RAM-eating monster that works too well
    SAFARI      # Apple's option for your hipster browsing needs
    IE          # Microsoft's browser allegedly only used in China
    PHANTOMJS   # Headless browser that runs pure HTML/JS without rendering in a GUI. Usually much faster but less 
                  compatible with your super cool JS scripts so use with care


# Running Tests

Please go to the [Running Tests](https://github.com/jbaxenom/laget-framework/wiki/Running-Tests) page in the wiki
to see how to use the different runners the framework provides.

# Automatic Retries after Test Failures

Retrying tests is a known best practice to:

1. Increase stability and reliability of tests running in unreliable test environments,
2. Identify *flaky tests* (tests that fail apparently randomly) for further investigation and fixing.

It is important to understand that using test retrying techniques should never be considered a 
normal approach to testing, not only because it is slower, but most importantly because not 
investigating the reason for tests to be flaky can potentially hide actual bugs in the system.


## Supported Retry Types

This library provides 3 different approaches for automated retries:

* **Retry all failing tests**: Retry any failing TestNG test marked with TestNG's `@Test` annotation. 
* **Retry specific failing tests**: Retry specific failing TestNG tests, annotated with the `@Retry` annotation.

### Retrying All Failing Tests 

By using the `TestAnnotationTransformer` listener, every test method or class using TestNG's default
`@Test` annotation will be automatically retried on failure a defined set of times. 

To define maximum number of runs use the `maxRunCount` parameter in the `environment.properties` file.

For example, a value of `3` will set maximum number of runs to 3 (1 original run and 2 retries).

### Retrying Specific Failing Tests

By using the `RetryAnnotationTransformer` listener, you can specify the test methods or classes that 
will retry on error by simply adding a `@Retry` annotation either at method or class level.

To define the total amount of times that the test should run you can use the `count` parameter inside
the annotation (ex: `@Retry(count = 5)`)


## Adding TestNG listeners

To use either of the TestNG retry mechanisms, you just need to add 
the desired listener to your TestNNG run. There are several ways to do it, as explained in the 
[TestNG's documentation](http://testng.org/doc/documentation-main.html#testng-listeners). 

In order to enable it in your IntelliJ runs we recommend to add the listener as a command line 
argument. The way to do it is to edit the TestNG executor that runs the test. You can define the 
executors in the upper right selector in IntelliJ UI, where the name of the last test run appears: 
just click in the small arrow in the right, choose "Edit Configurations" and, under "JDK Settings", 
add the following in "Test runner params":

    -listener com.akamai.testing.testng.listeners.TestAnnotationTransformer.class

or
    
    -listener com.akamai.testing.testng.listeners.RetryAnnotationTransformer.class

Now every time you run a test from IntelliJ it should retry as you defined.

# Roadmap

Please go the [Roadmap](https://github.com/jbaxenom/laget-framework/wiki/Roadmap) page in the wiki to see some of the 
features I'm planning on adding to the LAGET framework.
