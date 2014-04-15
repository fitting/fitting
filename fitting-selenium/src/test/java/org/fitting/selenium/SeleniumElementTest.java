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

package org.fitting.selenium;

import org.fitting.FittingContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Unit tests for {@link org.fitting.selenium.SeleniumElement}.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FittingContainer.class, SeleniumUtil.class})
public class SeleniumElementTest {
    @Mock
    private WebElement element;
    @Mock
    private FittingSeleniumConnector fittingSeleniumConnector;
    @Mock
    private WebDriver webDriver;

    /**
     * Set up the mocks and environment for the unit tests.
     * <p>
     * This method is executed before each test.
     * </p>
     *
     * @throws Exception When setup failed.
     */
    @Before
    public void setUp() throws Exception {
        mockStatic(SeleniumUtil.class);
        mockStatic(FittingContainer.class);

        when(fittingSeleniumConnector.getWebDriver()).thenReturn(webDriver);
        when(fittingSeleniumConnector.getDefaultSearchContext()).thenReturn(new WebDriverSearchContext(webDriver));

        when(FittingContainer.get()).thenReturn(fittingSeleniumConnector);
    }

    @Test
    public void shouldStoreUnderlyingImplementation() {
        SeleniumElement instance = new SeleniumElement(element);

        assertSame(element, instance.getImplementation());
    }

    @Test
    public void shouldGetElementName() {
        when(element.getTagName()).thenReturn("name");
        SeleniumElement instance = new SeleniumElement(element);

        assertEquals("name", instance.getName());
        verify(element).getTagName();
    }

    // public String getName()
    // public String getType()
    // public String getText()
    // public String getValue()
    // public void click()
    // public void sendKeys(final CharSequence... characters)
    // public void setValue(final String value) throws FittingException
    // public void setValueWithText(final String value) throws FittingException
    // public void clear()
    // public String getAttributeValue(String attributeName)
    // public boolean isActive()
    // public boolean isDisplayed()
    // public Point getLocation()
    // public Dimension getSize()
    // public boolean isInput()
    // public List<Element> findElementsBy(final Selector selector)
    // public Element findElementBy(final Selector selector)
    // public void waitForElement(final Selector selector, final int timeout) throws NoSuchElementException
    // public void waitForElementWithContent(final Selector selector, final String content, final int timeout) throws NoSuchElementException
    // private WebDriver getDriver() throws IllegalArgumentException

}
