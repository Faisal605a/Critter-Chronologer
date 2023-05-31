package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.user.Customer;
import com.udacity.jdnd.course3.critter.Data.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private PetService petService;
    @Autowired
    private CustomerRepository customerRepository;
    public CustomerDTO saveCustomerDto(CustomerDTO customerDTO){
        List<Pet> petList = (List<Pet>) customerDTO.getPetIds().stream().map(s-> petService.getPetById(s));

        customerDTO.setId(customerRepository.save(
                new Customer(
                        customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getPhoneNumber(),
                        customerDTO.getNotes(),
                        petList)).getId()
        );

        return customerDTO;
    }
    public CustomerDTO saveNewCustomerDto(CustomerDTO customerDTO){
        customerDTO.setId(customerRepository.save(new Customer(customerDTO.getName(), customerDTO.getPhoneNumber())).getId());
        return customerDTO;
    }
    public Customer saveCustomer(Customer customer){ return customerRepository.save(customer); }
    public Customer getCustomerById( Long customerId){
        return customerRepository.getOne(customerId);
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();

        return customersToCustomerDtos(customerList);
    }
    public CustomerDTO getCustomersByPetId(long petId){
        Pet pet =petService.getPetById(petId);
        Customer customer = customerRepository.getCustomerByPets(pet);

        if(customer == null )
            throw new RuntimeException("No pet with this ID");

        List<Long> petsId = customer.getPets().stream().map(p-> p.getId()).collect(toList());

        return new CustomerDTO(customer.getId(),customer.getName(), customer.getPhoneNumber(),customer.getNotes(),petsId);
    }

    private List<CustomerDTO> customersToCustomerDtos(List<Customer> customers){
        List<CustomerDTO> customerDTOS=new ArrayList<>();

        for(Customer customer : customers){
            List<Long> petsId = new ArrayList<>();
            if(customer.getPets()!=null)
                 petsId =  customer.getPets().stream().map(p-> p.getId()).collect(toList());

            customerDTOS.add(new CustomerDTO(customer.getId(),customer.getName(), customer.getPhoneNumber(),customer.getNotes(),petsId));
        }

        return customerDTOS;
    }
}
