package org.fitting.cli;

import static java.lang.String.format;

/** Test results of an executed test suite. */
public class SuiteResult {
    /** The suite name. */
    private final String suite;
    /** The number of tests that passed. */
    private final int passed;
    /** The number of tests that failed. */
    private final int failed;
    /** The number of tests that were skipped. */
    private final int skipped;
    /** The number of tests with exceptions. */
    private final int exceptions;

    /**
     * Create a new SuiteResult instance.
     * @param suite      The suite name.
     * @param passed     The number of tests that passed.
     * @param failed     The number of tests that failed.
     * @param skipped    The number of tests that were skipped
     * @param exceptions The number of tests with exceptions.
     */
    public SuiteResult(final String suite, final int passed, final int failed, final int skipped,
            final int exceptions) {
        this.suite = suite;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
        this.exceptions = exceptions;
    }

    /**
     * Get the name of the test suite.
     * @return The name.
     */
    public String getSuiteName() {
        return suite;
    }

    /**
     * Get the number of tests that passed.
     * @return The number of tests.
     */
    public int numberOfTestsPassed() {
        return passed;
    }

    /**
     * Get the percentage of tests that passed. <p> 10% is returned as 10.0. </p>
     * @return The percentage.
     */
    public double percentageOfTestsPassed() {
        return getPercentage(passed, numberOfTests());
    }

    /**
     * Get the number of tests that failed.
     * @return The number of tests.
     */
    public int numberOfTestsFailed() {
        return failed;
    }

    /**
     * Get the percentage of tests that failed. <p> 10% is returned as 10.0. </p>
     * @return The percentage.
     */
    public double percentageOfTestsFailed() {
        return getPercentage(failed, numberOfTests());
    }

    /**
     * Get the number of tests that were skipped.
     * @return The number of tests.
     */
    public int numberOfTestsSkipped() {
        return skipped;
    }

    /**
     * Get the number of tests that failed due to exceptions.
     * @return The number of tests.
     */
    public int numberOfTestsWithExceptions() {
        return exceptions;
    }

    /**
     * Get the percentage of tests that failed due to exceptions. <p> 10% is returned as 10.0. </p>
     * @return The percentage.
     */
    public double percentageOfTestsWithExceptions() {
        return getPercentage(exceptions, numberOfTests());
    }

    /**
     * Get the total number of tests executed, excluding the skipped tests.
     * @return The number of tests.
     */
    public int numberOfTests() {
        return (passed + failed + exceptions);
    }

    /**
     * Get the absolute number of tests that were executed, including those skipped.
     * @return The number of tests.
     */
    public int absoluteNumberOfTests() {
        return (passed + failed + skipped + exceptions);
    }

    /**
     * Get the percentage.
     * @param count The count to get a percentage for.
     * @param total The total to get the percentage for.
     * @return The percentage.
     */
    private double getPercentage(final int count, final int total) {
        return ((double) count / (double) total) * 100;
    }

    @Override
    public String toString() {
        return format("Results for suite %s: [total: %d, passed: %d, failed: %d, skipped: %d, exceptions: %d].",
                suite, absoluteNumberOfTests(), numberOfTestsPassed(), numberOfTestsFailed(),
                numberOfTestsSkipped(), numberOfTestsWithExceptions());
    }
}
