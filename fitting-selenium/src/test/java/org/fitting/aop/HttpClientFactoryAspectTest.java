package org.fitting.aop;

import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.io.StringWriter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** Test class for HttpClientFactoryAspect. */
@RunWith(MockitoJUnitRunner.class)
public class HttpClientFactoryAspectTest {
    private HttpClientFactoryAspect aspect;
    @Mock
    private DefaultHttpClient httpClient;
    @Mock
    private PrintStream out;
    private StringWriter writer;

    @Before
    public void setUp() throws Exception {
        aspect = new HttpClientFactoryAspect();
        System.setOut(out);
    }

    @Test
    public void shouldThrowExceptionWhenPortNumberNotInteger() throws Exception {
        System.getProperties().setProperty("http.proxyPort", "port");
        aspect.afterHttpClient(httpClient);
        verify(out, times(1)).println("Could not set credentials for proxy.");
    }

    @Test
    public void shouldSetCredentials() throws Exception {
        System.getProperties().setProperty("http.proxyHost", "localhost");
        System.getProperties().setProperty("http.proxyPort", "9000");
        System.getProperties().setProperty("http.proxyUser", "user");
        System.getProperties().setProperty("http.proxyPassword", "password");
        aspect.afterHttpClient(httpClient);
        verify(out, times(1)).println("Adding proxy user credentials");
        verify(out, never()).println("Could not set credentials for proxy");
    }

    @Test
    public void shouldNotSetCredentialsWhenUsernameIsNull() throws Exception {
        System.getProperties().setProperty("http.proxyHost", "localhost");
        System.getProperties().setProperty("http.proxyPort", "9000");
        System.getProperties().setProperty("http.proxyUser", "");
        System.getProperties().setProperty("http.proxyPassword", "password");
        aspect.afterHttpClient(httpClient);
        verify(out, never()).println("Adding proxy user credentials");
        verify(out, never()).println("Could not set credentials for proxy");
    }

    @Test
    public void shouldNotSetCredentialsWhenPasswordIsNull() throws Exception {
        System.getProperties().setProperty("http.proxyHost", "localhost");
        System.getProperties().setProperty("http.proxyPort", "9000");
        System.getProperties().setProperty("http.proxyUser", "username");
        System.getProperties().setProperty("http.proxyPassword", "");
        aspect.afterHttpClient(httpClient);
        verify(out, never()).println("Adding proxy user credentials");
        verify(out, never()).println("Could not set credentials for proxy");
    }


    @After
    public void tearDown() throws Exception {
        reset(out);
    }
}
