package com.michael.department.service.impl;

import com.michael.department.entity.Department;
import com.michael.department.exception.DepartmentNotFoundException;
import com.michael.department.payload.request.DepartmentRequest;
import com.michael.department.payload.response.DepartmentResponse;
import com.michael.department.repository.DepartmentRepository;
import com.michael.department.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public DepartmentResponse saveDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .departmentDescription(departmentRequest.getDepartmentDescription())
                .departmentCode(departmentRequest.getDepartmentCode())
                .build();
        department = departmentRepository.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }


    @Override
    public DepartmentResponse getDepartmentByCode(String departmentCode) throws DepartmentNotFoundException {
        Department department = departmentRepository
                .findByDepartmentCode(departmentCode)
                .orElseThrow (() -> new DepartmentNotFoundException(String.format("Department with department code %s not found", departmentCode)));
        return mapper.map(department, DepartmentResponse.class);
    }
}
