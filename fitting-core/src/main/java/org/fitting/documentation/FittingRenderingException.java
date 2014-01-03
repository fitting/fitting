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

package org.fitting.documentation;

import org.fitting.FittingException;

/**
 * Exception for rendering related exceptions.
 */
public class FittingRenderingException extends FittingException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception.
     *
     * @param message The exception message.
     */
    public FittingRenderingException(final String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message The exception message.
     * @param cause   The cause-exception.
     */
    public FittingRenderingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new exception.
     *
     * @param cause The cause-exception.
     */
    public FittingRenderingException(final Throwable cause) {
        super(cause);
    }
}
