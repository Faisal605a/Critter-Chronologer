package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.Data.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {



    public List<Schedule> getSchedulesByPets(Pet pets);




    public List<Schedule> getSchedulesByEmployees(Employee employees);
}


