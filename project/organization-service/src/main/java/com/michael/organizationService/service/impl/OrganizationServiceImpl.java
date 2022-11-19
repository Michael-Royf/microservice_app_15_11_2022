package com.michael.organizationService.service.impl;

import com.michael.organizationService.entity.Organization;
import com.michael.organizationService.exception.OrganizationNotFoundException;
import com.michael.organizationService.payload.request.OrganizationRequest;
import com.michael.organizationService.payload.response.OrganizationResponse;
import com.michael.organizationService.repository.OrganizationRepository;
import com.michael.organizationService.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public OrganizationResponse saveOrganization(OrganizationRequest organizationRequest) {
        Organization organization = Organization.builder()
                .organizationName(organizationRequest.getOrganizationName())
                .organizationCode(organizationRequest.getOrganizationCode())
                .organizationDescription(organizationRequest.getOrganizationDescription())
                .build();
        organization = organizationRepository.save(organization);
        return mapper.map(organization, OrganizationResponse.class);
    }

    @Override
    public OrganizationResponse getOrganizationByCode(String organizationCode) throws OrganizationNotFoundException {
        Organization organization = organizationRepository
                .findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new OrganizationNotFoundException(String.format("Organization with Code %s not found", organizationCode)));
        return mapper.map(organization, OrganizationResponse.class);
    }
}
