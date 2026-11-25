
package auca.ac.clinicsystem.controller;

import auca.ac.clinicsystem.model.Office;
import auca.ac.clinicsystem.service.AppointmentService;
import auca.ac.clinicsystem.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
@CrossOrigin(origins = "http://localhost:5173")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Office> getAll() {
        return officeService.getAll();
    }

    @GetMapping("/{id}")
    public Office getById(@PathVariable Long id) {
        return officeService.getById(id);
    }

    @PostMapping
    public Office create(@RequestBody Office office) {
        return officeService.create(office);
    }

    @PutMapping("/{id}")
    public Office update(@PathVariable Long id, @RequestBody Office office) {
        return officeService.update(id, office);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        officeService.delete(id);
    }

    @GetMapping("/free")
    public List<Office> freeOffices() {
        return officeService.findFreeOffices();
    }

    @GetMapping("/busiest")
    public Object busiest() {
        Object[] result = appointmentService.findBusiestOffice();

        if (result == null) {
            return "No appointments yet";
        }

        return result;
    }
}
