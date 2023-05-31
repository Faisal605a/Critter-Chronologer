package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.Data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Data.user.Employee;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO){

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if(scheduleDTO.getEmployeeIds()!= null )
            employees = scheduleDTO.getEmployeeIds().stream().map(s -> employeeService.getEmployeeById(s)).collect(Collectors.toList());
        if( scheduleDTO.getPetIds() != null)
            pets = scheduleDTO.getPetIds().stream().map(s -> petService.getPetById(s)).collect(Collectors.toList());

        scheduleDTO.setId(scheduleRepository.save(new Schedule
                (
                        scheduleDTO.getId(),
                        employees,
                        pets,
                        scheduleDTO.getDate(),
                        scheduleDTO.getActivities())).getId()
        );

        return scheduleDTO;
    }

    public List<ScheduleDTO> getScheduleByPetId(long id){

        List<Schedule> schedules = scheduleRepository.getSchedulesByPets(petService.getPetById(id));

       return ScheduleToScheduleDto(schedules);
    }
    public List<ScheduleDTO> getAllSchedules(){

        List<Schedule> schedules = scheduleRepository.findAll();

        return ScheduleToScheduleDto(schedules);
    }
    public List<ScheduleDTO> getScheduleForEmployee(long employeeId){

        List<Employee> employees = new ArrayList<>();
        List<Schedule>  schedules = scheduleRepository.getSchedulesByEmployees(employeeService.getEmployeeById(employeeId));

        return ScheduleToScheduleDto(schedules);
    }
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId){

        List<ScheduleDTO>  schedules = new ArrayList<>();

        customerService.
                getCustomerById(customerId).getPets()
                .stream().forEach(pet-> schedules.
                        addAll(getScheduleByPetId(pet.getId())));

        return schedules;
    }

    private List<ScheduleDTO> ScheduleToScheduleDto(List<Schedule> schedules){

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule schedule: schedules){
            List<Long> petsId = new ArrayList<>();
            List<Long> employeesId = new ArrayList<>();
            if(schedule.getPets()!=null && schedule.getEmployees() !=null){

                employeesId = schedule.getEmployees().stream().map(s-> s.getId()).collect(Collectors.toList());
                petsId =  schedule.getPets().stream().map(s-> s.getId()).collect(Collectors.toList());
            }
            scheduleDTOS.add(
                    new ScheduleDTO(schedule.getId(),
                            employeesId,
                            petsId,
                            schedule.getDate(),
                            schedule.getActivities()));
        }
        return scheduleDTOS;
    }
}
