package auca.ac.clinicsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // nullable = true so a doctor can exist without an office (required by B2)
    @OneToOne
    @JoinColumn(name = "office_id", unique = true)
    private Office office;

    @ManyToMany
    @JoinTable(
            name = "doctor_specializations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }

    public Set<Specialization> getSpecializations() { return specializations; }
    public void setSpecializations(Set<Specialization> specializations) { this.specializations = specializations; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}
