package com.salesforce.tests.fs;

import org.junit.Test;

import java.io.IOException;

/**
 * Place holder for your unit tests
 */
public class TouchTest extends BaseTest {

    @Test
    public void testTouchSuccessCmd() throws IOException {
        String fileName = "tstFss345dd";

        String[] expectedResults = {
                ".\n..\n" + fileName
        };

        runTest(expectedResults, "touch " + fileName, "ls", "quit");
    }

    @Test
    public void testTouchErrorCmd() throws IOException {
        String fileName = "tstFss%";

        String[] expectedResults = {
                "Invalid fileName"
        };

        runTest(expectedResults, "touch " + fileName, "quit");
    }

    @Test
    public void testTouchProtectedCmd() throws IOException {
        String fileName = "root";

        String[] expectedResults = {
                "File/Directory "+fileName+" already exists"
        };

        runTest(expectedResults, "touch " + fileName, "quit");
    }

    @Test
    public void testTouchExistsCmd() throws IOException {
        String fileName = "tstFsss";

        String[] expectedResults = {
                "File/Directory " + fileName + " already exists"
        };

        runTest(expectedResults, "touch " + fileName, "touch " + fileName, "quit");
        runTest(expectedResults, "touch " + fileName, "mkdir " + fileName, "quit");
    }
}
