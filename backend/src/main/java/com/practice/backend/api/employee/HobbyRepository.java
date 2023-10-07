package com.practice.backend.api.employee;

import com.practice.backend.api.employee.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyRepository  extends JpaRepository<Hobby,Long> {

    Optional<Hobby> findByName(String name);

}
