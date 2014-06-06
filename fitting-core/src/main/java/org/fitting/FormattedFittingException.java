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

import static java.lang.String.format;

/** {@link org.fitting.FittingException} implementation that provides FitNesse formatted messages. */
public class FormattedFittingException extends FittingException {
    /** The wrapper string for clean fitnesse messages. */
    private static final String MESSAGE_WRAPPER = "message:<<FITTING_ERROR %s>>";

    /**
     * Constructor.
     * @param message The message.
     */
    public FormattedFittingException(final String message) {
        super(format(MESSAGE_WRAPPER, message));
    }

    /**
     * Constructor.
     * @param message The message.
     * @param cause The cause.
     */
    public FormattedFittingException(final String message, final Throwable cause) {
        super(format(MESSAGE_WRAPPER, message), cause);
    }

    /**
     * Constructor.
     * @param cause The cause.
     */
    public FormattedFittingException(final Throwable cause) {
        super(format(MESSAGE_WRAPPER, cause.getMessage()), cause);
    }
}
