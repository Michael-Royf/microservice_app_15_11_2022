package com.michael.employee.service.impl;

import com.michael.employee.entity.Employee;
import com.michael.employee.exception.EmployeeNotFoundException;
import com.michael.employee.payload.request.EmployeeRequest;
import com.michael.employee.payload.response.APIResponseDTO;
import com.michael.employee.payload.response.DepartmentResponse;
import com.michael.employee.payload.response.EmployeeResponse;
import com.michael.employee.payload.response.OrganizationResponse;
import com.michael.employee.repository.EmployeeRepository;
import com.michael.employee.service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;

    //        @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private WebClient webClient;
//    @Autowired
//    private APIClient apiClient;

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .departmentCode(employeeRequest.getDepartmentCode())
                .organizationCode(employeeRequest.getOrganizationCode())
                .build();
        employee = employeeRepository.save(employee);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    //  @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {
        Employee employee = getEmployeeFromDb(employeeId);
        log.info("Inside getEmployeeById() method");
        //for restTemplate
//        ResponseEntity<DepartmentResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
//                DepartmentResponse.class);
//        DepartmentResponse departmentResponse = responseEntity.getBody();

        //   webClients
        DepartmentResponse departmentResponse = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();

        //   DepartmentResponse departmentResponse = apiClient.getDepartmentByDepartmentCode(employee.getDepartmentCode());

        OrganizationResponse organizationResponse = webClient.get()
                .uri("http://localhost:8083/api/organizations/"+ employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationResponse.class)
                .block();

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
        apiResponseDTO.setEmployeeResponse(employeeResponse);
        apiResponseDTO.setDepartmentResponse(departmentResponse);
        apiResponseDTO.setOrganizationResponse(organizationResponse);
        return apiResponseDTO;
    }


    private Employee getEmployeeFromDb(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId)));
    }

    //если модуль Department перестанет работать, при запросе вернется дефолтное значение
    public APIResponseDTO getDefaultDepartment(Long employeeId, Exception exception) {
        log.info("inside the getDefaultDepartment() method");
        Employee employee = getEmployeeFromDb(employeeId);
        DepartmentResponse departmentResponse = DepartmentResponse.builder()
                .departmentName("R&D Department")
                .departmentCode("RD001")
                .departmentDescription("Research and Development Department")
                .build();
        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
        apiResponseDTO.setEmployeeResponse(employeeResponse);
        apiResponseDTO.setDepartmentResponse(departmentResponse);
        return apiResponseDTO;
    }

}
