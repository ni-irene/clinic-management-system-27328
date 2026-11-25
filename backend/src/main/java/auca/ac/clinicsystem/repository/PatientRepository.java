package auca.ac.clinicsystem.repository;

import auca.ac.clinicsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT p FROM Patient p JOIN p.appointments a GROUP BY p HAVING COUNT(a) >= :min ORDER BY COUNT(a) DESC")
    List<Patient> findFrequentPatients(@Param("min") int min);

    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") Long doctorId);

    Patient findBySsNumber(String ssNumber);
}
