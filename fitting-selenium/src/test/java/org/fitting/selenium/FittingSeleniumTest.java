package org.fitting.selenium;

import org.fitting.FittingContainer;
import org.fitting.fixture.ElementFixture;

public class FittingSeleniumTest {

    /*
     * This is not a real unit test, but a try-out class to see if the code is working.
     */

    public static void main(final String[] args) throws Exception {
        BrowserConnector browserConnector = BrowserConnector.builder().withPlatform(System.getProperty("os.name"))
                .withBrowser("ff").onHost("localhost", 4444).withJavascriptEnabled(true).build();
//        FittingContainer.set(FittingConfiguration.getInstance().getSystemConnector().newInstance());
        FittingSeleniumConnector connector = new FittingSeleniumConnector(browserConnector);
        FittingContainer.set(connector);
        connector.getFittingAction().waitXSeconds(3);
        browserConnector.getWebDriver().navigate().to("www.google.com");
        boolean exists = new ElementFixture().elementWithExists("div", "lga");
        System.out.println("exists: " + exists);

    }

}
