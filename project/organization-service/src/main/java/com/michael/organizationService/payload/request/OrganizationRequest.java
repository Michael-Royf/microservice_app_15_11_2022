package com.michael.organizationService.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrganizationRequest {
    @NotBlank
    private String organizationName;
    @NotBlank
    private String organizationDescription;
    @NotBlank
    private String organizationCode;
//    private String createdDate;
}
