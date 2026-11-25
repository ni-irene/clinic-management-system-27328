package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Doctor;
import auca.ac.clinicsystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getById(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @PostMapping
    public Doctor create(@RequestBody Doctor doctor) {
        return doctorService.create(doctor);
    }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.update(id, doctor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        doctorService.delete(id);
    }

    @GetMapping("/by-specialization")
    public List<Doctor> bySpecialization(@RequestParam String name) {
        return doctorService.findBySpecialization(name);
    }

    @GetMapping("/without-office")
    public List<Doctor> withoutOffice() {
        return doctorService.findWithoutOffice();
    }

    @PutMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<String> addSpecialization(@PathVariable Long doctorId,
                                                    @PathVariable Long specializationId) {
        String result = doctorService.addSpecialization(doctorId, specializationId);
        if (result.equals("Specialization added to the doctor")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("The doctor already has that specialization")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @DeleteMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<String> removeSpecialization(@PathVariable Long doctorId,
                                                       @PathVariable Long specializationId) {
        String result = doctorService.removeSpecialization(doctorId, specializationId);
        if (result.equals("Specialization removed from the doctor")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("The doctor does not have that specialization")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PatchMapping("/{doctorId}/office")
    public ResponseEntity<String> moveOffice(@PathVariable Long doctorId,
                                             @RequestParam String officeNumber) {
        String result = doctorService.moveOffice(doctorId, officeNumber);
        if (result.equals("Office changed successfully")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("That office is taken by other doctor")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @GetMapping("/{doctorId}/agenda")
    public ResponseEntity<Object> agenda(@PathVariable Long doctorId,
                                         @RequestParam String date) {
        Doctor doctor = doctorService.getById(doctorId);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The doctor with that id does not exist");
        }
        return ResponseEntity.ok(doctorService.agendaForDay(doctorId, date));
    }

    @GetMapping("/search")
    public List<Doctor> search(@RequestParam String name) {
        return doctorService.searchByName(name);
    }

    @GetMapping("/workload")
    public List<Object[]> workload(@RequestParam String start, @RequestParam String end) {
        return doctorService.workload(start, end);
    }
}
