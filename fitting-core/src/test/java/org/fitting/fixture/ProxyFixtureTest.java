package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/** Test class for ProxyFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProxyFixture.class, FitnesseContainer.class, FitnesseContext.class, SeleniumConnector.class})
public class ProxyFixtureTest {
    private ProxyFixture fixture; // class under test

    @Mock
    private FitnesseContext context;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        fixture = new ProxyFixture();
        when(FitnesseContainer.get()).thenReturn(context);
        whenNew(FitnesseContext.class).withArguments(any(SeleniumConnector.class)).thenReturn(context);
    }

    @Test
    public void shouldNotAddDomainMappingOnSetIp() throws Exception {
        fixture.setIp("ip");
        verify(context, never()).addIpDomainMapping(isA(String.class), isA(String.class));
    }

    @Test
    public void shouldAddDomainMappingOnSetDomain() throws Exception {
        fixture.setIp("ip");
        fixture.setDomain("domain");
        verify(context, times(1)).addIpDomainMapping(eq("ip"), eq("domain"));
    }
}
