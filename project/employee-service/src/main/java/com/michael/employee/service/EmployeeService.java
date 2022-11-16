package com.michael.employee.service;

import com.michael.employee.entity.Employee;
import com.michael.employee.payload.request.EmployeeRequest;
import com.michael.employee.payload.response.APIResponseDTO;
import com.michael.employee.payload.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse saveEmployee(EmployeeRequest employeeRequest);

    APIResponseDTO getEmployeeById(Long employeeId);

}
