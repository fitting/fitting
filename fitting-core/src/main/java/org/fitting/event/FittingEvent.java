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

package org.fitting.event;

import java.lang.ref.WeakReference;

/** Base class for Fitting events. */
public abstract class FittingEvent {
    /**
     * The source.
     * @TODO: Decide whether to keep a hard-reference (allowing for the source to not be garbage collected) or risk the source being garbage collected and not having the reference in the event.
     */
    private WeakReference<Object> source;
    /** The timestamp of when the event was created. */
    private final long timeStamp;

    /** Create a new event without a source. */
    public FittingEvent() {
        timeStamp = System.currentTimeMillis();
    }

    /**
     * Create a new event.
     * @param source The object creating/firing the event.
     */
    public FittingEvent(Object source) {
        this.source = new WeakReference<Object>(source);
        timeStamp = System.currentTimeMillis();
    }

    /**
     * Get the timestamp of when the event was created.
     * @return The timestamp.
     */
    public final long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get the object that created/fired the event.
     * @return The source object or <code>null</code> when that information is unavailable.
     */
    public final Object getSource() {
        Object obj = null;
        if (source != null) {
            obj = source.get();
        }
        return obj;
    }
}
