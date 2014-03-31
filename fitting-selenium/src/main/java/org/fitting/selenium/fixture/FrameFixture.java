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
import org.openqa.selenium.NoSuchFrameException;

/**
 * Fixture for navigating between HTML (i)Frames.
 * <p>
 * TODO: Add management of frames via {@link org.fitting.selenium.SeleniumFrame}.
 * </p>
 */
public class FrameFixture extends SeleniumFixture {
    /**
     * Switch to a frame with the given name/id.
     * @param frameId The name or id of the frame to switch to.
     * @throws FittingException When switching to the specified frame failed.
     */
    public void switchToFrame(String frameId) throws FittingException {
        try {
            getWebDriver().switchTo().frame(frameId);
        } catch (NoSuchFrameException e) {
            throw new FormattedFittingException("No frame found with name/id " + frameId, e);
        }
    }
}
