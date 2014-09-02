package org.motechproject.sms.it;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.sms.audit.*;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Verify SmsAuditService present & functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class SmsAuditServiceIT extends BasePaxIT{
    @Inject
    private SmsRecordsDataService smsRecordsDataService;

    @Inject
    private SmsAuditService smsAuditService;

    @Before
    public void cleanupDatabase() {
        smsRecordsDataService.deleteAll();
    }

    @Test
    public void verifyServiceFunctional() {
        getLogger().info("verifyServiceFunctional");

        SmsRecord smsRecord = new SmsRecord("config", SmsDirection.INBOUND, "from", "message", DateTime.now(),
                DeliveryStatus.PENDING, "status", "mid", "pid", null);
        smsAuditService.log(smsRecord);

        List<SmsRecord> smsRecords = smsRecordsDataService.retrieveAll();
        assertEquals(1, smsRecords.size());
        assertEquals(smsRecords.get(0), smsRecord);
    }
}
