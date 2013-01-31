package org.fitting.proxy.server;

import static org.apache.commons.lang.StringUtils.startsWith;

/** Session utils. */
public final class SessionUtils {
    public static String GET = "GET";
    public static String HEAD = "HEAD";
    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String CONNECT = "CONNECT";
    public static String OPTIONS = "OPTIONS";
    public static String CONTENT_LENGTH = "CONTENT-LENGTH";
    public static String PROXY_CONNECTION = "Proxy-Connection:";
    public static String COOKIE = "Cookie:";
    public static String REFERER = "Referer:";
    public static String USER_AGENT = "User-Agent";

    /**
     * Tests what method is used with the reqest
     * @return -1 if the server doesn't support the method
     */
    public static int httpMethodId(final String httpMethod) {
        if (startsWith(httpMethod, GET) || startsWith(httpMethod, HEAD)) {
            return 0;
        } else if (startsWith(httpMethod, POST) || startsWith(httpMethod, PUT)) {
            return 1;
        } else if (startsWith(httpMethod, CONNECT)) {
            return 2;
        } else if (startsWith(httpMethod, OPTIONS)) {
            return 3;
        } else {
            return -1;
            /**
             * No match...
             * Following methods are not implemented: ||
             * startsWith(httpMethod,"TRACE")
             */
        }
    }
}
