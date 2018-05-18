# LAGET Framework - Layered API and GUI End-to-end Testing

## Background

**LAGET's goal is to show how a test framework can enable all members of the team to create automated
tests in the way they can be more effective**. 

Having all members of the team collaborating to create tests can greatly increase the quality of your 
deliverables. However, we testers often create tools that are easy to use by ourselves but not by other 
members of our team, specially non-technical ones.

The LAGET Framework aims to bridge this knowledge gap by providing an adaptive approach for creating 
test cases. It approaches this by using 3 different abstraction layers that adapt to the different 
technical skill-sets: "Behaviour", "Domain" and "Interactions". Thanks to this adaptive approach 
LAGET has successfully been used to teach non-technical Testers, Business Analysts and Product Owners 
to use Cucumber, Selenium, Appium, Cloud Testing, Continuous Integration and several other tools and 
methodologies related to Agile Testing.

I like to think of LAGET as a "test automation boiler plate", a compendium of core settings, 
methodologies, best practices and tools that will give you a head start to create GUI and API tests 
for Desktop or Mobile websites and apps. I hope it helps you as well!


## Features and Capabilities

LAGET provides tools to automatically test:
+ Desktop and mobile websites,
+ Native and Hybrid mobile apps, and
+ RESTfull webservices

Additionally, it also includes tons of test automation candy:

- **Cross-browser, OS & Platform**. The framework is built to make web testing platform-agnostic, 
  using Appium and WebDriver to support Chrome, Firefox, IE and Safari browsers in both desktop and 
  mobile devices
- **Parameterized test runs**. This allows for easy integration with CI tools. You can use either a 
  configuration file (`environment.properties`) or environmental variables. 
- **Local and remote GUI test runs via Selenium Grid**. When creating GUI tests the 
  framework will automatically generate the right instance of WebDriver/AppiumDriver depending on 
  the parameters set for the test run. 
- **SauceLabs integration**. It will send the proper test name and result to your SauceLabs hub just 
  by specifying your username and key in the configuration file
- **External browser servers included**. Chromedriver, geckodriver and iedriverserver latest versions 
  are included in the `lib/` folder, and can be easily setup in `environment.properties`
- **Test Environment-agnostic**. You can run your tests in different environments just by 
  changing one parameter or environmental variable. 
- **Page Object helpers**. It includes the typical boilerplate for both WebDriver and Appium page 
  objects. You just need to extend the provided `AbstractPage` for the respective platform
- **Parallel Cucumber tests**. It provides an out-of-the-box solution to run cucumber-jvm parallel 
  tests and aggregate the reports into one nice-looking HTML
- **Test Data tools**. It provides entities, actors and actions that can process and parse json data
  and SQL queries
- **Automatic Retry of failed tests**. You can choose to automatically retry specific tests if they 
  fail.

Want more info about it? Check out the [wiki](https://github.com/jbaxenom/laget-framework/wiki)
