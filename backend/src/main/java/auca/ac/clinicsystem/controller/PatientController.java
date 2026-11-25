package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Patient;
import auca.ac.clinicsystem.service.AppointmentService;
import auca.ac.clinicsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Patient> getAll() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable Long id) {
        return patientService.getById(id);
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.update(id, patient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }

    // A1 - search by last name
    @GetMapping("/by-last-name")
    public List<Patient> byLastName(@RequestParam String lastName) {
        return patientService.findByLastName(lastName);
    }

    // B4 - patients of a doctor, returns message if doctor not found
    @GetMapping("/of-doctor/{doctorId}")
    public Object ofDoctor(@PathVariable Long doctorId) {
        return patientService.findPatientsByDoctor(doctorId);
    }

    // C2 - frequent patients
    @GetMapping("/frequent")
    public List<Patient> frequent(@RequestParam int min) {
        return patientService.findFrequentPatients(min);
    }

    // D2 - upcoming appointments of a patient
    @GetMapping("/{ssNumber}/upcoming")
    public ResponseEntity<Object> upcoming(@PathVariable String ssNumber) {
        Patient patient = patientService.findBySsNumber(ssNumber);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That patient with that ssnumber does not exist");
        }
        Object result = appointmentService.upcomingForPatient(ssNumber);
        return ResponseEntity.ok(result);
    }
}
