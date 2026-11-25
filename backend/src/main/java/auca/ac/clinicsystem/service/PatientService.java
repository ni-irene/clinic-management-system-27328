package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Patient;
import auca.ac.clinicsystem.repository.DoctorRepository;
import auca.ac.clinicsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient updated) {
        Patient patient = getById(id);
        patient.setFirstName(updated.getFirstName());
        patient.setLastName(updated.getLastName());
        patient.setSsNumber(updated.getSsNumber());
        patient.setPhone(updated.getPhone());
        return patientRepository.save(patient);
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findByLastName(String lastName) {
        return patientRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public Object findPatientsByDoctor(Long doctorId) {
        boolean exists = doctorRepository.existsById(doctorId);
        if (!exists) {
            return "The doctor with that id does not exist";
        }
        return patientRepository.findPatientsByDoctorId(doctorId);
    }

    public List<Patient> findFrequentPatients(int min) {
        return patientRepository.findFrequentPatients(min);
    }

    public Patient findBySsNumber(String ssNumber) {
        return patientRepository.findBySsNumber(ssNumber);
    }
}
