package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        Customer customer = convertCustomerDTOtoCustomer(customerDTO);

        customerService.saveCustomer(customer);
        return convertCustomertoCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();

        return customers.stream()
                .map(this::convertCustomertoCustomerDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
            Pet pet = petService.getPetById(petId);
            Customer customer = customerService.getCustomerById(pet.getCustomer().getId());
            return convertCustomertoCustomerDTO(customer);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee Employee = convertEmployeeDTOtoEmployee(employeeDTO);
        employeeService.createEmployee(Employee);
        return convertEmployeetoEmployeeDTO(Employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
       Employee employee = employeeService.getEmployeeById(employeeId);
       return convertEmployeetoEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.createEmployee(employee);
    }
//

    //  get the skills in the requestDTO. Get the days required too
    //  Find a way to do a search of the database to find employees that have the skills and dates
    // return the employees that are found
    // Cast the return employees to Employee DTO
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
//
      DayOfWeek dateRequested = employeeDTO.getDate().getDayOfWeek();
      Set<EmployeeSkill> skillsRequested = employeeDTO.getSkills();

      List<Employee> employees = employeeService.findEmployeesByDates(dateRequested);

        return employees.stream().filter(employee -> employee.getSkills().containsAll(skillsRequested))
                .map(this::convertEmployeetoEmployeeDTO)
                .collect(Collectors.toList());

    }

//DTO helper methods
    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();

//      customer.setPets();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertCustomertoCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
       customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

        return customerDTO;
    }

    private Employee convertEmployeeDTOtoEmployee(EmployeeDTO EmployeeDTO){
        Employee Employee = new Employee();
        BeanUtils.copyProperties(EmployeeDTO, Employee);
        return Employee;
    }

    private EmployeeDTO convertEmployeetoEmployeeDTO(Employee Employee){
        EmployeeDTO EmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(Employee, EmployeeDTO);
        return EmployeeDTO;
    }

}
