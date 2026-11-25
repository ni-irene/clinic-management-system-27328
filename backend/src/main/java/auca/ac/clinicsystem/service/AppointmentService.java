package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Appointment;
import auca.ac.clinicsystem.model.AppointmentStatus;
import auca.ac.clinicsystem.repository.AppointmentRepository;
import auca.ac.clinicsystem.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public String create(Appointment appointment) {
        if (appointment.getAppointmentDate().toLocalDate().isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        LocalDateTime date = appointment.getAppointmentDate();
        LocalDateTime start = date.toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        boolean booked = appointmentRepository.existsByDoctorIdAndAppointmentDateBetweenAndStatusNot(
                appointment.getDoctor().getId(), start, end, AppointmentStatus.CANCELLED);
        if (booked) {
            return "Doctor is already booked on that date";
        }
        appointmentRepository.save(appointment);
        return "Appointment saved successfully";
    }

    public Appointment update(Long id, Appointment updated) {
        Appointment appointment = getById(id);
        appointment.setAppointmentDate(updated.getAppointmentDate());
        appointment.setReason(updated.getReason());
        return appointmentRepository.save(appointment);
    }

    public String delete(Long id) {
        boolean exists = appointmentRepository.existsById(id);
        if (!exists) {
            return "The appointment with that id does not exist";
        }
        if (prescriptionRepository.existsByAppointmentId(id)) {
            return "That appointment has prescriptions, delete them first";
        }
        appointmentRepository.deleteById(id);
        return "Appointment deleted successfully";
    }

    public List<Appointment> findByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
    }

    public List<Appointment> findBetween(LocalDate start, LocalDate end) {
        LocalDateTime from = start.atStartOfDay();
        LocalDateTime to = end.atTime(23, 59, 59);
        return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAsc(from, to);
    }

    public List<Object[]> countByStatus() {
        return appointmentRepository.countByStatus();
    }

    public Object[] findBusiestOffice() {
        List<Object[]> results = appointmentRepository.findBusiestOffice();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Transactional
    public String cancelDoctorDay(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        int count = appointmentRepository.cancelAppointmentsForDoctorOnDay(
                doctorId, start, end, AppointmentStatus.CANCELLED, AppointmentStatus.COMPLETED);
        return count + " appointments cancelled";
    }

    public String updateStatus(Long id, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        AppointmentStatus current = appointment.getStatus();
        boolean allowed = false;
        if (current == AppointmentStatus.SCHEDULED &&
                (newStatus == AppointmentStatus.CONFIRMED || newStatus == AppointmentStatus.CANCELLED)) {
            allowed = true;
        }
        if (current == AppointmentStatus.CONFIRMED &&
                (newStatus == AppointmentStatus.COMPLETED || newStatus == AppointmentStatus.CANCELLED)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);
        return "Appointment status updated successfully";
    }

    public String reschedule(Long id, LocalDate newDate) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            return "A COMPLETED appointment cannot be rescheduled";
        }
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            return "A CANCELLED appointment cannot be rescheduled";
        }
        if (newDate.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        LocalDateTime start = newDate.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        boolean booked = appointmentRepository.existsByDoctorIdAndAppointmentDateBetweenAndStatusNot(
                appointment.getDoctor().getId(), start, end, AppointmentStatus.CANCELLED);
        if (booked) {
            return "Doctor is already booked on that date";
        }
        appointment.setAppointmentDate(newDate.atStartOfDay());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointmentRepository.save(appointment);
        return "Appointment rescheduled successfully";
    }

    public List<Appointment> upcomingForPatient(String ssNumber) {
        List<AppointmentStatus> statuses = Arrays.asList(
                AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED);
        return appointmentRepository
                .findByPatientSsNumberAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
                        ssNumber, LocalDate.now().atStartOfDay(), statuses);
    }

    public Page<Appointment> getPage(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    @Transactional
    public String deleteCancelledBefore(LocalDate date) {
        int count = appointmentRepository.deleteCancelledBefore(date.atStartOfDay(), AppointmentStatus.CANCELLED);
        return count + " appointments deleted";
    }

    public long countScheduled() {
        return appointmentRepository.findByStatusOrderByAppointmentDateAsc(AppointmentStatus.SCHEDULED).size();
    }

    public long countToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end).size();
    }
}
