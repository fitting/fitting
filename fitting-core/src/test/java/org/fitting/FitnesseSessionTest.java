package org.fitting;

/** Test class for FitnesseSession. */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({FitnesseSession.class, DesiredCapabilities.class})
public class FitnesseSessionTest {
//    @Mock
//    private PropertiesConfiguration configuration;
//    @Mock
//    private WebDriver driver;
//
//    @Before
//    public void setUp() throws Exception {
//        setFitnesseSessionInstance(null); // Reset instance
//        suppress(RemoteWebDriver.class.getDeclaredConstructor(URL.class, Capabilities.class)); // don't start browser
//        when(configuration.getString("selenium.server.ip")).thenReturn("127.0.0.1");
//        when(configuration.getString("selenium.server.port")).thenReturn("4444");
//        when(configuration.getString("selenium.platform")).thenReturn("xp");
//        when(configuration.getString("http.proxyHost")).thenReturn("127.0.0.1");
//        when(configuration.getString("http.proxyPort")).thenReturn("8080");
//        when(configuration.getString("http.proxyUser")).thenReturn("user");
//        when(configuration.getString("http.proxyPassword")).thenReturn("password");
//    }
//
//    @SuppressWarnings("unchecked")
//	@Test(expected = IllegalArgumentException.class)
//    public void shouldThrowIllegalArgumentExceptionWhenPropertyFileIsNotFound() throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenThrow(ConfigurationException.class);
//        setFitnesseSessionInstance(null); // Reset instance
//        FitnesseSession.instance();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowIllegalArgumentExceptionWhenPropertyIsNotFound() throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//        when(configuration.getString("selenium.server.ip")).thenReturn(":");
//        setFitnesseSessionInstance(null); // Reset instance
//        FitnesseSession.instance().openBrowser("firefox");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowIllegalArgumentExceptionWhenPlatformIsNull() throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//        when(configuration.getString("selenium.platform")).thenReturn(null);
//        setFitnesseSessionInstance(null); // Reset instance
//        FitnesseSession.instance().openBrowser("firefox");
//    }
//
//    @Test
//    public void shouldSetVersionWhenVersionNotNull() throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//        FitnesseSession.instance().openBrowser("firefox", "11", "xp", "127.0.0.1", "4444");
//    }
//
//    @Test
//    public void shouldNotCreateNewInstanceIfInstanceIsNotNull() throws Exception {
//        assertEquals(FitnesseSession.instance(), FitnesseSession.instance());
//    }
//
//    @Test(expected = WebDriverException.class)
//    public void shouldGetNewWebDriver() throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//
//        FitnesseSession.instance().getDriver();
//        assertNotNull(getWebDriverInstance());
//        verify(configuration, times(1)).getBoolean("mode.js");
//    }
//
//    @Test
//    public void shouldNotGetNewWebDriver() throws Exception {
//        setWebDriverInstance(driver);
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//
//        FitnesseSession.instance().getDriver();
//        verify(configuration, never()).getBoolean("mode.js");
//    }
//
//    @Test
//    public void shouldCreateRemoteWebDriverWithInternetExplorerCapabilities() throws Exception {
//        mockStatic(DesiredCapabilities.class);
//        when(DesiredCapabilities.internetExplorer()).thenReturn(new DesiredCapabilities());
//        FitnesseSession.instance().openBrowser("ie");
//        verifyStatic(times(1));
//    }
//
//    @Test
//    public void shouldCreateRemoteWebDriverWithChromeCapabilities() throws Exception {
//        mockStatic(DesiredCapabilities.class);
//        when(DesiredCapabilities.chrome()).thenReturn(new DesiredCapabilities());
//        FitnesseSession.instance().openBrowser("chrome");
//        verifyStatic(times(1));
//    }
//
//    @Test
//    public void shouldCreateRemoteWebDriverWithOperaCapabilities() throws Exception {
//        mockStatic(DesiredCapabilities.class);
//        when(DesiredCapabilities.opera()).thenReturn(new DesiredCapabilities());
//        FitnesseSession.instance().openBrowser("opera");
//        verifyStatic(times(1));
//    }
//
//    @Test
//    public void shouldCreateRemoteWebDriverWithFirefoxCapabilities() throws Exception {
//        mockStatic(DesiredCapabilities.class);
//        when(DesiredCapabilities.firefox()).thenReturn(new DesiredCapabilities());
//        FitnesseSession.instance().openBrowser("firefox");
//        verifyStatic(times(1));
//    }
//
//    @Test
//    public void shouldCreateRemoteWebDriverWithFirefoxCapabilitiesForAnyOtherBrowser() throws Exception {
//        mockStatic(DesiredCapabilities.class);
//        when(DesiredCapabilities.firefox()).thenReturn(new DesiredCapabilities());
//        FitnesseSession.instance().openBrowser("safari");
//        verifyStatic(times(1));
//    }
//
//    @Test
//    public void shouldNotCloseBrowserWhenDriverIsNull() throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().closeBrowser();
//        assertNull(getWebDriverInstance());
//    }
//
//    @Test
//    public void shouldOpenBrowserWithIpAddressAndPortNumber() throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().openBrowser("firefox", "127.0.0.1", "4444");
//        assertNotNull(getWebDriverInstance());
//    }
//
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenIpAddressIsInvalidWhileOpenBrowserWithIpAddressAndPortNumber()
//            throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().openBrowser("firefox", ":", "4444");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenIpAddressIsNullWhileOpenBrowserWithIpAddressAndPortNumber()
//            throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().openBrowser("firefox", null, "4444");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenPortNumberIsInvalidWhileOpenBrowserWithIpAddressAndPortNumber()
//            throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().openBrowser("firefox", "127.0.0.1", "port");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenPortNumberNullWhileOpenBrowserWithIpAddressAndPortNumber()
//            throws Exception {
//        assertNull(getWebDriverInstance());
//        FitnesseSession.instance().openBrowser("firefox", "127.0.0.1", null);
//    }
//
//    @Test
//    public void shouldGetIpAddressAndPortNumberFromPropertyFileWhileOpenBrowser()
//            throws Exception {
//        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("fitnesse.properties")
//                .thenReturn(configuration);
//        when(configuration.getString("selenium.server.ip")).thenReturn("127.0.0.1");
//        when(configuration.getString("selenium.server.port")).thenReturn("4444");
//        setFitnesseSessionInstance(null); // Reset instance
//        FitnesseSession.instance().openBrowser("firefox");
//        assertNotNull(getWebDriverInstance());
//        verify(configuration, times(1)).getString("selenium.server.ip");
//        verify(configuration, times(1)).getString("selenium.server.port");
//    }
//
//    @Test
//    public void shouldCloseBrowser() throws Exception {
//        setWebDriverInstance(driver);
//        FitnesseSession.instance().closeBrowser();
//        assertNull(getWebDriverInstance());
//        verify(driver).close();
//    }
}
