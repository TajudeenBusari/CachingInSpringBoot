package com.tjtechy.Caching_in_spring_boot.service.impl;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import com.tjtechy.Caching_in_spring_boot.exception.modelNotFound.DepartmentNotFoundException;
import com.tjtechy.Caching_in_spring_boot.repository.DepartmentRepository;
import com.tjtechy.Caching_in_spring_boot.service.DepartmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

  @Mock
  private DepartmentRepository departmentRepository;

  @InjectMocks
  private DepartmentServiceImpl departmentService;

  List<Department> departmentList;

  @BeforeEach
  void setUp() {
    departmentList = new ArrayList<>();

    Department department1 = new Department();
    department1.setId(100L);
    department1.setName("IT");
    department1.setLocation("Lagos");
    department1.setCode("IT-001");
    departmentList.add(department1);

    Department department2 = new Department();
    department2.setId(101L);
    department2.setName("HR");
    department2.setLocation("Lagos");
    department2.setCode("HR-001");
    departmentList.add(department2);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void saveDepartmentSuccess() {
    //Given
    var department = new Department();
    department.setName("IT");
    department.setLocation("Lagos");
    department.setCode("IT-001");

    given(departmentRepository.save(department)).willReturn(department);
    //When
    var savedDepartment = departmentService.saveDepartment(department);

    //Then
    assertThat(savedDepartment).isNotNull();
    assertThat(savedDepartment.getName()).isEqualTo(savedDepartment.getName());
    assertThat(savedDepartment.getLocation()).isEqualTo(savedDepartment.getLocation());
    assertThat(savedDepartment.getCode()).isEqualTo(savedDepartment.getCode());
    verify(departmentRepository, times(1)).save(department);


  }

  @Test
  void fetchDepartmentByIdSuccess() {
    //Given
    var department = new Department();
    department.setId(100L);
    department.setName("IT");
    department.setLocation("Lagos");
    department.setCode("IT-001");

    given(departmentRepository.findById(100L)).willReturn(Optional.of(department));

    //When
    var fetchedDepartment = departmentService.fetchDepartmentById(100L);

    //Then
    assertThat(fetchedDepartment).isNotNull();
  }

  @Test
  void fetchDepartmentByIdFailure() {
    //Given
    var nonExistingDepartment = new Department();
    nonExistingDepartment.setId(500L);
    nonExistingDepartment.setName("IT");
    nonExistingDepartment.setLocation("Lagos");
    nonExistingDepartment.setCode("IT-001");

    given(departmentRepository.findById(500L)).willReturn(Optional.empty());

    //When
   Throwable exception = catchThrowable(() -> departmentService.fetchDepartmentById(500L));

    //Then
    assertThat(exception).isInstanceOf(DepartmentNotFoundException.class);
    assertThat(exception).hasMessage("Department not found with id: 500");
  }

  @Test
  void fetchAllDepartmentsSuccess() {
    //Given
    given(departmentRepository.findAll()).willReturn(departmentList);

    //When
    var fetchedDepartments = departmentService.fetchAllDepartments();

    //Then
    assertThat(fetchedDepartments).isNotNull();
    assertThat(fetchedDepartments.size()).isEqualTo(2);
  }

  @Test
  void updateDepartmentSuccess() {
    //Given
    var oldDepartment = new Department();
    oldDepartment.setId(100L);
    oldDepartment.setName("IT");
    oldDepartment.setLocation("Lagos");
    oldDepartment.setCode("IT-001");

    var updatedDepartment = new Department();
    updatedDepartment.setId(100L);
    updatedDepartment.setName("IT");
    updatedDepartment.setLocation("Lagos updated");
    updatedDepartment.setCode("IT-001");

    //find the department and save it
    given(departmentRepository.findById(oldDepartment.getId())).willReturn(Optional.of(oldDepartment));
    given(departmentRepository.save(oldDepartment)).willReturn(oldDepartment);

    //When
    var updated = departmentService.updateDepartment(100L, updatedDepartment);

    //Then
    assertThat(updated).isNotNull();
    assertThat(updated.getId()).isEqualTo(100L);
    assertThat(updated.getName()).isEqualTo("IT");
    assertThat(updated.getLocation()).isEqualTo("Lagos updated");
    assertThat(updated.getCode()).isEqualTo("IT-001");
  }

  @Test
  void updateDepartmentFailure() {
    //Given
    var oldDepartment = new Department();
    oldDepartment.setId(100L);
    oldDepartment.setName("IT");
    oldDepartment.setLocation("Lagos");
    oldDepartment.setCode("IT-001");

    var updatedDepartment = new Department();
    updatedDepartment.setId(100L);
    updatedDepartment.setName("IT");
    updatedDepartment.setLocation("Lagos updated");
    updatedDepartment.setCode("IT-001");

    //find the department, you cant save it because it does not exist
    given(departmentRepository.findById(oldDepartment.getId())).willReturn(Optional.empty());

    //When
    Throwable exception = assertThrows(DepartmentNotFoundException.class, () ->
            departmentService.updateDepartment(100L, updatedDepartment));

    //Then
    assertThat(exception).isInstanceOf(RuntimeException.class);
    assertThat(exception).hasMessage("Department not found with id: 100");
  }

  @Test
  void deleteDepartmentSuccess() {
    //Given
    var department = new Department();
    department.setId(100L);
    department.setName("IT");
    department.setLocation("Lagos");
    department.setCode("IT-001");

    given(departmentRepository.findById(department.getId())).willReturn(Optional.of(department));

    //When
    departmentService.deleteDepartment(department.getId());

    //Then
    verify(departmentRepository, times(1)).deleteById(100L);
  }
}