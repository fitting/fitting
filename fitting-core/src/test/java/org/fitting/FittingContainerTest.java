package org.fitting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FittingContainerTest {

    @Mock
    private FittingConnector fittingConnector;

    @Test
    public void testFittingContainer() {
        Assert.assertNull(FittingContainer.get());
        Assert.assertFalse(FittingContainer.isInitialised());

        FittingContainer.set(fittingConnector);
        Assert.assertEquals(fittingConnector, FittingContainer.get());
        Assert.assertTrue(FittingContainer.isInitialised());

        FittingContainer.unset();
        Assert.assertNull(FittingContainer.get());
        Assert.assertFalse(FittingContainer.isInitialised());
    }

}
