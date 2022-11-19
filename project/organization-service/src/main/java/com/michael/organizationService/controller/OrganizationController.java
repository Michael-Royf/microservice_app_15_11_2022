package com.michael.organizationService.controller;

import com.michael.organizationService.exception.OrganizationNotFoundException;
import com.michael.organizationService.payload.request.OrganizationRequest;
import com.michael.organizationService.payload.response.OrganizationResponse;
import com.michael.organizationService.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping()
    public ResponseEntity<OrganizationResponse> daveOrganization(@RequestBody @Valid OrganizationRequest organizationRequest) {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{organizationCode}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable String organizationCode) throws OrganizationNotFoundException {
     return new ResponseEntity<>(organizationService.getOrganizationByCode(organizationCode),HttpStatus.OK);
    }

}
