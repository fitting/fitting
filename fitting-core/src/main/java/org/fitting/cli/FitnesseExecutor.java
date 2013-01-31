package org.fitting.cli;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import static java.lang.String.format;

/** TODO Document class. */
public class FitnesseExecutor {
    private static final String FULL_REQUEST_MASK = "/%s?suite&format=xml";
    private final String host;
    private final int port;
    private final String suite;

    public FitnesseExecutor(final String host, final int port, final String suite) {
        this.host = host;
        this.port = port;
        this.suite = suite;
    }

    public void execute() {
        SuiteResult result = null;
        try {
            result = executeTests();
            printResult(result);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        exitBasedOnResult(result);
    }

    private void printResult(final SuiteResult result) {
        if (result == null) {
            System.out.println("Not results retrieved, exiting.");
        } else {
            System.out.println(format("Results of %s (%d tests) [", result.getSuiteName(), result.numberOfTests()));
            System.out.println(format("    Passed     : [%d / %.1f%%]", result.numberOfTestsPassed(),
                    result.percentageOfTestsPassed()));
            System.out.println(format("    Failed     : [%d / %.1f%%]", result.numberOfTestsFailed(),
                    result.percentageOfTestsFailed()));
            System.out.println(format("    Exceptions : [%d / %.1f%%]", result.numberOfTestsWithExceptions(),
                    result.percentageOfTestsWithExceptions()));
            System.out.println(format("]"));
        }
    }

    private void exitBasedOnResult(final SuiteResult result) {
        if (result == null) {
            System.exit(3);
        } else if (result.numberOfTestsFailed() > 0) {
            System.exit(1);
        } else if (result.numberOfTestsWithExceptions() > 0) {
            System.exit(2);
        } else {
            System.exit(0);
        }
    }

    private SuiteResult executeTests() throws IOException {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet request = new HttpGet(format(FULL_REQUEST_MASK, this.suite));
        final HttpHost host = new HttpHost(this.host, this.port);
        final HttpResponse response = client.execute(host, request);
        final ResultParser parser = new HttpResponseResultParser(response);
        return parser.parse();
    }

    public static void main(String[] args) {
        if (args != null && args.length == 0) {
            final String host = "127.0.0.1";
            final int port = 8005;
            final String suite = "FitNesse.SuiteAcceptanceTests.SuiteSlimTests";
            new FitnesseExecutor(host, port, suite).execute();
        } else if (args == null || args.length != 3) {
            printHelp();
        } else {
            try {
                final String host = args[0];
                final int port = Integer.parseInt(args[1]);
                final String suite = args[2];
                new FitnesseExecutor(host, port, suite).execute();
            } catch (NumberFormatException e) {
                printHelp();
            }
        }
    }

    private static void printHelp() {
        System.out.println("Execute a FitNesse test suite and print the results, using a system exit code when the " +
                "tests failed.");
        System.out.println("Usage [java -cp <classpath> org.fitting.cli.SuitResult <host> <port> <suite>]");
        System.out.println("    <classpath>: The classpath containing the fitnesse-core-util classes, " +
                "commons-lang and httpclient");
        System.out.println("    <host>     : The host of the FitNesse server (e.g. 127.0.0.1).");
        System.out.println("    <port>     : The port of the FitNesse server (e.g. 8005).");
        System.out.println("    <suite>    : The suite page to run in FitNesse notation (e.g. FitNesse.MyProject" +
                ".MySuite.");
    }
}
