package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Appointment;
import auca.ac.clinicsystem.model.AppointmentStatus;
import auca.ac.clinicsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public Appointment getById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<String> create(@RequestBody Appointment appointment) {
        String result = appointmentService.create(appointment);
        if (result.equals("Appointment saved successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.update(id, appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String result = appointmentService.delete(id);
        if (result.equals("Appointment deleted successfully")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("That appointment has prescriptions, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @GetMapping("/by-status")
    public List<Appointment> byStatus(@RequestParam AppointmentStatus status) {
        return appointmentService.findByStatus(status);
    }

    @GetMapping("/between")
    public List<Appointment> between(@RequestParam String start, @RequestParam String end) {
        return appointmentService.findBetween(LocalDate.parse(start), LocalDate.parse(end));
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> statsByStatus() {
        return appointmentService.countByStatus();
    }

    @PatchMapping("/cancel-day")
    public String cancelDay(@RequestParam Long doctorId, @RequestParam String date) {
        return appointmentService.cancelDoctorDay(doctorId, LocalDate.parse(date));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id,
                                               @RequestParam AppointmentStatus status) {
        String result = appointmentService.updateStatus(id, status);
        if (result.equals("Appointment status updated successfully")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("The appointment with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<String> reschedule(@PathVariable Long id,
                                             @RequestParam String newDate) {
        String result = appointmentService.reschedule(id, LocalDate.parse(newDate));
        if (result.equals("Appointment rescheduled successfully")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("The appointment with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Doctor is already booked on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/page")
    public Page<Appointment> getPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size) {
        return appointmentService.getPage(PageRequest.of(page, size, Sort.by("appointmentDate")));
    }

    @DeleteMapping("/cancelled-before")
    public String deleteCancelledBefore(@RequestParam String date) {
        return appointmentService.deleteCancelledBefore(LocalDate.parse(date));
    }
}
