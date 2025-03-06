package com.example.geolocutil;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoLocationIntegrationTest {

    /**
     * Helper method to run Main.main() with the given arguments and capture all output (stdout and stderr).
     */
    private String runMainAndCaptureAll(String[] args) {
        ByteArrayOutputStream allContent = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(allContent);
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(ps);
        System.setErr(ps);
        try {
            Main.main(args);
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
        return allContent.toString();
    }

    @Test
    public void testCityStateAndZip() {
        // Test with a city/state and a zip code.
        String[] args = {"Madison, WI", "12345"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output:\n" + output);
        // Verify that expected output labels are present.
        assertTrue(output.contains("Latitude:"), "Output should contain 'Latitude:'");
        assertTrue(output.contains("Longitude:"), "Output should contain 'Longitude:'");
    }

    @Test
    public void testValidCityStateOnly() {
        // Test with a valid city/state input.
        String[] args = {"Chicago, IL"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for Chicago, IL:\n" + output);
        // Check that the output contains expected labels and input text.
        assertTrue(output.contains("Input: Chicago, IL"), "Output should contain 'Input: Chicago, IL'");
        assertTrue(output.contains("Latitude:"), "Output should contain 'Latitude:'");
        assertTrue(output.contains("Longitude:"), "Output should contain 'Longitude:'");
        assertTrue(output.contains("Country:"), "Output should contain 'Country:'");
    }

    @Test
    public void testValidZipOnly() {
        // Test with a valid zip code input.
        String[] args = {"10001"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for zip 10001:\n" + output);
        // Check that the output contains expected labels and input text.
        assertTrue(output.contains("Input: 10001"), "Output should contain 'Input: 10001'");
        assertTrue(output.contains("Latitude:"), "Output should contain 'Latitude:'");
        assertTrue(output.contains("Longitude:"), "Output should contain 'Longitude:'");
        assertTrue(output.contains("Country:"), "Output should contain 'Country:'");
    }

    @Test
    public void testMixedInputs() {
        // Test with multiple valid inputs: city/state and zip codes.
        String[] args = {"Madison, WI", "12345", "Los Angeles, CA"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for mixed valid inputs:\n" + output);
        // Verify that each valid input produces an output block.
        assertTrue(output.contains("Input: Madison, WI"), "Output should contain 'Input: Madison, WI'");
        assertTrue(output.contains("Input: 12345"), "Output should contain 'Input: 12345'");
        assertTrue(output.contains("Input: Los Angeles, CA"), "Output should contain 'Input: Los Angeles, CA'");
        // Check for common labels.
        assertTrue(output.contains("Latitude:"), "Output should contain 'Latitude:'");
        assertTrue(output.contains("Longitude:"), "Output should contain 'Longitude:'");
    }

    @Test
    public void testInvalidInputFormat() {
        // Test with an input that does not match city,state or zip code format.
        String[] args = {"InvalidInput"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for invalid input:\n" + output);
        // Expect an error message indicating invalid location format.
        assertTrue(output.contains("Invalid location format"), "Output should contain an error message for invalid format");
    }

    @Test
    public void testMultipleInputsMixed() {
        // Test with a mix of valid and invalid inputs.
        String[] args = {"Madison, WI", "InvalidInput", "10001"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for mixed valid and invalid inputs:\n" + output);
        // Verify that valid inputs produce proper output.
        assertTrue(output.contains("Input: Madison, WI"), "Output should contain 'Input: Madison, WI'");
        assertTrue(output.contains("Input: 10001"), "Output should contain 'Input: 10001'");
        // Verify that the invalid input triggers an error.
        assertTrue(output.contains("Invalid location format"), "Output should contain an error message for invalid format");
    }

    @Test
    public void testWithLocationsFlag() {
        // Test with the optional --locations flag.
        String[] args = {"--locations", "Chicago, IL", "10001"};
        String output = runMainAndCaptureAll(args);
        System.out.println("Captured output for --locations flag:\n" + output);
        // Verify that both valid inputs are processed.
        assertTrue(output.contains("Input: Chicago, IL"), "Output should contain 'Input: Chicago, IL'");
        assertTrue(output.contains("Input: 10001"), "Output should contain 'Input: 10001'");
    }
}
