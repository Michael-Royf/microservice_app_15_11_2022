package com.michael.department.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentRequest {
    @NotBlank
    private String departmentName;
    @NotBlank
    private String departmentDescription;
    @NotBlank
    private String departmentCode;
}
