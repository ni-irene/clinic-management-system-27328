package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.repository.AppointmentRepository;
import auca.ac.clinicsystem.repository.DoctorRepository;
import auca.ac.clinicsystem.repository.OfficeRepository;
import auca.ac.clinicsystem.repository.PatientRepository;
import auca.ac.clinicsystem.repository.PrescriptionRepository;
import auca.ac.clinicsystem.model.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Map<String, Long> getSummary() {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        long freeOffices = officeRepository.findFreeOffices().size();
        long appointmentsToday = appointmentRepository
                .findByAppointmentDateBetweenOrderByAppointmentDateAsc(todayStart, todayEnd).size();
        long scheduled = appointmentRepository
                .findByStatusOrderByAppointmentDateAsc(AppointmentStatus.SCHEDULED).size();

        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("patients", patientRepository.count());
        summary.put("doctors", doctorRepository.count());
        summary.put("freeOffices", freeOffices);
        summary.put("appointmentsToday", appointmentsToday);
        summary.put("scheduled", scheduled);
        summary.put("prescriptions", prescriptionRepository.count());
        return summary;
    }
}
