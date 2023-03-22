package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertscheduleDTOtoschedule(scheduleDTO);
        List<Employee> employees = employeeService.findAllEmployeesByIds(scheduleDTO.getEmployeeIds());
        List<Pet> pets = petService.findAllPetsByIds(scheduleDTO.getPetIds());
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        scheduleService.saveSchedule(schedule);
        return convertscheduletoscheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> allSchedules = scheduleService.getAllSchedules();

        return allSchedules.stream().map(this::convertscheduletoscheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> foundSchedules = scheduleService.findScheduleByPetIds(petId);
        return foundSchedules.stream().map(this::convertscheduletoscheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> foundSchedules = scheduleService.findScheduleByEmployeeIds(employeeId);

        return foundSchedules.stream().map(this::convertscheduletoscheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    //use the customerId to get the pets related to the customer
        List<Pet> pets = petService.getPetsByOwner(customerId);

        //use the obtained pets to get the schedule and convert this to a DTO
       List<Schedule> schedules = scheduleService.findScheduleByPets(pets);
       List<ScheduleDTO> scheduleDTOS = schedules.stream().map(this::convertscheduletoscheduleDTO).collect(Collectors.toList());
        return scheduleDTOS;
    }

    private Schedule convertscheduleDTOtoschedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private ScheduleDTO convertscheduletoscheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));

        return scheduleDTO;
    }
}
