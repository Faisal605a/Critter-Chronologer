package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Data.user.Employee;
import com.udacity.jdnd.course3.critter.Data.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.Data.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.Data.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleService scheduleService;


    public List<EmployeeDTO> getAllEmployees(){ return EmployeesToEmployeeDTOs(employeeRepository.findAll()); }
    public EmployeeDTO SaveEmployee(EmployeeDTO employeeDTO){
        List<EmployeeSkill> employeeSkills = new ArrayList<>(employeeDTO.getSkills());

        employeeDTO.setId(employeeRepository.save(
                new Employee(
                        employeeDTO.getId(),
                        employeeDTO.getName(),
                        employeeSkills,
                        employeeDTO.getDaysAvailable()
                )
                ).getId()
        );

        return employeeDTO;
    }

    public void setAvailability(Set<DayOfWeek> dayOfWeeks, long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);

        employee.setDaysAvailable(dayOfWeeks);
        employeeRepository.save(employee);
    }
    public Employee getEmployeeById(long id){ return   employeeRepository.getOne(id); }
    public EmployeeDTO getEmployeeDtoById(long id){
        Employee employee = employeeRepository.getOne(id);

        return  new EmployeeDTO(
                        employee.getId(),
                        employee.getName(),
                        new HashSet<>(employee.getSkills()),
                        employee.getDaysAvailable()
                ) ;
    }
    public List<EmployeeDTO> getAvailabileEmployee(EmployeeRequestDTO employeeRequestDTO){

        List<Employee> employees = employeeRepository.getEmployeesByDaysAvailable(employeeRequestDTO.getDate().getDayOfWeek());
        List<Employee> avilableEmployee = new ArrayList<>();

        for(Employee employee : employees){
          if (employeeRequestDTO.getSkills().stream().allMatch( requestedSkills-> employee.getSkills().stream().anyMatch(employeeSkills-> requestedSkills == employeeSkills )) )
              avilableEmployee.add(employee);
        }

        return EmployeesToEmployeeDTOs(avilableEmployee);
    }

    public List<EmployeeDTO> EmployeesToEmployeeDTOs(List<Employee> employees){
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        employees.stream().
                forEach(employee ->
                        employeeDTOS.
                                add(new EmployeeDTO(
                                                employee.getId(),
                                                employee.getName(),
                                                new HashSet<>(employee.getSkills()),
                                                employee.getDaysAvailable()))
                );
        return employeeDTOS;
    }

}
