package com.practice.backend.api.employee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    @DateTimeFormat(pattern="yyyy-mm-gg")
    private LocalDate birthday;


    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    @JoinTable(
            name = "employee_hobby",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    //@JsonManagedReference
    @RestResource(exported = false)
    List<Hobby> hobbies = new ArrayList<>();


    public void addHobby(Hobby hobby){
        hobbies.add(hobby);
        hobby.getEmployees().add(this);
    }

    public void removeHobby(Hobby hobby){
        hobbies.remove(hobby);
        hobby.getEmployees().remove(this);
    }


}
