package com.jbaxenom.laget.api.test_data.string_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jbaxenom.laget.api.test_data.Luhn;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author jbaxenom on 7/24/14.
 */
public class PayloadTools {

    /**
     * @param filePath
     * @return a String containing the json payload in the file passed
     */
    public static String getPayloadFromFile(String filePath) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);

        java.util.Scanner scanner = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A");

        return scanner.hasNext() ? scanner.next() : "";
    }

    /**
     * Sets the value {@code value} in the field {@code field}
     *
     * @param payload
     * @param field
     * @param value
     * @return the payload containing the updated value(s)
     */
    public static String setFieldValueInPayload(String payload, String field, String value) {
        try {
            ObjectNode root = (ObjectNode) new ObjectMapper().readTree(payload);
            if (field.contains(".")) {
                String[] array = field.split("\\.");
                switch (array.length) {
                    case 2:
                        if (value.equals("null")) {
                            root.with(array[0]).putNull(array[1]);
                        } else {
                            root.with(array[0]).put(array[1], value);
                        }
                        break;
                    case 3:
                        if (array[1].equals("0")) {
                            if (value.equals("null")) {
                                ((ObjectNode) root.get(array[0]).get(0)).putNull(array[2]);
                            } else {
                                ((ObjectNode) root.get(array[0]).get(0)).put(array[2], value);
                            }
                        } else {
                            if (value.equals("null")) {
                                root.with(array[0]).with(array[1]).putNull(array[2]);
                            } else {

                                //hack to be able to override nulls with objects
                                if (root.get(array[0]).get(array[1]).isNull()) {
                                    root.with(array[0]).putObject(array[1]);
                                }
                                root
                                        .with(array[0])
                                        .with(array[1])
                                        .put(array[2], value);
                            }
                        }
                        break;
                }
            } else if (value.equals("null")) {
                root.remove(field);
                root.putNull(field);
            } else {
                root.put(field, value);
            }

            return root.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Removes the field {@code field} from the payload
     *
     * @param payload
     * @param field
     * @return the updated payload
     */
    public static String removeFieldFromPayload(String payload, String field) {
        try {
            ObjectNode root = (ObjectNode) new ObjectMapper().readTree(payload);
            if (field.contains(".")) {
                String[] array = field.split("\\.");
                root.with(array[0]).remove(array[1]);
            } else {
                root.remove(field);
            }
            return root.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param payload
     * @param field
     * @return the value of the field {@code field} in the payload
     */
    public static String getFieldValueInPayload(String payload, String field) {
        try {
            ObjectNode root = (ObjectNode) new ObjectMapper().readTree(payload);
            if (field.contains(".")) {
                String[] array = field.split("\\.");
                switch (array.length) {
                    case 2:
                        return root.with(array[0]).get(array[1]).asText();
                    case 3:
                        if (array[1].equals("0")) {
                            return root.get(array[0]).get(0).get(array[2]).asText();
                        } else {
                            return root.with(array[0]).with(array[1]).get(array[2]).asText();
                        }
                }
            } else {
                return root.get(field).asText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @return a randomly generated valid Swedish PNO in format "yyMMdd-cccl"
     */
    public static String generateRandomIdentificationNumber() {
        String randomIdPartOne = "";
        Boolean dateFormatValid = false;
        while (!dateFormatValid) {
            randomIdPartOne = RandomStringUtils.randomNumeric(6);
            try {
                DateFormat df = new SimpleDateFormat("yyMMdd");
                df.setLenient(false);
                df.parse(randomIdPartOne);
                dateFormatValid = true;
            } catch (ParseException e) {
                dateFormatValid = false;
            }
        }

        String randomIdPartTwo = RandomStringUtils.randomNumeric(3);
        String luhn = Luhn.generateDigit(randomIdPartOne.concat(randomIdPartTwo));
        return randomIdPartOne + "-" + randomIdPartTwo + luhn;
    }

    /**
     * @param numberOfCharacters
     * @return a randomly generated String with the number of characters passed
     */
    public static String generateRandomString(int numberOfCharacters) {
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

}
