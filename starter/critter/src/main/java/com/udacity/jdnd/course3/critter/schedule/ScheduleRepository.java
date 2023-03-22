package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.SheetCollate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

        List<Schedule> findAllByPetsContains(Pet pet);
        List<Schedule> findAllByEmployeesContains(Employee employee);

//        @Query("select s from Schedule s where s.pets like :pets ")
        List<Schedule> findAllByPetsIn(List<Pet> pets);

}
