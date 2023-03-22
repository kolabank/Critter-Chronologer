package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    public void saveSchedule (Schedule schedule){

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetIds(Long petId){
        Pet pet = petRepository.findById(petId).get();
        return scheduleRepository.findAllByPetsContains(pet);
    }

    public List<Schedule> findScheduleByEmployeeIds(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();
        return scheduleRepository.findAllByEmployeesContains(employee);
    }

    public List<Schedule> findScheduleByPets(List<Pet> pets){

        return scheduleRepository.findAllByPetsIn(pets);
    }
}
