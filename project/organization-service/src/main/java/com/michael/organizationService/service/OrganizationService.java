package com.michael.organizationService.service;

import com.michael.organizationService.exception.OrganizationNotFoundException;
import com.michael.organizationService.payload.request.OrganizationRequest;
import com.michael.organizationService.payload.response.OrganizationResponse;

public interface OrganizationService {

    OrganizationResponse saveOrganization(OrganizationRequest organizationRequest);

    OrganizationResponse getOrganizationByCode(String organizationCode) throws OrganizationNotFoundException;
}
