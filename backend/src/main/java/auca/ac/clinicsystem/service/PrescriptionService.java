package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Appointment;
import auca.ac.clinicsystem.model.AppointmentStatus;
import auca.ac.clinicsystem.model.Prescription;
import auca.ac.clinicsystem.repository.AppointmentRepository;
import auca.ac.clinicsystem.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public String save(Long appointmentId, Prescription prescription) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            return "Prescriptions are only for COMPLETED appointments";
        }
        prescription.setIssuedDate(LocalDate.now());
        prescription.setAppointment(appointment);
        prescriptionRepository.save(prescription);
        return "Prescription saved successfully";
    }

    public Object findByAppointment(Long appointmentId) {
        boolean exists = appointmentRepository.existsById(appointmentId);
        if (!exists) {
            return "The appointment with that id does not exist";
        }
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }

    public List<Prescription> findByPatient(String ssNumber) {
        return prescriptionRepository.findByPatientSsNumber(ssNumber);
    }

    public String update(UUID id, Prescription updated) {
        Prescription p = prescriptionRepository.findById(id).orElse(null);
        if (p == null) {
            return "The prescription with that id does not exist";
        }
        p.setMedicineName(updated.getMedicineName());
        p.setDosage(updated.getDosage());
        p.setDurationDays(updated.getDurationDays());
        prescriptionRepository.save(p);
        return "Prescription updated successfully";
    }

    public String delete(UUID id) {
        boolean exists = prescriptionRepository.existsById(id);
        if (!exists) {
            return "The prescription with that id does not exist";
        }
        prescriptionRepository.deleteById(id);
        return "Prescription deleted successfully";
    }

    public List<Object[]> topMedicines() {
        return prescriptionRepository.findTopMedicines();
    }

    public long countAll() {
        return prescriptionRepository.count();
    }
}
