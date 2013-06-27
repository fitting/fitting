package org.fitting.aop;

import java.util.Properties;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Aspect around the get HttpClientFactory.getHttpClient(...) method and if required, it will add the proxy credentials to the HttpClient.
 */
@Aspect
public class HttpClientFactoryAspect {

    /**
     * Set the proxy credentials if possible. If we want to go outside of the network we need to set the proxy credentials to do dns lookups.
     *
     * @param httpClient The HttpClient.
     */
    @AfterReturning(pointcut = "execution(* org.openqa.selenium.remote.internal.HttpClientFactory.getHttpClient(..))", returning = "httpClient")
    public void afterHttpClient(final Object httpClient) {
        try {
            final DefaultHttpClient client = (DefaultHttpClient) httpClient;
            final Properties properties = System.getProperties();
            final String proxyHost = (String) properties.get("http.proxyHost");
            final Integer proxyPort = Integer.parseInt((String) properties.get("http.proxyPort"));
            final String proxyUser = (String) properties.get("http.proxyUser");
            final String proxyPassword = (String) properties.get("http.proxyPassword");

            if (isNotEmpty(proxyUser) && isNotEmpty(proxyPassword)) { // only set credentials if the sys prop is set
                System.out.println("Adding proxy user credentials");
                final CredentialsProvider credentialsProvider = client.getCredentialsProvider();
                credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(proxyUser, proxyPassword));
            }
        } catch (Exception e) {
            System.out.println("Could not set credentials for proxy.");
        }
    }
}