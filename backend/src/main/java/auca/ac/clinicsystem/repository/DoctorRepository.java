package auca.ac.clinicsystem.repository;
import auca.ac.clinicsystem.model.AppointmentStatus;

import auca.ac.clinicsystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(:name)")
    List<Doctor> findBySpecializationName(@Param("name") String name);

    @Query("SELECT d FROM Doctor d WHERE d.office IS NULL ORDER BY d.lastName ASC")
    List<Doctor> findDoctorsWithoutOffice();

    boolean existsByOfficeRoomNumber(String roomNumber);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.lastName ASC")
    List<Doctor> searchByName(@Param("name") String name);

    @Query("SELECT d.firstName, d.lastName, COUNT(a) FROM Appointment a JOIN a.doctor d WHERE a.status = :completed AND a.appointmentDate BETWEEN :start AND :end GROUP BY d.id, d.firstName, d.lastName ORDER BY COUNT(a) DESC")
    List<Object[]> findDoctorWorkload(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("completed") AppointmentStatus completed);
}
