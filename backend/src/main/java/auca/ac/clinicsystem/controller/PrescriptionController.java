package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Prescription;
import auca.ac.clinicsystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam Long appointmentId,
                                       @RequestBody Prescription prescription) {
        String result = prescriptionService.save(appointmentId, prescription);
        if (result.equals("Prescription saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else if (result.equals("The appointment with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Object> byAppointment(@PathVariable Long appointmentId) {
        Object result = prescriptionService.findByAppointment(appointmentId);
        if (result instanceof String) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/patient/{ssNumber}")
    public List<Prescription> byPatient(@PathVariable String ssNumber) {
        return prescriptionService.findByPatient(ssNumber);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id,
                                         @RequestBody Prescription prescription) {
        String result = prescriptionService.update(id, prescription);
        if (result.equals("The prescription with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = prescriptionService.delete(id);
        if (result.equals("The prescription with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats/top-medicines")
    public List<Object[]> topMedicines() {
        return prescriptionService.topMedicines();
    }
}
