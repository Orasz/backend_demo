package com.practice.backend.api.employee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="hobby")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hobby {

    public Hobby(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "hobbies", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Employee> employees = new ArrayList<>();

}
