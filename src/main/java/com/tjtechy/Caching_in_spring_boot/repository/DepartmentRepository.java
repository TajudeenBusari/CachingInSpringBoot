package com.tjtechy.Caching_in_spring_boot.repository;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //this is an interface that extends the JpaRepository
    // has two params, the type of entity and the primary key
    //this interface is used to perform CRUD operations on the entity
    //the JpaRepository has all the methods to perform CRUD operations
    //we can also define our own methods here
    //the JpaRepository is a generic interface
}
