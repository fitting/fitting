package org.fitting.cli;

import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

/** Unit test for {@link SuiteResult}. */
public class SuiteResultTest {
    private static final double ALLOWED_DOUBLE_DELTA = 0d;
    private static final int EXCEPTIONS = 5;
    private static final double EXCEPTIONS_PERCENT = 25d;
    private static final int SKIPPED = 20;
    private static final int FAILED = 5;
    private static final double FAILED_PERCENT = 25d;
    private static final int PASSED = 10;
    private static final double PASSED_PERCENT = 50d;
    private static final int TOTAL = 20;
    private static final int TOTAL_ABSOLUTE = 40;
    private static final String NAME = "testSuite";
    private static final String TO_STRING = format("Results for suite %s: [total: %d, passed: %d, " +
            "failed: %d, skipped: %d, exceptions: %d].", NAME, TOTAL_ABSOLUTE, PASSED, FAILED, SKIPPED, EXCEPTIONS);

    /** The object under test. */
    private SuiteResult result;

    @Before
    public void setUp() throws Exception {
        this.result = new SuiteResult(NAME, PASSED, FAILED, SKIPPED, EXCEPTIONS);
    }

    @Test
    public void shouldInitialise() throws Exception {
        assertEquals("Name not properly set.", NAME, result.getSuiteName());
        assertEquals("Passed tests not properly set.", PASSED, result.numberOfTestsPassed());
        assertEquals("Failed tests not properly set.", FAILED, result.numberOfTestsFailed());
        assertEquals("Skipped tests not properly set.", SKIPPED, result.numberOfTestsSkipped());
        assertEquals("Exceptions in tests not properly set.", EXCEPTIONS, result.numberOfTestsWithExceptions());
    }

    @Test
    public void shouldCalculateTotalNumberOfExecutedTests() throws Exception {
        assertEquals("Total not calculated", TOTAL, result.numberOfTests());
    }

    @Test
    public void shouldCalculateAbsoluteTotalNumberOfTests() throws Exception {
        assertEquals("Absolute total not calculated", TOTAL_ABSOLUTE, result.absoluteNumberOfTests());
    }

    @Test
    public void shouldCalculatePercentagePassedTests() throws Exception {
        assertEquals("Percentage of passed tests not calculated", PASSED_PERCENT, result.percentageOfTestsPassed(),
                ALLOWED_DOUBLE_DELTA);
    }

    @Test
    public void shouldCalculatePercentageFailedTests() throws Exception {
        assertEquals("Percentage of passed tests not calculated", FAILED_PERCENT, result.percentageOfTestsFailed(),
                ALLOWED_DOUBLE_DELTA);
    }

    @Test
    public void shouldCalculatePercentageTestsWithExceptions() throws Exception {
        assertEquals("Percentage of tests with exceptions not calculated", EXCEPTIONS_PERCENT,
                result.percentageOfTestsWithExceptions(),
                ALLOWED_DOUBLE_DELTA);
    }

    @Test
    public void shouldPrintPrettyString() throws Exception {
        assertEquals("toString not printing expected format", TO_STRING, result.toString());
    }
}
