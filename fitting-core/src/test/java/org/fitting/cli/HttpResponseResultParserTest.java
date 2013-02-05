package org.fitting.cli;

import java.io.ByteArrayInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.fitting.util.ReflectionUtility.extract;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Unit test for {@link HttpResponseResultParser}. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpResponse.class, HttpEntity.class })
public class HttpResponseResultParserTest {
    private static final String RESULT_BODY =
            "<result><rootPath>mySuite</rootPath><finalCounts><right>10</right><wrong>5</wrong><ignores>10</ignores" +
                    "><exceptions>5</exceptions></finalCounts></result>";
    private static final String SUITE_NAME = "mySuite";
    private static final int TESTS_PASSED = 10;
    private static final int TESTS_FAILED = 5;
    private static final int TESTS_IGNORED = 10;
    private static final int TESTS_EXCEPTIONS = 5;

    @Mock
    private HttpResponse response;
    @Mock
    private HttpEntity entity;

    @Before
    public void setUp() throws Exception {
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(RESULT_BODY.getBytes()));
    }

    @Test
    public void shouldReadBodyStreamOnCreation() throws Exception {
        final HttpResponseResultParser parser = new HttpResponseResultParser(response);

        verify(response, times(1)).getEntity();
        verify(entity, times(1)).getContent();

        final String response = (String) extract(parser, "response");
        assertEquals("Content body not fully read", RESULT_BODY, response);
    }

    @Test
    public void shouldParseBody() throws Exception {
        final HttpResponseResultParser parser = new HttpResponseResultParser(response);
        final SuiteResult result = parser.parse();

        assertEquals("Suite name not parsed", SUITE_NAME, result.getSuiteName());
        assertEquals("Passed tests not parsed", TESTS_PASSED, result.numberOfTestsPassed());
        assertEquals("Failed tests not parsed", TESTS_FAILED, result.numberOfTestsFailed());
        assertEquals("Ignored tests not parsed", TESTS_IGNORED, result.numberOfTestsSkipped());
        assertEquals("Exception tests not parsed", TESTS_EXCEPTIONS, result.numberOfTestsWithExceptions());
    }
}
