package org.motechproject.sms.it;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Specify which integration test classes to run
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SmsAuditServiceIT.class, IncomingControllerIT.class, SendControllerIT.class})
public class IntegrationTests {
}
