package org.motechproject.appointments.service.it;

import org.joda.time.DateTime;
import org.motechproject.appointments.domain.Appointment;
import org.motechproject.appointments.domain.AppointmentException;
import org.motechproject.appointments.domain.AppointmentStatus;
import org.motechproject.appointments.repository.AppointmentChangeRecordDataService;
import org.motechproject.appointments.service.AppointmentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.motechproject.testing.osgi.BasePaxIT;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Verify that HelloWorldAuthorService present, functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class AppointmentServiceIT extends BasePaxIT {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AppointmentService appointmentService;

    @Inject
    private AppointmentChangeRecordDataService appointmentChangeRecordDataService;

    @Test
    public void testAppointmentService() throws Exception {
        logger.info("testAppointmentService");
        assertNotNull(appointmentService);
    }

    @Test
    public void addAppointment() throws Exception {
        logger.info("addAppointment");
        Appointment current = new Appointment();
        current.setExternalId("1234");

        Appointment result = appointmentService.addAppointment(current);
        assertEquals(result.getExternalId(), current.getExternalId());
    }

    @Test
    public void addAppointmentDuplicate() throws Exception {
        logger.info("addAppointmentDuplicate");
        Appointment current = new Appointment();
        current.setExternalId("1234");
        Appointment result = appointmentService.addAppointment(current);
        assertEquals(result.getExternalId(), current.getExternalId());

        appointmentService.addAppointment(current);
    }

    @Test
    public void getAppointment() throws Exception {
        logger.info("getAppointment");
        Appointment current = new Appointment();
        current.setExternalId("1234");

        Appointment result = appointmentService.addAppointment(current);
        assertEquals(result.getExternalId(), current.getExternalId());

        Appointment lookup = appointmentService.getAppointment(result.getApptId());
        assertEquals(result.getApptId(), lookup.getApptId());
        assertEquals(result.getExternalId(), lookup.getExternalId());
    }

    @Test
    public void deleteAppointment() throws Exception {
        logger.info("deleteAppointment");
        Appointment current = new Appointment();
        current.setExternalId("1234");

        assertNotNull(appointmentService.addAppointment(current));
        appointmentService.removeAppointment(current.getApptId());
        List<Appointment> results = appointmentService.findAppointmentsByExternalId(current.getExternalId());

        // checking for null here in case other tests run first and leave appointments behind
        if (results != null) {
            for (Appointment appt : results) {
                assertNotEquals(appt.getApptId(), current.getApptId());
            }
        }
    }

    @Test
    public void deleteAppointmentException() throws Exception {
        logger.info("deleteAppointmentException");

        try {
            appointmentService.removeAppointment("CantTouchThis");
        } catch (Exception ae) {
            assertEquals(AppointmentException.class, ae.getClass());
        }
    }

    @Test
    public void updateAppointment() throws Exception {
        logger.info("updateAppointment");

        Appointment current = new Appointment();
        current.setExternalId("2345");
        appointmentService.addAppointment(current);
        assertEquals(AppointmentStatus.NONE, current.getStatus());

        current.setStatus(AppointmentStatus.CONFIRMED);
        appointmentService.updateAppointment(current);
        Appointment result = appointmentService.getAppointment(current.getApptId());
        assertNotNull(result);
        assertEquals(AppointmentStatus.CONFIRMED, result.getStatus());
    }

    @Test
    public void updateAppointmentException() throws Exception {
        logger.info("updateAppointmentException");

        // add
        Appointment current = new Appointment();
        current.setExternalId("2345");
        appointmentService.addAppointment(current);
        assertEquals(AppointmentStatus.NONE, current.getStatus());

        // remove
        appointmentService.removeAppointment(current.getApptId());

        // update, should throw exception
        try {
            current.setStatus(AppointmentStatus.CONFIRMED);
            appointmentService.updateAppointment(current);
        } catch (Exception ae) {
            assertEquals(AppointmentException.class, ae.getClass());
        }
    }

    @Test
    public void toggleReminders() throws Exception {
        logger.info("toggleReminders");
        Appointment current = new Appointment();
        current.setExternalId("123");
        current.setAppointmentDate(DateTime.now().plusDays(1));
        appointmentService.addAppointment(current);

        Appointment result = appointmentService.getAppointment(current.getApptId());
        assertNotNull(result);

        appointmentService.toggleReminders(result.getExternalId(), true);
        result = appointmentService.getAppointment(current.getApptId());
        assertEquals(true, result.getSendReminders());

    }

    @Test
    public void addMultiple() throws Exception {
        logger.info("addMultiple");
        final Appointment current1 = new Appointment();
        final Appointment current2 = new Appointment();
        current1.setExternalId("555");
        current2.setExternalId("555");
        assertNotNull(
                appointmentService.addAppointments(
                        new ArrayList<Appointment>() {{
                            add(current1);
                            add(current2);
                        }}
                )
        );

        List<Appointment> results = appointmentService.findAppointmentsByExternalId("555");
        assertTrue(results.size() >= 2);
    }

    @Test
    public void findAppointmentsByExternalId() throws Exception {
        logger.info("findAppointmentsByExternalId");
        final Appointment current1 = new Appointment();
        final Appointment current2 = new Appointment();
        final Appointment current3 = new Appointment();


        current1.setExternalId("666");
        current2.setExternalId("666");
        current3.setExternalId("777");
        appointmentService.addAppointments(
                new ArrayList<Appointment>() {{
                    add(current1);
                    add(current2);
                    add(current3);
                }}
        );

        List<Appointment> results = appointmentService.findAppointmentsByExternalId("666");
        for (Appointment temp : results) {
            assertEquals("666", temp.getExternalId());
        }
    }

    @Test
    public void findAppointmentsByExternalIdAndStatus() throws Exception {
        logger.info("findAppointmentsByExternalIdAndStatus");
        final Appointment current1 = new Appointment();
        final Appointment current2 = new Appointment();
        final Appointment current3 = new Appointment();

        current1.setExternalId("666");
        current2.setExternalId("666");
        current3.setExternalId("777");
        current1.setStatus(AppointmentStatus.CONFIRMED);
        current2.setStatus(AppointmentStatus.VISITED);
        appointmentService.addAppointments(
                new ArrayList<Appointment>() {{
                    add(current1);
                    add(current2);
                    add(current3);
                }}
        );

        List<Appointment> results = appointmentService.findAppointmentsByExternalId("666", AppointmentStatus.VISITED);
        assertTrue(results.size() > 0);

        for (Appointment temp : results) {
            assertEquals("666", temp.getExternalId());
            assertEquals(AppointmentStatus.VISITED, temp.getStatus());
        }
    }
}

