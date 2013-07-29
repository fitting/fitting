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

import org.fitting.ElementContainer;

/**
 * Event to signal a {@link ElementContainer} was created.
 */
public class ContainerCreatedEvent extends FittingEvent {
    /**
     * The created container.
     *
     * <p>
     * TODO:
     * Decide whether to keep a hard-reference (allowing for the container to not be garbage collected) or risk the container being garbage collected and not having the
     * reference in the event.
     * </p>
     */
    private WeakReference<ElementContainer> container;

    /**
     * Create a new ContainerCreatedEvent.
     *
     * @param container The created container.
     */
    public ContainerCreatedEvent(ElementContainer container) {
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Create a new ContainerCreatedEvent.
     *
     * @param container The created container.
     * @param source    The object firing the event.
     */
    public ContainerCreatedEvent(ElementContainer container, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Get the created element container.
     *
     * @return The created element container.
     */
    public ElementContainer getElementContainer() {
        return container.get();
    }
}