package org.fitting.fixture;

import static org.fitting.WebDriverUtil.waitForElementPresent;
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
     * Wait for the given milliseconds.
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
    public boolean waitForElementWithIdPresentForSeconds(final String id, final int seconds) {
        return waitForElementPresent(getDriver(), id(id), seconds);

    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param name    The name.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithNamePresentForSeconds(final String name, final int seconds) {
        return waitForElementPresent(getDriver(), name(name), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param className The className.
     * @param seconds   The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithClassNamePresentForSeconds(final String className, final int seconds) {
        return waitForElementPresent(getDriver(), className(className), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param tagName The tagName.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithTagNamePresentForSeconds(final String tagName, final int seconds) {
        return waitForElementPresent(getDriver(), tagName(tagName), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param xpath   The xpath.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithXpathPresentForSeconds(final String xpath, final int seconds) {
        return waitForElementPresent(getDriver(), xpath(xpath), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param linkText linkText xpath.
     * @param seconds  The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithLinkTextPresentForSeconds(final String linkText, final int seconds) {
        return waitForElementPresent(getDriver(), linkText(linkText), seconds);
    }

	public boolean waitForElementWithIdAndContentPresentForSeconds(final String id,
			final String content, final int seconds) {
		return waitForElementPresent(getDriver(), id(id), content, seconds);
	}

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param name    The name.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithNameAndContentPresentForSeconds(final String name,
		final String content, final int seconds) {
        return waitForElementPresent(getDriver(), name(name), content, seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param className The className.
     * @param seconds   The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithClassNameAndContentPresentForSeconds(final String className,
		final String content, final int seconds) {
        return waitForElementPresent(getDriver(), className(className), content, seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param tagName The tagName.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithTagNameAndContentPresentForSeconds(final String tagName,
		final String content, final int seconds) {
        return waitForElementPresent(getDriver(), tagName(tagName), content, seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param xpath   The xpath.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithXpathAndContentPresentForSeconds(final String xpath,
		final String content, final int seconds) {
        return waitForElementPresent(getDriver(), xpath(xpath), content, seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param linkText linkText xpath.
     * @param seconds  The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     */
    public boolean waitForElementWithLinkTextAndContentPresentForSeconds(final String linkText,
            final String content, final int seconds) {
        return waitForElementPresent(getDriver(), linkText(linkText), content, seconds);
    }
    
    public boolean waitForTitlePresentForSeconds(final String title,
            final int seconds) {
        return waitForElementPresent(getDriver(), new ExpectedCondition<String>() {

            @Override public String apply(final WebDriver driver) {
                String t = driver.getTitle();
                if (t != null && t.equals(title)) {
                    
                    return t;
                }
                return null;
            }
        }, seconds, null);
    }
}
