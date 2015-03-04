package org.motechproject.csd.it;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.csd.domain.Facility;
import org.motechproject.csd.service.FacilityService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.motechproject.testing.utils.TestContext;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Verify ConfigService present & functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class FacilityServiceBundleIT extends BasePaxIT {

    @Inject
    private FacilityService service;

    @Test
    public void verifyServiceFunctional() {

        Facility facility = service.getFacility("123");
        assertNull(facility);
    }

    @Test
    public void verifySoapEndpoint() throws IOException, InterruptedException {
        login();

        // TODO: Use a real SOAP client, not HTTP client
        // TODO: Some headers are not understood by the server
        HttpPost post;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csdreq.xml")) {
            String xml = IOUtils.toString(in);
            post = new HttpPost(String.format("http://localhost:%d/csd/foobar",
                    TestContext.getJettyPort()));
            post.setEntity(new StringEntity(xml));
        }

        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/soap+xml");

        // TODO: Do something more with the response
        String response = getHttpClient().execute(post, new BasicResponseHandler());
        assertNotNull(response);
        assertTrue(response.contains("env:Envelope"));
    }
}
