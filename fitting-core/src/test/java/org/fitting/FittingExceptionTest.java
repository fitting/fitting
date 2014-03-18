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

import org.fitting.FittingException;
import org.junit.Assert;
import org.junit.Test;

public class FittingExceptionTest {

    private static final Throwable CAUSE = new Throwable();
    private static final String MESSAGE = "Message";

    @Test
    public void testMessage() {
        FittingException fittingException = new FittingException(MESSAGE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
    }

    @Test
    public void testCause() {
        FittingException fittingException = new FittingException(CAUSE);
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

    @Test
    public void testMessageCause() {
        FittingException fittingException = new FittingException(MESSAGE, CAUSE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

}
