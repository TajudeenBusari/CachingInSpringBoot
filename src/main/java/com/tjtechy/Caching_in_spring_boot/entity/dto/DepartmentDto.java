package com.tjtechy.Caching_in_spring_boot.entity.dto;


import jakarta.validation.constraints.NotEmpty;

public record DepartmentDto(
        Long departmentId,
        @NotEmpty(message = "Name cannot be empty")
        String name,
        @NotEmpty(message = "Location cannot be empty")
        String location,
        @NotEmpty(message = "Code cannot be empty")
        String code) {

}
