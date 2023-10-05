package com.practice.backend.api.employee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_hobby",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    Set<Hobby> hobbies;


}
