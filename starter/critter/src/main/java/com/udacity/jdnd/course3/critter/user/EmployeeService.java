package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;


@Service
public class EmployeeService {

 @Autowired
 EmployeeRepository employeeRepository;

public void createEmployee(Employee employee){
    employeeRepository.save(employee);
}

public Employee getEmployeeById(Long id){
    return employeeRepository.findById(id).get();
}

public List<Employee> getAllEmployees(){
    return employeeRepository.findAll();
}

    public List<Employee> findEmployeesByDates(DayOfWeek day){

        return employeeRepository.getAllByDaysAvailableContains(day);
    }

    public List<Employee> findAllEmployeesByIds(List<Long> ids){
    return employeeRepository.findAllById(ids);
    }

}
