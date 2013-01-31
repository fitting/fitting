package org.fitting.cli;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/** {@link ResultParser} implementation for parsing a HttpResponse object. */
public class HttpResponseResultParser implements ResultParser {
    /** Opening tag for finalCounts. */
    private static final String TAG_FINALCOUNTS_START = "<finalCounts>";
    /** Closing tag for finalCounts. */
    private static final String TAG_FINALCOUNTS_END = "</finalCounts>";
    /** Opening tag for right. */
    private static final String TAG_RIGHT_START = "<right>";
    /** Closing tag for finalCounts. */
    private static final String TAG_RIGHT_END = "</right>";
    /** Opening tag for wrong. */
    private static final String TAG_WRONG_START = "<wrong>";
    /** Closing tag for wrong. */
    private static final String TAG_WRONG_END = "</wrong>";
    /** Opening tag for ignores. */
    private static final String TAG_IGNORES_START = "<ignores>";
    /** Closing tag for ignores. */
    private static final String TAG_IGNORES_END = "</ignores>";
    /** Opening tag for exceptions. */
    private static final String TAG_EXCEPTIONS_START = "<exceptions>";
    /** Closing tag for exceptions. */
    private static final String TAG_EXCEPTIONS_END = "</exceptions>";
    /** Opening tag for rootPath. */
    private static final String TAG_ROOTPATH_START = "<rootPath>";
    /** Closing tag for rootPath. */
    private static final String TAG_ROOTPATH_END = "</rootPath>";
    /** The response body. */
    private final String response;

    /**
     * Create a new HttpResponseResultParser.
     * @param response The response object to parse.
     */
    public HttpResponseResultParser(final HttpResponse response) {
        this.response = getResponseBody(response);
    }

    /**
     * Get the response body as a string.
     * @param response The HttpResponse object to parse.
     * @return The body String.
     */
    private String getResponseBody(final HttpResponse response) {
        final StringBuffer result = new StringBuffer();
        final HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                final InputStream stream = entity.getContent();
                try {
                    int b = 0;
                    while ((b = stream.read()) > 0) {
                        result.append((char) b);
                    }
                } finally {
                    stream.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error parsing response.", e);
        }
        return result.toString();
    }

    /**
     * Get the name of the suite from the response body.
     * @param response The response body.
     * @return The name of the suite.
     */
    private String getSuiteName(final String response) {
        return getSubString(response, TAG_ROOTPATH_START, TAG_ROOTPATH_END);
    }

    /**
     * Get the block of final suite test counts.
     * @param response The response body.
     * @return The final counts.
     */
    private String getFinalCounts(final String response) {
        return getSubString(response, TAG_FINALCOUNTS_START, TAG_FINALCOUNTS_END);
    }

    /**
     * Get the number of tests that went right from a counts block.
     * @param response The counts block.
     * @return The number of tests.
     * @see HttpResponseResultParser#getFinalCounts(String)
     */
    private int getRights(final String response) {
        return Integer.parseInt(getSubString(response, TAG_RIGHT_START, TAG_RIGHT_END));
    }

    /**
     * Get the number of tests that went wrong from a counts block.
     * @param response The counts block.
     * @return The number of tests.
     * @see HttpResponseResultParser#getFinalCounts(String)
     */
    private int getWrongs(final String response) {
        return Integer.parseInt(getSubString(response, TAG_WRONG_START, TAG_WRONG_END));
    }

    /**
     * Get the number of tests that were ignored from a counts block.
     * @param response The counts block.
     * @return The number of tests.
     * @see HttpResponseResultParser#getFinalCounts(String)
     */
    private int getIgnores(final String response) {
        return Integer.parseInt(getSubString(response, TAG_IGNORES_START, TAG_IGNORES_END));
    }

    /**
     * Get the number of tests that generated exceptions  from a counts block.
     * @param response The counts block.
     * @return The number of tests.
     * @see HttpResponseResultParser#getFinalCounts(String)
     */
    private int getExceptions(final String response) {
        return Integer.parseInt(getSubString(response, TAG_EXCEPTIONS_START, TAG_EXCEPTIONS_END));
    }

    /**
     * Get the text from the response that is located between the start-tag and end-tag.
     * @param response The response.
     * @param startTag The start tag.
     * @param endTag   The end tag.
     * @return The block.
     */
    private String getSubString(final String response, final String startTag, final String endTag) {
        final int start = response.indexOf(startTag) + startTag.length();
        final int end = response.indexOf(endTag);
        return response.substring(start, end);
    }

    @Override
    public SuiteResult parse() {
        final String suite = getSuiteName(response);
        final String finalCounts = getFinalCounts(response);
        return new SuiteResult(suite, getRights(finalCounts), getWrongs(finalCounts), getIgnores(finalCounts),
                getExceptions(finalCounts));
    }
}
