package com.tjtechy.Caching_in_spring_boot.exception.modelNotFound;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with id: " + id);
    }
}
