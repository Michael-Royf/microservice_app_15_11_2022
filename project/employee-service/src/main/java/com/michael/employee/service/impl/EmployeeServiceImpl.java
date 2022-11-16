package com.michael.employee.service.impl;

import com.michael.employee.entity.Employee;
import com.michael.employee.exception.EmployeeNotFoundException;
import com.michael.employee.payload.request.EmployeeRequest;
import com.michael.employee.payload.response.APIResponseDTO;
import com.michael.employee.payload.response.DepartmentResponse;
import com.michael.employee.payload.response.EmployeeResponse;
import com.michael.employee.repository.EmployeeRepository;
import com.michael.employee.service.APIClient;
import com.michael.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;

//        @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private WebClient webClient;
    @Autowired
    private APIClient apiClient;

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .departmentCode(employeeRequest.getDepartmentCode())
                .build();
        employee = employeeRepository.save(employee);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {
        Employee employee = getEmployeeFromDb(employeeId);

        //for restTemplate
//        ResponseEntity<DepartmentResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
//                DepartmentResponse.class);
//        DepartmentResponse departmentResponse = responseEntity.getBody();

         //webClients
//        DepartmentResponse departmentResponse =  webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentResponse.class)
//                .block();

        DepartmentResponse departmentResponse = apiClient.getDepartmentByDepartmentCode(employee.getDepartmentCode());

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
        apiResponseDTO.setEmployeeResponse(employeeResponse);
        apiResponseDTO.setDepartmentResponse(departmentResponse);
        return apiResponseDTO;
    }


    private Employee getEmployeeFromDb(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId)));
    }
}
