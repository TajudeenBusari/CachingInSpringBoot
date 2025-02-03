package com.tjtechy.Caching_in_spring_boot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjtechy.Caching_in_spring_boot.entity.Department;
import com.tjtechy.Caching_in_spring_boot.entity.dto.DepartmentDto;
import com.tjtechy.Caching_in_spring_boot.exception.modelNotFound.DepartmentNotFoundException;
import com.tjtechy.Caching_in_spring_boot.service.DepartmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

  //use MockitoBean instead of mock so that test results will not be stored in the database
  @MockitoBean
  private DepartmentService departmentService;

  //This is not needed because we are using mockMvc
//  @InjectMocks
//  private DepartmentController departmentController;

  private DepartmentDto departmentDto;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Value("${api.endpoint.base-url}")
  String baseUrl;

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
  void createDepartmentSuccess() throws Exception {
    //given
    DepartmentDto departmentDto = new DepartmentDto(null,"IT", "Lagos", "IT-001");

    var json = objectMapper.writeValueAsString(departmentDto);

    //data the service layer will return
    var savedDepartment = new Department();
    savedDepartment.setId(100L);
    savedDepartment.setName("IT");
    savedDepartment.setLocation("Lagos");
    savedDepartment.setCode("IT-001");

    given(departmentService.saveDepartment(Mockito.any(Department.class))).willReturn(savedDepartment);

    //when and then
    this.mockMvc.perform(post(baseUrl + "/department")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Add Success"))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.data.departmentId").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("IT"))
            .andExpect(jsonPath("$.data.location").value("Lagos"))
            .andExpect(jsonPath("$.data.code").value("IT-001"));
  }

  @Test
  void getAllDepartmentsSuccess() throws Exception {
    //given
    given(departmentService.fetchAllDepartments()).willReturn(departmentList);

    //when and //then
    this.mockMvc.perform(get(baseUrl + "/department").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Get All Success"))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].departmentId").isNotEmpty())
            .andExpect(jsonPath("$.data[0].name").value("IT"))
            .andExpect(jsonPath("$.data[0].location").value("Lagos"))
            .andExpect(jsonPath("$.data[0].code").value("IT-001"))
            .andExpect(jsonPath("$.data[1].departmentId").isNotEmpty())
            .andExpect(jsonPath("$.data[1].name").value("HR"))
            .andExpect(jsonPath("$.data[1].location").value("Lagos"))
            .andExpect(jsonPath("$.data[1].code").value("HR-001"));
  }

  @Test
  void getDepartmentByIdSuccess() throws Exception {
    //given
    given(departmentService.fetchDepartmentById(100L)).willReturn(departmentList.get(0));

    //when and //then
    this.mockMvc.perform(get(baseUrl + "/department/100").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Get One Success"))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.data.departmentId").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("IT"))
            .andExpect(jsonPath("$.data.location").value("Lagos"))
            .andExpect(jsonPath("$.data.code").value("IT-001"));
  }

  @Test
  void getDepartmentByIdFailure() throws Exception {
    //given
    given(departmentService.fetchDepartmentById(100L)).willThrow(new DepartmentNotFoundException(100L));

    //when and //then
    this.mockMvc.perform(get(baseUrl + "/department/100").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Department not found with id: 100"))
            .andExpect(jsonPath("$.flag").value(false))
            .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void updateDepartmentSuccess() throws Exception {
    //given
    DepartmentDto departmentDto = new DepartmentDto(100L,"IT", "Lagos", "IT-001");

    var json = objectMapper.writeValueAsString(departmentDto);

    //data the service layer will return
    var updatedDepartment = new Department();
    updatedDepartment.setId(100L);
    updatedDepartment.setName("IT");
    updatedDepartment.setLocation("Lagos-updated");
    updatedDepartment.setCode("IT-001");

    //In the given method, use the Mockito.eq for the id and not the raw value and use Mockito.any for the Department object
    given(departmentService.updateDepartment(Mockito.eq(100L), Mockito.any(Department.class))).willReturn(updatedDepartment);

    //when and then
    this.mockMvc.perform(put(baseUrl + "/department/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Update Success"))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.data.departmentId").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("IT"))
            .andExpect(jsonPath("$.data.location").value("Lagos-updated"))
            .andExpect(jsonPath("$.data.code").value("IT-001"));
  }

  @Test
  void updateDepartmentFailure() throws Exception {
    //given
    DepartmentDto departmentDto = new DepartmentDto(100L,"IT", "Lagos", "IT-001");

    var json = objectMapper.writeValueAsString(departmentDto);

    //data the service layer will return
    var updatedDepartment = new Department();
    updatedDepartment.setId(100L);
    updatedDepartment.setName("IT");
    updatedDepartment.setLocation("Lagos-updated");
    updatedDepartment.setCode("IT-001");

    //In the given method, use the Mockito.eq for the id and not the raw value and use Mockito.any for the Department object
    given(departmentService.updateDepartment(Mockito.eq(100L), Mockito.any(Department.class))).willThrow(new DepartmentNotFoundException(100L));

    //when and then
    this.mockMvc.perform(put(baseUrl + "/department/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Department not found with id: 100"))
            .andExpect(jsonPath("$.flag").value(false))
            .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void deleteDepartmentSuccess() throws Exception {
    //given
    doNothing().when(departmentService).deleteDepartment(100L);

    //when and then
    this.mockMvc.perform(delete(baseUrl + "/department/100").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Delete Success"))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.data").isEmpty());
  }

}