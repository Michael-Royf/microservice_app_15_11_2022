package com.michael.department.controller;

import com.michael.department.exception.DepartmentNotFoundException;
import com.michael.department.payload.request.DepartmentRequest;
import com.michael.department.payload.response.DepartmentResponse;
import com.michael.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping()
    public ResponseEntity<DepartmentResponse> saveDepartment(@RequestBody @Valid DepartmentRequest departmentRequest) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{departmentCode}")
    public ResponseEntity<DepartmentResponse> getDepartmentByDepartmentCode(@PathVariable String departmentCode) throws DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode),HttpStatus.OK);
    }


}
