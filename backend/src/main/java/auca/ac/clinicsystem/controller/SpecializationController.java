package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Doctor;
import auca.ac.clinicsystem.model.Specialization;
import auca.ac.clinicsystem.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public List<Specialization> getAll() {
        return specializationService.getAll();
    }

    @GetMapping("/{id}")
    public Specialization getById(@PathVariable Long id) {
        return specializationService.getById(id);
    }

    @PostMapping
    public Specialization create(@RequestBody Specialization specialization) {
        return specializationService.create(specialization);
    }

    @PutMapping("/{id}")
    public Specialization update(@PathVariable Long id, @RequestBody Specialization specialization) {
        return specializationService.update(id, specialization);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        specializationService.delete(id);
    }

    @PostMapping("/{specId}/doctors/{doctorId}")
    public Doctor assignToDoctor(@PathVariable Long specId, @PathVariable Long doctorId) {
        return specializationService.assignToDoctor(specId, doctorId);
    }

    @DeleteMapping("/{specId}/doctors/{doctorId}")
    public Doctor removeFromDoctor(@PathVariable Long specId, @PathVariable Long doctorId) {
        return specializationService.removeFromDoctor(specId, doctorId);
    }

    @GetMapping("/unused")
    public List<Specialization> unused() {
        return specializationService.findUnused();
    }
}
