package com.michael.employee.service;

import com.michael.employee.payload.response.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service")
public interface APIClient {
    @GetMapping("/api/departments/{departmentCode}")
    DepartmentResponse getDepartmentByDepartmentCode(@PathVariable("departmentCode") String departmentCode);


}
