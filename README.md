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
With the goal of maximizing the usefulness of the framework and thus removing unnecessary overhead for more technical 
testers, LAGET is offered in 2 flavours: LAGET-DD and LAGET-LITE. LAGET-DD (Domain-Driven), which is my recommended 
option, is prepared to use all the layers combined, whilst LAGET-LITE removes the Domain Layer to go "straight to the 
point" and combine Cucumber and Selenium and/or REST calls.  

Thanks to this adaptative approach, LAGET has successfully been used to teach non-technical testers how to use Cucumber,
Selenium, Appium, Cloud Testing, Continuous Integration and several other tools and methodologies related to Agile
Testing. 

So as good Agile testers we are, give it a try, see if you like it, ask questions and feel free to experiment, change and
improve it!

# LAGET Layers Explained

## Behavior Layer (Cucumber)

Behavior-Driven Development aims to provide a common language to represent business features through different scenarios
that contain several steps. Scenarios implement user stories from their own perspective, which is at the same time the 
functional acceptance criteria for that application. [Cucumber](https://cucumber.io/) is an implementation of BDD that 
uses [gherkin notation](https://github.com/cucumber/cucumber/wiki/Gherkin) (Given, When, Then, And) as the common 
language framework.

In cucumber, statements implement parameter mapping, for instance:

    “^(.*) sends the \"(.*)\" REST call to (.*)$”

becomes 

    public void sendRESTCall(String sender, String call, String receiver) { }
    
Which provides a simple way to script the tests to make them more dynamic, as steps are reusable between different
scenarios and even feature files.

## Domain Layer

Tests ALWAYS follow the same pattern. If you think about it, we always do the same:
 
    We have a System Under Test in a particular state
    Someone does something on that SUT
    We compare the result with the expectation

LAGET implements this idea by adapting Meza's brilliant [AAO](https://github.com/meza/AAO) library to encapsulate REST 
calls, web page and mobile interactions into action objects which are executed by actor objects that pre-define whatever 
data the user would need to interact with the app(s). This allows for Framework users with little technical experience 
to create tests easily, as they can just use whatever actions are available for the actors that are implemented.

### AAO + Cucumber

AAO combines perfectly with Cucumber to provide a systematic, trivial approach to implement test steps: 

 - In the `Given` steps we use test data to define the `Actor`
 - During the `When` steps, the `Actor` performs a set of `Actions`
 - Finally in the `Then` steps we assert the `Outcomes` of these actions against their expected behaviour


## Interactions Layer

None of the above would be useful if we could not interact with our SUTs (System Under Test), and LAGET provides tons
of tester candy in this area! LAGET was designed to test websites in both desktop and mobile devices, mobile apps
and REST-full webservices, and it makes your work a lot easier by:

- Supporting parametrized test runs through configuration file (environment.properties) and/or environmental variables 
  (for easy integration with CI tools)
- Supporting both local test runs (browser or grid in both desktop and mobile) and remote test runs, including 
  out-of-the-box integration with SauceLabs
- Supporting Chrome, Firefox, IE, Safari and PhantomJS browsers and providing standard configuration for both iOS and
  Android apps. It also includes parameters to setup the browser servers, which are also included in the repository
- Implementing seamless setup of different test environments for both web and integration tests. The framework will point 
  to the right environment URL's just by setting them in the environment.properties file
- Providing the most common Page Object helpers for both WebDriver and Appium (through Abstract pages)
- Providing out-of-the-box solution to run parallel tests and aggregate the reports into one nice-looking HTML
- Providing some nice test data tools, specially for writing and reading REST payloads
 
### Selenium WebDriver 

Selenium WebDriver is an Open Source API that communicates directly with browsers through drivers. IT uses OO notation 
to define typical elements and interactions in any website: WebElement, Wait, FirefoxDriver (ChromeDriver, etc.). 
Selenium can run concurrently and remote through Selenium Grid, it is constantly improved and has a very active 
community.

LAGET provides abstract structures that contain the most common helpers you always have to implement from scratch when
using Selenium. It also provides examples in how to apply best practices. 

### Appium

Appium makes interactions with Android and IOS native, hybrid or web apps completely transparent by using their own 
AndroidDriver and IOSDriver implementations. As it is based in WebDriver it can be seamlessly integrated in web GUI 
frameworks. 

LAGET provides dedicated Actors and Actions ready to interact with mobile devices, which adds a further abstraction 
level to make it even easier. 

### Page Objects

Page Objects are a OO representation of a web page that uses the WebDriver API to abstract the structure of the website 
from the user interactions. The attributes of these classes are WebDriver’s WebElement objects, which require a unique 
web element locator and provide all the possible interactions with the element. The methods are interactions, presence 
verifications and actions.

Some other good properties of Page Object pattern are:

- Method chaining allows to implement actions or flows in a more readable way
- Interactions and actions always return another page object.
- Lazy Initialization: web elements are not looked up until they are used in the test!


### API Layer (REST tools)

Explanation coming soon!


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
    PERFTEST        # The Performance Test environment, usually very similar to the live system (sometimes Staging itself)
    TESTDRIVE       # Testdrives are usually production test environments
    PRODUCTION      # The live environment

## GRID

Selenium GRID allows concurrent, cross-browser testing by creating a farm of cloud clients where to run tests. Options 
supported:

    NO_GRID (or empty) # Runs the test in your local WebDriver browser instead of Selenium Grid (default value)
    LOCAL              # Use the default "http://localhost:4444/wd/hub" local Grid (which needs to be previously 
                       # started in your local machine)
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


There are 3 ways of running tests: using `ant`, using `maven` or using your preferred programming IDE.

# Running Tests with Ant

The `ant` build contains a predefined parallel cross-browser test run meant to be run in the SAUCE grid environment only. 
It is configured by the `build.xml` file in the root folder. You can set a couple of parameters for the run: ENVIRONMENT 
and SUITE. ENVIRONMENT is the variable explained above, whereas SUITE lets you set the cucumber tag for the tests to run.

To run the ant build, simply type `ant` in the root folder of the test framework. Everything else is automatic! 

The framework will also build a nice HTML test report with the aggregated results from all the parallel runs. It can be 
located in `LAGET/reports` and the time and date with format "MM.dd_HH.mm.ss". Just open the file 
`cucumber/feature-overview.html` to get a complete and beautiful status of your tests. 


# Running Tests with Maven

This is the default way of running tests. The standard phases of the maven project will run all the test suites, which 
is often not very convenient, but luckily the cucumber test runner provides some options to select the suite to run or 
change the output format.

Examples:

    mvn clean install -Dcucumber.options="--tags @pure_webdriver"

Will run all the tests with tag "@quinext" in a local Firefox in the "test" environment. Please note that you can tag
both features or single scenarios!

    ENVIRONMENT=DEV GRID=LOCAL BROWSER=CHROME mvn clean test -Dcucumber.options="--tags @pure_webdriver"

Will run the same test in the local Dev environment, using an already running local Selenium Grid and Chrome browser.

    mvn clean install -Dcucumber.options="'src/test/resources/com/jbaxenom/LAGET/Pure WebDriver POC.feature:7'"

Will run only the scenario on line 7 of the feature "Pure WebDriver POC", which corresponds to the first scenario,
in your local Firefox and pointing to the "test" environment.

If you want to know more about how to run maven you can check [their website](https://maven.apache.org/run-maven/index.html). Options for the cucumber
command line can be found [here](https://cucumber.io/docs/reference/jvm) or by running `mvn clean install -Dcucumber.options="--help"`.

# Running Tests in your IDE

Most of the more common IDE's have a cucumber plugin that can be use to run tests in the same way you run normal JUnit 
tests. IntelliJ for instance allows running tests by:
 
 - Right-clicking on a feature file and clicking `Run 'Feature: <feature name>'`
 - Opening a feature file, right-clicking on a `Feature` tag and clicking `Run 'Feature: <feature name>'`
 - Opening a feature file, right-clicking on a `Scenario` tag and clicking `Run 'Scenario: <scenario name>'`
 - Right-clicking on the `RunCucumberTest.java` file in `test/java/com.jbaxenom.laget` and clicking `Run RunCucumberTest`
 - Right-clicking on the project root and selecting `Run` -> `All Features in: LAGET`