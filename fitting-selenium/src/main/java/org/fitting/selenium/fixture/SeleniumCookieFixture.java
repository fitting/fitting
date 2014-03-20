/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting.selenium.fixture;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fitting.ElementContainer;
import org.fitting.ElementContainerProvider;
import org.fitting.FormattedFittingException;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Fixture for managing cookies in Selenium.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class SeleniumCookieFixture extends FittingSeleniumFixture {
    /** The pattern for a domain. */
    protected static final Pattern DOMAIN_PATTERN = Pattern.compile("^\\w+://([^\\/:\\?]+)([\\w\\W])*$");

    /**
     * Get the value of a cookie within the current domain..
     *
     * @param cookieName The name of the cookie.
     *
     * @return value The value of the cookie.
     */
    public String valueForCookieWithNameIs(String cookieName) {
        return getCookieValue(getWebDriver(), cookieName);
    }

    /**
     * Check if the value of a cookie within the current domain contains the provided value.
     *
     * @param cookieName The name of the cookie.
     * @param value      The value to check the cookie value for.
     *
     * @return <code>true</code> if the cookie value contains the given value.
     */
    public boolean valueForCookieWithNameContains(String cookieName, String value) {
        String cookieValue = getCookieValue(getWebDriver(), cookieName);
        return cookieValue != null && cookieValue.contains(value);
    }

    /**
     * Add a cookie with the provided name and value on the current domain.
     *
     * @param name  The name of the cookie.
     * @param value The value of the cookie.
     */
    public void addCookieWithNameAndValue(String name, String value) {
        addCookie(getWebDriver(), name, value, null, null);
    }

    /**
     * Add a cookie with the provided name and value on the provided domain.
     *
     * @param name   The name of the cookie.
     * @param value  The value of the cookie.
     * @param domain The domain to add the cookie to.
     */
    public void addCookieWithNameAndValueToDomain(String name, String value, String domain) {
        addCookie(getWebDriver(), name, value, null, domain);
    }

    /**
     * Copy a cookie from the current domain to the provided domain.
     * <p>
     * To copy the cookie, a new browser window is opened on the provided domain to set the domain cookie.
     * </p>
     *
     * @param name   The name of the cookie.
     * @param domain The target domain.
     */
    public void copyCookieWithNameToDomain(String name, String domain) {
        copyCookieToDomain(getWebDriver(), name, domain);
    }

    /**
     * Clear all cookies on the given domain.
     *
     * @param domain The domain.
     */
    public void clearAllCookiesOnDomain(String domain) {
        clearAllCookiesOnDomain(getWebDriver(), domain);
    }

    /**
     * Clear all cookies on the current domain.
     */
    public void clearAllCookies() {
        clearAllCookiesOnDomain(getWebDriver(), null);
    }

    /**
     * Deletes a cookie from the specified domain.
     * <p>
     * To delete the cookie, a new browser window is opened on the provided domain.
     * </p>
     *
     * @param name   The name of the cookie.
     * @param domain The domain.
     */
    public void deleteCookieWithNameOnDomain(String name, String domain) {
        deleteCookieWithNameOnDomain(getWebDriver(), name, domain);
    }

    /**
     * Delete a cookie from the current domain.
     *
     * @param name The name of the cookie.
     */
    public void deleteCookieWithName(String name) {
        deleteCookieWithNameOnDomain(getWebDriver(), name, null);
    }

    /**
     * Check if a cookie is present on the provided domain.
     *
     * @param name   The name of the cookie.
     * @param domain The domain.
     *
     * @return <code>true</code> if a cookie with the given name is present.
     */
    public boolean cookieWithNameIsPresentOnDomain(String name, String domain) {
        return isCookieWithNamePresentOnDomain(getWebDriver(), name, domain);
    }

    /**
     * Indicates if a cookie with the given name is present on the current domain.
     *
     * @param name The name of the cookie.
     *
     * @return <code>true</code> if a cookie with the given name is present.
     */
    public boolean cookieWithNameIsPresent(String name) {
        return isCookieWithNamePresentOnDomain(getWebDriver(), name, null);
    }

    /**
     * Get the value of a cookie.
     *
     * @param driver The web driver to use.
     * @param name   The name of the cookie.
     *
     * @return value The value of the cookie.
     */
    private String getCookieValue(WebDriver driver, String name) {
        try {
            return getCookie(driver, name).getValue();
        } catch (NullPointerException e) {
            throw new FormattedFittingException(format("No cookie with name [%s] found.", name), e);
        }
    }

    /**
     * Get a cookie.
     *
     * @param driver The Selenium web driver.
     * @param name   The name of the cookie.
     *
     * @return cookie The cookie or <code>null</code> if none was found with the given name.
     */
    private Cookie getCookie(WebDriver driver, String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Adds a cookie with the given data.
     *
     * @param driver The web driver.
     * @param name   The name of the cookie.
     * @param value  The value of the cookie.
     * @param path   The path to set the cookie.
     * @param domain The domain.
     */
    private void addCookie(final WebDriver driver, final String name, final String value, final String path, final String domain) {
        handleCookie(domain, new CookieCallback() {
            @Override
            public void execute() {
                final Cookie.Builder builder = new Cookie.Builder(name, value);
                if (isNotEmpty(path)) {
                    builder.path(path);
                }
                if (isNotEmpty(domain)) {
                    builder.domain(getStrippedDomain(domain));
                }

                driver.manage().addCookie(builder.build());
            }
        });
    }

    /**
     * Copy the cookie matching the given name to the given domain.
     *
     * @param driver The web driver.
     * @param name   The name of the cookie.
     * @param domain The domain.
     */
    private void copyCookieToDomain(WebDriver driver, String name, String domain) {
        try {
            final Cookie cookie = getCookie(driver, name);
            addCookie(driver, name, cookie.getValue(), cookie.getPath(), domain);
        } catch (NullPointerException e) {
            throw new FormattedFittingException(format("No cookie with name [%s] found.", name), e);
        }
    }

    /**
     * Clears all cookies on the provided domain.
     *
     * @param driver The web driver.
     * @param domain The domain.
     */
    private void clearAllCookiesOnDomain(final WebDriver driver, String domain) {
        handleCookie(domain, new CookieCallback() {
            @Override
            public void execute() {
                driver.manage().deleteAllCookies();
            }
        });
    }

    /**
     * Clears all cookies on the provided domain.
     *
     * @param driver The web driver.
     * @param name   The name of the cookie.
     * @param domain The domain.
     */
    private void deleteCookieWithNameOnDomain(final WebDriver driver, final String name, String domain) {
        handleCookie(domain, new CookieCallback() {
            @Override
            public void execute() {
                driver.manage().deleteCookieNamed(name);
            }
        });
    }

    /**
     * Indicates if a cookie with the given name exists on the given domain.
     *
     * @param driver The web driver.
     * @param name   The name of the cookie.
     * @param domain The domain.
     *
     * @return <code>true</code> if cookie is present, else <code>false</code>.
     */
    private boolean isCookieWithNamePresentOnDomain(final WebDriver driver, final String name, String domain) {
        final boolean[] exists = { false };
        handleCookie(domain, new CookieCallback() {
            @Override
            public void execute() {
                if (driver.manage().getCookieNamed(name) != null) {
                    exists[0] = true;
                }
            }
        });
        return exists[0];
    }

    /**
     * Based on the domain something is done with a cookie. If the domain is not null a window on that domain is opened
     * and something is done with the cookie on that domain.
     *
     * @param domain   The domain.
     * @param callback The CookieCallback.
     */
    private void handleCookie(String domain, CookieCallback callback) {
        ElementContainerProvider provider = getSeleniumConnector().getElementContainerProvider();
        if (isNotEmpty(domain) && domain.startsWith(".")) {
            throw new FormattedFittingException(format("Given domain [%s] is not valid.", domain));
        }
        ElementContainer container = null;
        if (isNotEmpty(domain)) {
            container = provider.createNewElementContainer(domain, true);
        }

        // TODO Double check that it's not needed to pass get, and pass through, the WebDriver instance for the window.
        callback.execute();

        if (container != null) {
            container.close();
        }
    }

    /** Cookie callback Interface. */
    private static interface CookieCallback {
        /** Do something with the cookie. */
        void execute();
    }

    /**
     * Strip a domain down to it's bare domain..
     *
     * @param domain The domain.
     *
     * @return strippedDomain The stripped domain.
     */
    private static String getStrippedDomain(String domain) {
        String strippedDomain = domain;
        final Matcher m = DOMAIN_PATTERN.matcher(domain);
        if (m.matches()) {
            strippedDomain = m.group(1);
        }
        return strippedDomain;
    }
}
