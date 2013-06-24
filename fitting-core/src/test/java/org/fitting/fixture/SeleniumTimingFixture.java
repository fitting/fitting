package org.fitting.fixture;

import static org.fitting.WebDriverUtil.waitForElementPresent;
import static org.fitting.WebDriverUtil.waitForElementWithContentPresent;
import static org.fitting.WebDriverUtil.waitXSecond;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * SeleniumTimingFixture is a fixture that exposes all the WebDriverUtil timing methods.
 * These methods allow for waiting for element.
 */
public final class SeleniumTimingFixture extends SeleniumFixture {
    /**
     * Wait for the a number of seconds.
     * @param seconds The number of seconds.
     */
    public void waitSeconds(final int seconds) {
        waitXSecond(getDriver(), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param id      The id.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithIdPresentWithinSeconds(final String id, final int seconds) {
        return waitForElementPresent(getDriver(), id(id), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param name    The name.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithNamePresentWithinSeconds(final String name, final int seconds) {
        return waitForElementPresent(getDriver(), name(name), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param className The className.
     * @param seconds   The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithClassNamePresentWithinSeconds(final String className, final int seconds) {
        return waitForElementPresent(getDriver(), className(className), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param tagName The tagName.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithTagNamePresentWithinSeconds(final String tagName, final int seconds) {
        return waitForElementPresent(getDriver(), tagName(tagName), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param xpath   The xpath.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithXpathPresentWithinSeconds(final String xpath, final int seconds) {
        return waitForElementPresent(getDriver(), xpath(xpath), seconds);
    }

    /**
     * Waits for a link with the given link text to be present within the given number of seconds.
     * @param linkText linkText The link text.
     * @param seconds  The number of seconds.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithLinkTextPresentWithinSeconds(final String linkText, final int seconds) {
        return waitForElementPresent(getDriver(), linkText(linkText), seconds);
    }

    /**
     * Waits for an element with a given id and (partial) content to be present within the given number of seconds.
     * @param id      The id of the element.
     * @param content The content of the element.
     * @param seconds The number of seconds to wait.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithIdAndContentPresentWithinSeconds(final String id,
            final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), id(id), content, seconds);
    }

    /**
     * Waits for an element with a given name and (partial) content to be present within the given number of seconds.
     * @param name    The name of the element.
     * @param content The content of the element.
     * @param seconds The number of seconds to wait.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithNameAndContentPresentWithinSeconds(final String name,
            final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), name(name), content, seconds);
    }

    /**
     * Waits for an element with a given class name and (partial) content to be present within the given number of seconds.
     * @param className The class name of the element.
     * @param content   The content of the element.
     * @param seconds   The number of seconds to wait.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithClassNameAndContentPresentWithinSeconds(final String className,
            final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), className(className), content, seconds);
    }

    /**
     * Waits for an element with a given tag name and (partial) content to be present within the given number of seconds.
     * @param tagName The tagName.
     * @param content The content of the element.
     * @param seconds The number of seconds.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithTagNameAndContentPresentWithinSeconds(final String tagName,
            final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), tagName(tagName), content, seconds);
    }

    /**
     * Waits for an element with a given xpath and (partial) content to be present within the given number of seconds.
     * @param xpath   The xpath.
     * @param content The content of the element.
     * @param seconds The number of seconds.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithXpathAndContentPresentWithinSeconds(final String xpath, final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), xpath(xpath), content, seconds);
    }

    /**
     * Waits for a link with a given link text and (partial) content to be present within the given number of seconds.
     * @param linkText linkText xpath.
     * @param content  The content of the element.
     * @param seconds  The number of seconds.
     * @return <code>true</code> if the searched for element was present, <code>false</code> if not.
     */
    public boolean waitForElementWithLinkTextAndContentPresentWithinSeconds(final String linkText,
            final String content, final int seconds) {
        return waitForElementWithContentPresent(getDriver(), linkText(linkText), content, seconds);
    }

    /**
     * Waits for a specific title of the page to be present within the given number of seconds.
     * @param title   The title.
     * @param seconds The number of seconds.
     * @return <code>true</code> if the searched for title was present, <code>false</code> if not.
     */
    public boolean waitForTitlePresentWithinSeconds(final String title, final int seconds) {
        return waitForElementPresent(getDriver(), new ExpectedCondition<String>() {

            @Override
            public String apply(final WebDriver driver) {
                String t = driver.getTitle();
                if (t != null && t.equals(title)) {

                    return t;
                }
                return null;
            }
        }, seconds, null);
    }
}
