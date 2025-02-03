package com.tjtechy.Caching_in_spring_boot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    private String name;
    private String location;
    private String code;


    public Department() {
    }

    public Department(Long id, String name, String location, String code) {
        this.departmentId = id;
        this.name = name;
        this.location = location;
        this.code = code;
    }

    public Long getId() {
        return departmentId;
    }

    public void setId(Long id) {
        this.departmentId = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
