package auca.ac.clinicsystem.service;

import auca.ac.clinicsystem.model.Office;
import auca.ac.clinicsystem.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public List<Office> getAll() {
        return officeRepository.findAll();
    }

    public Office getById(Long id) {
        return officeRepository.findById(id).orElse(null);
    }

    public Office create(Office office) {
        return officeRepository.save(office);
    }

    public Office update(Long id, Office updated) {
        Office office = getById(id);
        office.setRoomNumber(updated.getRoomNumber());
        office.setFloor(updated.getFloor());
        return officeRepository.save(office);
    }

    public void delete(Long id) {
        officeRepository.deleteById(id);
    }

    public List<Office> findFreeOffices() {
        return officeRepository.findFreeOffices();
    }
}
