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

import org.fitting.ElementContainer;

import java.lang.ref.WeakReference;

/** Event to signal a {@link org.fitting.ElementContainer} location was changed. */
public class LocationChangedEvent extends FittingEvent {
    /**
     * The container.
     * @TODO: Decide whether to keep a hard-reference (allowing for the container to not be garbage collected) or risk the container being garbage collected and not having the reference in the event.
     */
    private final WeakReference<ElementContainer> container;
    /** The old location. */
    private final String oldLocation;
    /** The new location. */
    private final String newLocation;

    /**
     * Create a new LocationChangedEvent.
     * @param container   The container of which the location was changed.
     * @param oldLocation The old location.
     * @param newLocation The new location.
     */
    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation) {
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    /**
     * Create a new LocationChangedEvent.
     * @param container   The container of which the location was changed.
     * @param oldLocation The old location.
     * @param newLocation The new location.
     * @param source      The object that created/fired the event.
     */
    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    /**
     * Get the container for which the location was changed.
     * @return The container.
     */
    public ElementContainer getElementContainer() {
        return container.get();
    }

    /**
     * Get the old location.
     * @return The old location.
     */
    public String getOldLocation() {
        return oldLocation;
    }

    /**
     * Get the new location.
     * @return The new location.
     */
    public String getNewLocation() {
        return newLocation;
    }
}
