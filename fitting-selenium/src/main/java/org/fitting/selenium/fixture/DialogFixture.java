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

import org.fitting.FittingException;
import org.fitting.FormattedFittingException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

/**
 * Fixture for manipulating HTML dialogs, such as alerts or printing dialogs.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class DialogFixture extends SeleniumFixture {
    /** Javascript for suppressing the print dialog and replacing it with a checkable variable. */
    private static String SUPPRESS_PRINT_DIALOG_JAVASCRIPT = "window.WebDriverPrintFunctionCalled = false;" +
            "window.print = function () { window.WebDriverPrintFunctionCalled = true; }";
    private static String CHECK_PRINT_DIALOG_JAVASCRIPT = "return window.WebDriverPrintFunctionCalled;";

    /**
     * Check if an alert window is present.
     *
     * <p>
     * FitNesse usage:
     * <pre>| alert is present |</pre>
     * </p>
     *
     * @return <code>true</code> if an alert window is present, <code>false</code>  if not.
     */
    public boolean alertIsPresent() {
        return isAlertPresent();
    }

    /**
     * Get the text from an alert window.
     *
     * <p>
     * FitNesse usage:
     * <pre>| text from alert |</pre>
     * </p>
     *
     * @return The text.
     *
     * @throws FittingException When no alert window was present.
     */
    public String textFromAlert() throws FittingException {
        return getAlert().getText();
    }

    /**
     * Click <code>cancel</code> on the alert window, dismissing it.
     *
     * <p>
     * FitNesse usage:
     * <pre>| click cancel for alert window |</pre>
     * </p>
     *
     * @throws FittingException When no alert window was present.
     */
    public void clickCancelForAlertWindow() throws FittingException {
        getAlert().dismiss();
    }

    /**
     * Click <code>accept</code> on the alert window.
     *
     * <p>
     * FitNesse usage:
     * <pre>| click accept for alert window |</pre>
     * </p>
     *
     * @throws FittingException When no alert window was present.
     */
    public void clickAcceptForAlertWindow() throws FittingException {
        getAlert().accept();
    }

    /**
     * Suppress the browser's print dialog and set a variable, that can be read via {@link DialogFixture#printDialogWasCalled()}, instead.
     *
     * <p>
     * FitNesse usage:
     * <pre>| expectPrintDialogToAppear |</pre>
     * </p>
     *
     * @throws FittingException When the underlying WebDriver does not support execution of JavaScript.
     * @see DialogFixture#printDialogWasCalled()
     */
    public void expectPrintDialogToAppear() throws FittingException {
        executeJavascript(SUPPRESS_PRINT_DIALOG_JAVASCRIPT);
    }

    /**
     * Check if a suppressed print dialog (see {@link DialogFixture#expectPrintDialogToAppear()}) was called.
     *
     * <p>
     * FitNesse usage:
     * <pre>| print dialog was called |</pre>
     * </p>
     *
     * @return <code>true</code> if a suppressed print dialog was called.
     *
     * @throws FittingException When the underlying WebDriver does not support execution of JavaScript.
     * @see DialogFixture#expectPrintDialogToAppear()
     */
    public boolean printDialogWasCalled() throws FittingException {
        Object returnVal = executeJavascript(CHECK_PRINT_DIALOG_JAVASCRIPT);
        return (returnVal instanceof Boolean) ? ((Boolean) returnVal).booleanValue() : false;
    }

    /**
     * Check if an alert window is present.
     *
     * @return <code>true</code> if an alert window is present.
     */
    private boolean isAlertPresent() {
        boolean present;
        try {
            present = (getAlert() != null);
        } catch (FittingException e) {
            present = false;
        }
        return present;
    }

    /**
     * Get the alert window.
     *
     * @return The alert window.
     *
     * @throws FittingException When no alert window was present.
     */
    private Alert getAlert() throws FittingException {
        Alert alert;
        try {
            alert = getWebDriver().switchTo().alert();
        } catch (NoAlertPresentException e) {
            throw new FormattedFittingException("No alert window present.", e);
        }
        return alert;
    }
}
