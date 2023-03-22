package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    @Query("SELECT e FROM Employee e WHERE :skill MEMBER OF e.skills")
//   List<Employee> getAllBySkillsContainsIn(Set<EmployeeSkill> skill);

    List<Employee> getAllByDaysAvailableContains(DayOfWeek day);

}
