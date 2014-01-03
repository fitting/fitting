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

/**
 * Exception for when an element could not been found.
 */
public class NoSuchElementException extends FormattedFittingException {
    private static final long serialVersionUID = 1L;

    /**
     * The default message.
     */
    private static final String NO_ELEMENT_MESSAGE = "No element found on search context %s matching by-clause %s";

    /**
     * Create a new NoSuchElementException.
     *
     * @param searchContext The search context where the element was searched on.
     * @param byClause      The By-clause used.
     */
    public NoSuchElementException(final SearchContext searchContext, final By byClause) {
        super(format(NO_ELEMENT_MESSAGE, searchContext.toString(), byClause.toString()));
    }
}
