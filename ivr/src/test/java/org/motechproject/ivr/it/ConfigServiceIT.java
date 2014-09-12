package org.motechproject.ivr.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.ivr.domain.Config;
import org.motechproject.ivr.domain.HttpMethod;
import org.motechproject.ivr.service.ConfigService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Verify ConfigService present & functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class ConfigServiceIT extends BasePaxIT {

    @Inject
    private ConfigService configService;

    @Test
    public void verifyServiceFunctional() {
        Config myConfig = new Config("MyConfig", null, null, HttpMethod.GET, "http://foo.com/bar");
        configService.updateConfigs(Arrays.asList(myConfig));

        Config config = configService.getConfig("MyConfig");
        assertEquals(config, myConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFindAbsentConfig() {
        configService.updateConfigs(Arrays.asList(new Config("foo", null, null, null, null)));
        configService.getConfig("bar");
    }
}
