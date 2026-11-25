package auca.ac.clinicsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    private String floor;

    // Inverse side of the one-to-one: the FK lives in doctors.office_id
    @JsonIgnore
    @OneToOne(mappedBy = "office")
    private Doctor doctor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}