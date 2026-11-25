package auca.ac.clinicsystem.repository;
import org.springframework.data.domain.Pageable;

import auca.ac.clinicsystem.model.Appointment;
import auca.ac.clinicsystem.model.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(
            LocalDateTime start, LocalDateTime end);

    boolean existsByDoctorIdAndAppointmentDateBetweenAndStatusNot(
            Long doctorId, LocalDateTime start, LocalDateTime end, AppointmentStatus status);

    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    List<Object[]> countByStatus();

    @Query("SELECT a.doctor.office.roomNumber, a.doctor.office.floor, COUNT(a) as total " +
           "FROM Appointment a WHERE a.doctor.office IS NOT NULL " +
           "GROUP BY a.doctor.office.id, a.doctor.office.roomNumber, a.doctor.office.floor " +
           "ORDER BY total DESC")
    List<Object[]> findBusiestOffice();

    @Modifying
    @Query("UPDATE Appointment a SET a.status = :cancelled " +
           "WHERE a.doctor.id = :doctorId " +
           "AND a.appointmentDate >= :start AND a.appointmentDate < :end " +
           "AND a.status <> :completed")
    int cancelAppointmentsForDoctorOnDay(@Param("doctorId") Long doctorId,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("cancelled") AppointmentStatus cancelled,
                                         @Param("completed") AppointmentStatus completed);

    Page<Appointment> findAll(Pageable pageable);

    @Modifying
    @Query("DELETE FROM Appointment a WHERE a.status = :cancelled " +
           "AND a.appointmentDate < :before")
    int deleteCancelledBefore(@Param("before") LocalDateTime before,
                              @Param("cancelled") AppointmentStatus cancelled);

    List<Appointment> findByDoctorIdAndAppointmentDateBetweenAndStatusNot(
            Long doctorId, LocalDateTime start, LocalDateTime end, AppointmentStatus status);

    List<Appointment> findByPatientSsNumberAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
            String ssNumber, LocalDateTime from, List<AppointmentStatus> statuses);
}
