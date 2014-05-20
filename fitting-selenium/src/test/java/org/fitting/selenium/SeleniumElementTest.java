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
    private static final String TEXTELEMENT_TAG = "p";
    private static final String TEXTELEMENT_TEXT = "text";
    private static final String INPUT_TAG = "input";
    private static final String INPUT_TYPE = "checkbox";
    private static final String INPUT_VALUE = "checkboxValue";
    private static final String INPUT_TEXT = "checkboxText";

    @Mock
    private WebElement textElement;
    @Mock
    private WebElement inputElement;
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

        when(textElement.getTagName()).thenReturn(TEXTELEMENT_TAG);
        when(textElement.getText()).thenReturn(TEXTELEMENT_TEXT);

        when(inputElement.getTagName()).thenReturn(INPUT_TAG);

        when(fittingSeleniumConnector.getWebDriver()).thenReturn(webDriver);
        when(fittingSeleniumConnector.getDefaultSearchContext()).thenReturn(new WebDriverSearchContext(webDriver));

        when(FittingContainer.get()).thenReturn(fittingSeleniumConnector);
    }

    /**
     * Given a new SeleniumElement wrapping a Selenium web element.<br/>
     * When {@link SeleniumElement#getImplementation()} is called.<br/>
     * Then the returned web element should be the same object instance (<code>==</code>) as the provided web element.
     *
     * @see SeleniumElement#SeleniumElement(WebElement)
     * @see SeleniumElement#getImplementation()
     */
    @Test
    public void shouldStoreUnderlyingImplementation() {
        SeleniumElement instance = new SeleniumElement(textElement);

        assertSame(textElement, instance.getImplementation());
    }

    /**
     * Given a SeleniumElement wrapping a Selenium web element.<br/>
     * When {@link SeleniumElement#getName()} is called.<br/>
     * Then the returned name should be the HTML tag of the web element.
     *
     * @see SeleniumElement#getName()
     */
    @Test
    public void shouldGetElementTagNameAsName() {
        when(textElement.getTagName()).thenReturn("tagName");
        SeleniumElement instance = new SeleniumElement(textElement);

        assertEquals("tagName", instance.getName());
        verify(textElement).getTagName();
    }

    /**
     * Given a SeleniumElement wrapping a Selenium web element.<br/>
     * When {@link SeleniumElement#getType()} is called.<br/>
     * Then the returned type should be the HTML tag of the web element.
     *
     * @see SeleniumElement#getType()
     */
    @Test
    public void shouldGetElementTagAsType() {
        when(textElement.getTagName()).thenReturn("tagName");
        SeleniumElement instance = new SeleniumElement(textElement);

        assertEquals("tagName", instance.getType());
        verify(textElement).getTagName();
    }
}
