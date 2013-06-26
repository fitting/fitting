package org.fitting.selenium;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {
    private WebDriver webDriver;
    private boolean javascriptEnabled;

    public Browser(DesiredCapabilities capabilities, URL url) {
        webDriver = new RemoteWebDriver(url, capabilities);
        javascriptEnabled = capabilities.isJavascriptEnabled();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public boolean isJavascriptEnabled() {
        return javascriptEnabled;
    }

    public void destroy() {
        this.webDriver.close();
        this.webDriver = null;
        this.javascriptEnabled = false;
    }
}
