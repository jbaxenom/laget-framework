# LAGET Framework - Layered API and GUI End-to-end Testing

## Background

**The LAGET Framework's goal is to show how a test framework can enable all members of the team to create 
automated tests in the way they can be more effective**. It does it by providing an adaptive approach 
for creating test cases, using different "abstraction layers" that adapt to the different technical 
skill-sets that agile team members have, taking special consideration towards team members that are 
less technically savvy (or not at all).

We testers love to complain about how difficult it is to adopt a _"Whole Team Approach"_ in our teams, 
blaming it on how wrongly others interact with us. We then go back to our workstations to continue 
coding our test automation scripts, created by ourselves and aimed to be used only by us...

After several years working in fixing similar behaviors in pretty much every test team I have worked 
at, I decided to share my experience and knowledge with the testing community. I wrote some blog posts 
(you can check them out 
[here](https://www.linkedin.com/pulse/what-test-automation-framework-should-building-chema-del-barco) 
and [here](https://www.linkedin.com/pulse/writing-test-framework-help-team-build-quality-chema-del-barco)), 
and I also decided to share how I have approached all my test libraries to help solving this problem. 
This is how the LAGET framework was born. For several years now, every time I have solved a particular 
challenge related to making test automation more effective, or to be able to test a different kind of 
software, every time that I believed that it could be useful for others, I added it to LAGET. 

As you can see, LAGET is just a result of my experience as a tester. It is something that _works for 
me_. I do not claim that it is going to solve your particular automation problems (like too many 
libraries and frameworks out there do), or that is a piece of java art (I actually don't consider 
myself good in programming). However, I think LAGET provides is a humble but strong combination of 
core settings, methodologies, best practices and out-of-the-box tools, and it has always helped me 
as a **good start point to create GUI tests for Desktop Web, Mobile Web and Mobile apps, or API tests 
for RESTfull webservices**, so I wanted to share it with everyone.

So as good Agile testers we are: give it a try, see if you like it, ask questions and feel free to 
experiment, change and improve it!

## Features and Capabilities

LAGET provides tools to test: 
+ Desktop and mobile websites,
+ Native and Hybrid mobile apps, and
+ RESTfull webservices

Additionally, LAGET also gives you a head start in many areas we testers always have to cover:

- Supports **parameterized test runs** through both a configuration file (`environment.properties`) 
  and/or using environmental variables, which allow for easy integration with CI tools
- Supports both **local and remote GUI test runs via Selenium Grid**. When creating GUI tests the 
  framework will automatically generate the right instance of WebDriver/AppiumDriver depending on 
  the parameters set for the test run. It also **integrates with SauceLabs**, including sending the 
  proper test name and its result: you just need to specify your username and key in the configuration 
  file
- Supports **Chrome, Firefox, IE, Safari and PhantomJS** desktop browsers and provides standard 
  configuration to test both **iOS and Android websites and native apps via Appium**. 
- Includes parameters to setup **external browser servers** (chromedriver, geckodriver and 
  iedriverserver), whose **latest versions are included in the `lib/` folder**
- Understands the concept of **"test environments"**: by setting the different environment's URLs 
  in the `environment.properties` file, LAGET will automatically point to the ones corresponding to 
  the ENVIRONMENT parameter. This means you can run your tests in different environments just by 
  changing one parameter/environmental variable
- Implements the most common **Page Object helpers** for both WebDriver and Appium, which can be 
  used via extending their respective provided `AbstractPage`
- It provides an out-of-the-box solution to **run cucumber-jvm parallel tests** and aggregate the 
  reports into one nice-looking HTML
- It provides **test data tools** in the form of entities, actors and actions that can process and 
  parse **JSON data** and **SQL queries**
- It provides the option to **automatically retry failing tests**, in different levels of execution
- It **will (hopefully soon) integrate with JIRA and Zephyr plugin for JIRA** so that your test runs 
  can be fully managed directly from these famous project and test management tools. 


## LAGET Layers Explained

LAGET defines 3 different but complementary layers: "Behavior", "Domain" and "Interactions", which 
are explained below. Thanks to this adaptative approach, LAGET has successfully been used to teach 
non-technical testers how to use Cucumber, Selenium, Appium, Cloud Testing, Continuous Integration 
and several other tools and methodologies related to Agile Testing. 


### Behavior Layer (Cucumber)

Behavior-Driven Development aims to provide a common language to represent business features through 
different scenarios (or examples) that contain several steps to define the functionality of the 
application. Scenarios implement user stories from their own perspective, which is at the same time 
the functional acceptance criteria for that application. Cucumber (in our case 
[cucumber-jvm](https://github.com/cucumber/cucumber-jvm)) is an implementation of BDD that uses 
[Gherkin notation](https://github.com/cucumber/cucumber/wiki/Gherkin) (Given, When, Then, And) as 
the common language framework.

In cucumber, statements implement parameter mapping, for instance:

    “^(.*) sends the \"(.*)\" REST call to (.*)$”

becomes:

    public void sendRESTCall(String sender, String call, String receiver) { }
    
Which implies that steps are reusable between different scenarios and even feature files.

For more information about how cucumber-jvm works I suggest to use the link above or go to the 
[Cucumber website](https://cucumber.io/).


### Domain Layer (AAO)

Tests ALWAYS follow the same pattern. Might seem crazy, but if you think about it we always do the 
same:

    1. We put a System Under Test in a particular state
    2. We interact with it in the way a user would
    3. We compare the result of that action with what we expected

LAGET implements this idea by adapting [Meza's brilliant AAO framework](https://github.com/meza/AAO) 
to encapsulate REST calls, web and mobile interactions into `Action` objects which are executed by 
`Actor` objects that pre-define whatever data the user would need to interact with the app(s). This 
allows for framework users with little technical experience to create tests easily, as they can just 
use whatever actions are available for the actors that are implemented.


#### AAO + Cucumber

AAO combines perfectly with Cucumber to provide a systematic and simple approach to implement test 
steps:

    1. In the "Given" steps, we prepare the SUT and use test data to define the Actor(s) that will 
       interact with it
    2. During the "When" steps, the Actor(s) performs one or more Actions in the SUT
    3. In the "Then" steps, we assert the Outcomes of these Actions against their expected behaviour

To put all this together, AAO defines a *Context* of a test as a global object that contains:
+ The `Environment` where the test is running, 
+ The last `Actor` used,
+ The last `Action` performed, and 
+ The `Subject` of the current step in the test (which can be anything: a text, a page... Whatever 
  we need to save for other steps)

This is represented as an `EnvironmentAwareContext` object created as an attribute of the cucumber's 
Step file class, allowing for global access to any step defined in that class.

As a result, tests created using the AAO layer are very easy to both write and implement:
    
    Feature: Testing with actors and actions
      As an agile team member
      I want to test using actors and actions
      So that I don't need to know the actual implementation
    
      
      Scenario: Test using Actors and Actions
        Given there is a user with username "chema" and password "pass" in the system
        When he performs the example action
        Then it is successful


### Interactions Layer

None of the above would be useful if we could not interact with our SUTs (System Under Test), and 
this framework provides tons of tester candy in this area! LAGET was designed to test websites in 
both desktop and mobile devices, mobile apps and REST-full webservices, and for that it integrates 
its behaviour and domain layers with the our big favorites Selenium WebDriver, Selenium Grid and 
Appium, as well as providing a custom solution for Integration tests. 

#### Selenium WebDriver

Selenium WebDriver is an Open Source API that communicates directly with browsers through "drivers". 
It provides an very simple API to define typical elements and interactions in any website: 
WebElement, Wait, FirefoxDriver (ChromeDriver, etc.). Selenium can run concurrently and remote 
through Selenium Grid, it is constantly improved and has a very active community, not to say it's a 
W3C standard for web testing!

LAGET provides abstract structures that contain the most common helpers you always have to implement 
from scratch when using Selenium. It also provides examples in how to apply best practices (yes, 
including Page Factories!).

#### Appium

The guys at SauceLabs wanted to make Mobile testing easier, and they thought "why making something 
from scratch if what we have is already well known and loved?". So they took the Selenium concept 
and created Appium, which makes interactions with Android and IOS native, hybrid or web apps 
completely transparent by using their own AndroidDriver and IOSDriver implementations. As it is 
based in WebDriver it can be seamlessly integrated in web GUI frameworks, which is exactly what 
LAGET does :)

LAGET provides dedicated Actors and Actions ready to interact with mobile devices in its domain 
layer, which makes creating tests for them even easier.

#### Page Objects

Page Objects are a class representation of a web page that uses the WebDriver API to separate the 
structure of the website and how users can interact with it from the actual tests. The attributes of
these classes are WebDriver’s WebElement objects, which require a unique web element locator and 
provide all the possible interactions with the element, whilst the methods are typically 
"interactions", "presence verifications" and "actions/flows".

In addition, by using the Page Object pattern you can:
+ Chain methods so to implement actions or flows in a more readable way. To do so, interactions and 
  actions should always return another page object (which could be itself);
+ Use the advantages of "Lazy Initialization": web elements are not looked up until they are used in
  the test!

#### Integration Testing (REST and SQL tools)

[TODO] Explanation coming soon!


## Package Structure
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
        

## Installation

+ The project requires `maven` and/or `ant` to run, depending if you want to run parallel tests using 
Selenium Grid or not.
+ If you want to run your tests locally using a specific web browser, you need to have it installed 
before (hehe)
+ Since Selenium 3.X, all browsers require to use their specific webdriver servers. If you use 
  Windows or Mac, the latest versions of these servers are already included in their respective `lib` 
  folder. You will need to set the path to the servers/binaries in the respective parameter of the 
  `environment.properties` file. You can get the latest binaries here:
  + [Chromedriver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
  + [Geckodriver]() (Firefox)
  + [PhantomJS](http://phantomjs.org/download.html)
  + [IEDriverServer](http://www.seleniumhq.org/download/) (Selenium's official website)
+ The default values of `environment.properties` will run the tests sequentially and individually 
  in your local Firefox browser, in the "test" environment for the respective application.
  

## Usage

The `environment.properties` file in the `test/resources` package is used to set up the test run. 
The file explains the different options available for the different parameters. Additionally you can 
use environmental variables to set the most important attributes: `ENVIRONMENT`, `GRID`, `BROWSER`, 
`BROWSER_VERSION` (only if `GRID=SAUCE`) and `OS` (also only if `GRID=SAUCE`).

Here's a short explanation of what these variables do:

### ENVIRONMENT

Sets the test environment where the tests will run. The endpoints need to be set in 
`environment.properties`. Options:

    DEV             # Local dev environment
    INTEGRATION     # Environment used for verifying the integration of different components of the 
                      system
    STAGING         # The Staging environment, normally used for regression tests
    TESTDRIVE       # Testdrives are usually production test environments
    PRODUCTION      # The live environment

### GRID

Selenium GRID allows concurrent, cross-browser testing by creating a farm of cloud clients where to 
run tests. Options supported:

    NO_GRID (or empty) # Runs the test in your local WebDriver browser instead of Selenium Grid 
                         (default value)
    LOCAL_GRID         # Use the default "http://localhost:4444/wd/hub" local Grid (which needs to 
                         be previously 
                       # started in your local machine)
    LOCAL_APPIUM       # Uses the default Appium local server at "http://localhost:4273/wd/hub"
    SAUCE              # Use the SAUCE LABS cloud (with the settings in `environment.properties`)
    <URL>              # Point to a remote Selenium Grid HUB hosted in that URL

Example:

    GRID=NO_GRID mvn clean test
    GRID=http://www.mycloudgrid.com:4444/wd/hub mvn clean test
    
### BROWSER

The browser where the test will be run. List of browsers supported:

    FIREFOX     # Mozilla's baby (default value)
    CHROME      # Google's RAM-eating monster that works too well
    SAFARI      # Apple's option for your hipster browsing needs
    IE          # Microsoft's browser allegedly only used in China
    PHANTOMJS   # Headless browser that runs pure HTML/JS without rendering in a GUI. Usually much 
                  faster but less compatible with your super cool JS scripts so use with care


## Running Tests

There are 3 ways of running tests: using **ant**, using **maven** or using your preferred 
**programming IDE**.

### Running Tests with Ant
The `ant` build contains a predefined parallel cross-browser test run meant to be run in the `SAUCE` 
grid environment only. It is configured by the `build.xml` file in the root folder. You can set a 
couple of parameters for the run: `ENVIRONMENT` and `SUITE`. `ENVIRONMENT` is the variable explained 
above, whereas `SUITE` lets you set the cucumber tag for the tests to run.

To run the ant build, simply type `ant` in the root folder of the test framework. Everything else is 
automatic!

The framework will also build a nice HTML test report with the aggregated results from all the 
parallel runs. It can be located in `LAGET/reports` and the time and date with format 
"MM.dd_HH.mm.ss". Just open the file `cucumber/feature-overview.html` to get a complete and beautiful 
status of your tests.

### Running Tests with Maven
This is the default way of running tests. The standard phases of the maven project will run all the 
test suites, which is often not very convenient, but luckily the cucumber test runner provides some 
options to select the suite to run or change the output format.


Examples:

`mvn clean install -Dcucumber.options="--tags @pure_webdriver"`

Will run all the tests with tag `@pure_webdriver` in a local Firefox in the "test" environment. 
Please note that you can tag both features or single scenarios!

`ENVIRONMENT=DEV GRID=LOCAL BROWSER=CHROME mvn clean test -Dcucumber.options="--tags @pure_webdriver"`

Will run the same test in the local Dev environment, using an already running local Selenium Grid 
and Chrome browser.

`mvn clean install -Dcucumber.options="'src/test/resources/com/jbaxenom/laget/Direct webdriver in 
cucumber example.feature:7'"`

Will run only the scenario on line 7 of the feature "Direct webdriver in cucumber example", which 
corresponds to the first scenario, in your local Firefox and pointing to the "test" environment.

If you want to know more about how to run maven you can check the 
[maven website](https://maven.apache.org/run-maven/index.html). Options for the cucumber command 
line can be found here or by running 

`mvn clean install -Dcucumber.options="--help"`.

### Running Tests in your IDE
Most of the more common IDE's have a cucumber plugin that can be use to run tests in the same way 
you run normal JUnit tests. IntelliJ for instance allows running tests by:

+ Right-clicking on a feature file and clicking `Run 'Feature: <feature name>'`
+ Opening a feature file, right-clicking on a `Feature` tag and clicking 
  `Run 'Feature: <feature name>'`
+ Opening a feature file, right-clicking on a `Scenario` tag and clicking 
  `Run 'Scenario: <scenario name>'`
+ Right-clicking on the `RunCucumberTest.java` file in `test/java/com.jbaxenom.laget` and clicking 
  `Run RunCucumberTest`
+ Right-clicking on the project root and selecting `Run` -> `All Features in: LAGET`

## Automatic Retries after Test Failures

Retrying tests is a known best practice to:

1. Increase stability and reliability of tests running in unreliable test environments,
2. Identify *flaky tests* (tests that fail apparently randomly) for further investigation and fixing.

It is important to understand that using test retrying techniques should never be considered a 
normal approach to testing, not only because it is slower, but most importantly because not 
investigating the reason for tests to be flaky can potentially hide actual bugs in the system.


### Supported Retry Types

This library provides 3 different approaches for automated retries:

* **Retry all failing tests**: Retry any failing TestNG test marked with TestNG's `@Test` annotation. 
* **Retry specific failing tests**: Retry specific failing TestNG tests, annotated with the `@Retry` 
                                    annotation.

#### Retrying All Failing Tests 

By using the `TestAnnotationTransformer` listener, every test method or class using TestNG's default
`@Test` annotation will be automatically retried on failure a defined set of times. 

To define maximum number of runs use the `maxRunCount` parameter in the `environment.properties` file.

For example, a value of `3` will set maximum number of runs to 3 (1 original run and 2 retries).

#### Retrying Specific Failing Tests

By using the `RetryAnnotationTransformer` listener, you can specify the test methods or classes that 
will retry on error by simply adding a `@Retry` annotation either at method or class level.

To define the total amount of times that the test should run you can use the `count` parameter inside
the annotation (ex: `@Retry(count = 5)`)


### Adding TestNG listeners

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

## Roadmap

This is a list of new features and ideas I'd like to include in the LAGET framework:

+ ~~Add capability to automatically retry tests that are failing~~ **[DONE]**
+ Replace current REST test layer with 
  [REST-assured based](https://github.com/rest-assured/rest-assured) implementation
+ Add SQL DB Query Handling/Testing
  + [DB] Add a SQLActor and SQLActions to handle SQL DB queries to be used in Integration tests 
  and/or to set Test Data for other tests
+ Add Integration to JIRA
  + [JIRA] Retrieve data from JIRA task to use it in the test
  + [JIRA] Parse cucumber scenarios defined in JIRA description field to json
  + [JIRA] Add capability to run a test from command line by passing a JIRA task ID
  + [JIRA] Send the test report for a particular test as JIRA comment for a given task
+ Add Integration to Zephyr's JIRA plugin
  + [Zephyr] Retrieve test data from Zephyr task in JIRA
  + [Zephyr] Parse cucumber scenarios described in Zephyr steps to json 
  + [Zephyr] Add capability to run a test from command line by passing a Zephyr task ID
  + [Zephyr] Add capability to run tests directly from the Zephyr task (Test WebService)
+ More to come!

