package com.jbaxenom.laget.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jbaxenom.laget.interactions.api.test_data.string_handling.PayloadTools;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cucumber report aggregator based on ...
 *
 * @author jbaxenom on 4/13/15.
 */
public class ReportAggregator {

    public static void main(String[] args) {

        // Get all the json reports from the test run
        String timestamp = System.getenv("TIMESTAMP");
        File reportOutputDirectory = new File("reports/" + timestamp + "/cucumber");
        List<String> list = new ArrayList<>();

        list.add("reports/" + timestamp + "/cucumber/osx-firefox.json");
        list.add("reports/" + timestamp + "/cucumber/osx-chrome.json");
        list.add("reports/" + timestamp + "/cucumber/linux-firefox.json");
        list.add("reports/" + timestamp + "/cucumber/linux-chrome.json");
        list.add("reports/" + timestamp + "/cucumber/windows81-firefox.json");
        list.add("reports/" + timestamp + "/cucumber/windows81-chrome.json");
        list.add("reports/" + timestamp + "/cucumber/windows81-ie11.json");
        list.add("reports/" + timestamp + "/cucumber/windows7-ie11.json");


        // Add os, browser and version to the feature names in the json reports for distinguishing between runs
        for (String jsonReport : list) {

            String payload = PayloadTools.getPayloadFromFile(jsonReport);
            String featureName = PayloadTools.getFieldValueInPayload(payload, "name");
            String os = StringUtils.substringBetween(jsonReport, "/cucumber/", "-").toUpperCase();
            String browser = StringUtils.substringBetween(jsonReport, "-", ".").toUpperCase();
            String newFeatureName = featureName + " [ " + os + " | " + browser + " | "
                    + com.jbaxenom.laget.configuration.Configuration.getEnvironment().toString() + " ] ";

            try {
                ArrayNode root = (ArrayNode) new ObjectMapper().readTree(payload);
                ((ObjectNode) root.get(0)).put("name", newFeatureName);

                FileWriter file = new FileWriter(jsonReport);
                file.write(root.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // build the unified report
        buildReport(list, reportOutputDirectory);
    }

    private static void buildReport(List<String> list, File reportOutputDirectory) {

        String projectName = "latef";

        boolean skippedFails = true;
        boolean pendingFails = false;
        boolean undefinedFails = true;
        boolean missingFails = true;
        boolean runWithJenkins = false;
        boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        configuration.setStatusFlags(skippedFails, pendingFails, undefinedFails, missingFails);
        configuration.setParallelTesting(parallelTesting);
        configuration.setRunWithJenkins(runWithJenkins);

        ReportBuilder reportBuilder = new ReportBuilder(list, configuration);
        reportBuilder.generateReports();
    }

}
