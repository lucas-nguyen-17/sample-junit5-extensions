package com.giangtester.extensions;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TraceFailedTestListener implements TestExecutionListener {

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED) {
            Path resultFile = Path.of("src/test/resources/result.txt");
            String body = "";
            try {
                if (Files.exists(resultFile)) {
                    body = Files.readString(resultFile);
                } else {
                    Files.createFile(resultFile);
                }
                String testName = testIdentifier.getDisplayName().replace("()", "");
                if (!body.contains(testName)) {
                    body = body + testName + "\n";
                }
                Files.writeString(resultFile, body);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
