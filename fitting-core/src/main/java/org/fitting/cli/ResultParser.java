package org.fitting.cli;

/** Parser for parsing results. */
public interface ResultParser {
    /**
     * Parse the result to a {@link SuiteResult}.
     * @return The SuiteResult containing the result data.
     */
    SuiteResult parse();
}
