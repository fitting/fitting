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
package org.fitting;

import org.fitting.FormattedFittingException;
import org.junit.Assert;
import org.junit.Test;

public class FormattedFittingExceptionTest {

    private static final Throwable CAUSE = new Throwable();
    private static final String MESSAGE = "Message";

    @Test
    public void testMessage() {
        FormattedFittingException fittingException = new FormattedFittingException(MESSAGE);
        Assert.assertEquals("message:<<FITTING_ERROR Message>>", fittingException.getMessage());
    }

    @Test
    public void testCause() {
        FormattedFittingException fittingException = new FormattedFittingException(CAUSE);
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

    @Test
    public void testMessageCause() {
        FormattedFittingException fittingException = new FormattedFittingException(MESSAGE, CAUSE);
        Assert.assertEquals("message:<<FITTING_ERROR Message>>", fittingException.getMessage());
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

}
