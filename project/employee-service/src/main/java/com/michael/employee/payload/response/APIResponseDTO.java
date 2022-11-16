package com.michael.employee.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class APIResponseDTO {
    private EmployeeResponse employeeResponse;
    private DepartmentResponse departmentResponse;
}
