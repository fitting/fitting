package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.WebDriverUtil;

import static org.fitting.WebDriverUtil.addCookie;
import static org.fitting.WebDriverUtil.copyCookieToDomain;
import static org.fitting.WebDriverUtil.getCookieValue;
import static org.fitting.WebDriverUtil.isCookieWithNamePresentOnDomain;

/**
 * SeleniumCookieFixture is a fixture that exposes all the WebDriverUtil cookie methods.
 * These methods allow for interaction and manipulation of browser cookies.
 */
public class SeleniumCookieFixture extends SeleniumFixture {

    /**
     * Gets the value of the cookie matching the given cookie name.
     * @param cookieName The name of the cookie.
     * @return value The value of the cookie.
     */
    public String valueForCookieWithNameIs(final String cookieName) {
        return getCookieValue(getDriver(), cookieName);
    }

    /**
     * Indicates if the given cookie value contains the given value.
     * @param cookieName The name of the cookie.
     * @param contains   The value to contain.
     * @return <code>true</code> if the cookie value contains the given value, else <code>false</code>.
     */
    public boolean valueForCookieWithNameContains(final String cookieName, final String contains) {
        return WebDriverUtil.cookieValueContains(getDriver(), cookieName, contains);
    }

    /**
     * Adds a cookie with the given name and value on the given domain.
     * @param name  The name of the cookie.
     * @param value The value of the cookie.
     */
    public void addCookieWithNameAndValue(final String name, final String value) {
        addCookie(FitnesseContainer.get(), getDriver(), name, value, null, null);
    }

    /**
     * Adds a cookie with the given name and value on the given domain.
     * @param name  The name of the cookie.
     * @param value The value of the cookie.
     */
    public void addCookieWithNameAndValueToDomain(final String name, final String value, final String domain) {
        addCookie(FitnesseContainer.get(), getDriver(), name, value, null, domain);
    }

    /**
     * Copy the cookie matching the given name to the given domain.
     * To do so we should open the domain in a seperate window so we can set the domain cookie.
     * @param name   The name of the cookie.
     * @param domain The domain of the cookie.
     */
    public void copyCookieWithNameToDomain(final String name, final String domain) {
        copyCookieToDomain(FitnesseContainer.get(), getDriver(), name, domain);
    }

    /**
     * Clears all cookies on the given domain.
     * @param domain The domain.
     */
    public void clearAllCookiesOnDomain(final String domain) {
        WebDriverUtil.clearAllCookiesOnDomain(FitnesseContainer.get(), getDriver(), domain);
    }

    /** Clears all cookies on the current domain. */
    public void clearAllCookies() {
        WebDriverUtil.clearAllCookiesOnDomain(FitnesseContainer.get(), getDriver(), null);

    }

    /**
     * Deletes the cookie with the given name.
     * @param cookieName The cookie name.
     * @param domain     The domain.
     */
    public void deleteCookieWithNameOnDomain(final String cookieName, final String domain) {
        WebDriverUtil.deleteCookieWithNameOnDomain(FitnesseContainer.get(), getDriver(), cookieName, domain);
    }

    /**
     * Deletes the cookie with the given name.
     * @param cookieName The cookie name.
     */
    public void deleteCookieWithName(final String cookieName) {
        WebDriverUtil.deleteCookieWithNameOnDomain(FitnesseContainer.get(), getDriver(), cookieName, null);
    }

    /**
     * Indicates if a cookie with the given name is present on the given domain.
     * @param cookieName The name of the cookie.
     * @param domain     The domain.
     * @return <code>true</code> if cookie with given name exists, else <code>false</code>.
     */
    public boolean cookieWithNameOnDomainIsPresent(final String cookieName, final String domain) {
        return isCookieWithNamePresentOnDomain(FitnesseContainer.get(), getDriver(), cookieName, domain);
    }

    /**
     * Indicates if a cookie with the given name is present on the current domain.
     * @param cookieName The name of the cookie.
     * @return <code>true</code> if cookie with given name exists, else <code>false</code>.
     */
    public boolean cookieWithNameIsPresent(final String cookieName) {
        return isCookieWithNamePresentOnDomain(FitnesseContainer.get(), getDriver(), cookieName, null);
    }
}
