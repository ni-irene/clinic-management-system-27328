
package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Appointment;
import auca.ac.clinicsystem.model.AppointmentStatus;
import auca.ac.clinicsystem.model.Doctor;
import auca.ac.clinicsystem.model.Office;
import auca.ac.clinicsystem.model.Specialization;
import auca.ac.clinicsystem.repository.AppointmentRepository;
import auca.ac.clinicsystem.repository.DoctorRepository;
import auca.ac.clinicsystem.repository.OfficeRepository;
import auca.ac.clinicsystem.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor update(Long id, Doctor updated) {
        Doctor doc = getById(id);
        doc.setFirstName(updated.getFirstName());
        doc.setLastName(updated.getLastName());
        doc.setOffice(updated.getOffice());
        return doctorRepository.save(doc);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> findBySpecialization(String name) {
        return doctorRepository.findBySpecializationName(name);
    }

    public List<Doctor> findWithoutOffice() {
        return doctorRepository.findDoctorsWithoutOffice();
    }

    public String addSpecialization(Long doctorId, Long specializationId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }

        Specialization spec = specializationRepository.findById(specializationId).orElse(null);
        if (spec == null) {
            return "That specialization does not exist";
        }

        if (doctor.getSpecializations().contains(spec)) {
            return "The doctor already has that specialization";
        }

        doctor.getSpecializations().add(spec);
        doctorRepository.save(doctor);
        return "Specialization added to the doctor";
    }

    public String removeSpecialization(Long doctorId, Long specializationId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }

        Specialization spec = specializationRepository.findById(specializationId).orElse(null);
        if (spec == null) {
            return "That specialization does not exist";
        }

        if (!doctor.getSpecializations().contains(spec)) {
            return "The doctor does not have that specialization";
        }

        doctor.getSpecializations().remove(spec);
        doctorRepository.save(doctor);
        return "Specialization removed from the doctor";
    }

    public String moveOffice(Long doctorId, String officeNumber) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }

        Office office = officeRepository.findByRoomNumber(officeNumber);
        if (office == null) {
            return "The office with that office number does not exist";
        }

        if (doctorRepository.existsByOfficeRoomNumber(officeNumber)) {
            return "That office is taken by other doctor";
        }

        doctor.setOffice(office);
        doctorRepository.save(doctor);
        return "Office changed successfully";
    }

    public List<Appointment> agendaForDay(Long doctorId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        return appointmentRepository.findByDoctorIdAndAppointmentDateBetweenAndStatusNot(
                doctorId, start, end, AppointmentStatus.CANCELLED);
    }

    public List<Doctor> searchByName(String name) {
        return doctorRepository.searchByName(name);
    }

    public List<Object[]> workload(String start, String end) {
        LocalDateTime from = LocalDate.parse(start).atStartOfDay();
        LocalDateTime to = LocalDate.parse(end).atTime(23, 59, 59);

        return doctorRepository.findDoctorWorkload(
                from, to, AppointmentStatus.COMPLETED);
    }
}
