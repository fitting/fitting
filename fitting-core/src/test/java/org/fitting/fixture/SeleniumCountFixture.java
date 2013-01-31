package org.fitting.fixture;

import static org.fitting.WebDriverUtil.*;
import static org.openqa.selenium.By.*;

import java.util.List;

import org.openqa.selenium.WebElement;

public class SeleniumCountFixture extends SeleniumFixture {

	/**
	 * Count the number of elements found.
	 * 
	 * @param tagName
	 * @return number of elements found
	 */
	public int numberOfElementsWithTagNameIs(String tagName) {
		final List<WebElement> elements = getElements(getDriver(), tagName(tagName));
		return elements.size();
	}

	/**
	 * Count the number of elements found.
	 * 
	 * @param xpath
	 * @return number of elements found
	 */
	public int numberOfElementsWithXpathIs(String xpath) {
		final List<WebElement> elements = getElements(getDriver(), xpath(xpath));
		return elements.size();
	}
	
	/**
	 * Count the number of elements found.
	 * 
	 * @param className
	 * @return number of elements found
	 */
	public int numberOfElementsWithClassNameIs(String className) {
		final List<WebElement> elements = getElements(getDriver(), className(className));
		return elements.size();
	}

	/**
	 * Count the number of elements found.
	 * 
	 * @param css
	 * @return number of elements found
	 */
	public int numberOfElementsWithCssSelectorIs(String css) {
		final List<WebElement> elements = getElements(getDriver(), cssSelector(css));
		return elements.size();
	}
}
