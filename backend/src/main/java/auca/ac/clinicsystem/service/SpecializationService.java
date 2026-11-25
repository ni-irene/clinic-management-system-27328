package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Doctor;
import auca.ac.clinicsystem.model.Specialization;
import auca.ac.clinicsystem.repository.DoctorRepository;
import auca.ac.clinicsystem.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Specialization> getAll() {
        return specializationRepository.findAll();
    }

    public Specialization getById(Long id) {
        return specializationRepository.findById(id).orElse(null);
    }

    public Specialization create(Specialization spec) {
        return specializationRepository.save(spec);
    }

    public Specialization update(Long id, Specialization updated) {
        Specialization spec = getById(id);
        spec.setName(updated.getName());
        return specializationRepository.save(spec);
    }

    public void delete(Long id) {
        specializationRepository.deleteById(id);
    }

    public Doctor assignToDoctor(Long specId, Long doctorId) {
        Specialization spec = getById(specId);
        Doctor doc = doctorRepository.findById(doctorId).orElse(null);
        doc.getSpecializations().add(spec);
        return doctorRepository.save(doc);
    }

    public Doctor removeFromDoctor(Long specId, Long doctorId) {
        Specialization spec = getById(specId);
        Doctor doc = doctorRepository.findById(doctorId).orElse(null);
        doc.getSpecializations().remove(spec);
        return doctorRepository.save(doc);
    }

    public List<Specialization> findUnused() {
        return specializationRepository.findUnusedSpecializations();
    }
}
