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

/** Singleton container that holds the FittingConnector in a ThreadLocal so each running test or suite contains its own context. */
public final class FittingContainer {
    /** ThreadLocal containing the FittingConnector. */
    private static final ThreadLocal<FittingConnector> LOCAL = new ThreadLocal<FittingConnector>();

    /** Private constructor to prevent instantiation. */
    private FittingContainer() {
    }

    /**
     * Set the FittingConnector to use for the current thread.
     *
     * @param context The FittingConnector to use..
     */
    public static void set(final FittingConnector context) {
        LOCAL.set(context);
    }

    /** Remove the FittingConnector. */
    public static void unset() {
        LOCAL.remove();
    }

    /**
     * Get the FittingConnector for the current thread.
     *
     * @return context The FitnesseContext.
     */
    public static FittingConnector get() {
        return LOCAL.get();
    }

    /**
     * Check if the connector has been initialised.
     *
     * @return <code>true</code> if the connector has been initialised.
     */
    public static boolean isInitialised() {
        return LOCAL.get() != null;
    }
}
