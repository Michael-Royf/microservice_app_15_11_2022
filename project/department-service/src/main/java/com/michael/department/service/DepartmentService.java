package com.michael.department.service;

import com.michael.department.exception.DepartmentNotFoundException;
import com.michael.department.payload.request.DepartmentRequest;
import com.michael.department.payload.response.DepartmentResponse;

public interface DepartmentService {
    DepartmentResponse saveDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse getDepartmentByCode(String departmentCode) throws DepartmentNotFoundException;
}
