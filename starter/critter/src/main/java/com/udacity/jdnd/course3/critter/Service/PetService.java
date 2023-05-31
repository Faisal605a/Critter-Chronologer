package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.Data.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Data.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerService customerService;

    public PetDTO getPetDTOById(Long petId){
        Pet pet =  petRepository.getOne(petId);
        return new PetDTO(
                pet.getId(),
                pet.getType(),
                pet.getName(),
                pet.getCustomer().getId(),
                pet.getBirthDate(),
                pet.getNotes());
    }

    public Pet getPetById(Long petId){
        return petRepository.getOne(petId);
    }

    public PetDTO savePet(PetDTO petDTO){
        Customer customer = customerService.getCustomerById(petDTO.getOwnerId());

        List<Pet> pets = new ArrayList<>();
        if(customer.getPets()!=null)
            pets.addAll(customer.getPets());

        Pet pet =  new Pet(
                petDTO.getType(),
                petDTO.getName(),
                customer,
                petDTO.getBirthDate(),
                petDTO.getNotes()
        );

        pets.add(pet);
        customer.setPets(pets);
        Customer customer1=customerService.saveCustomer(customer);
        petDTO.setId(customer1.getPets().get(customer1.getPets().size()-1).getId());

        return petDTO;
    }

    public List<PetDTO> getAllPets(){
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petDTOS = new ArrayList<>();

        pets.stream().
                forEach(
                        pet -> petDTOS.add(
                                new PetDTO(
                                        pet.getId(),
                                        pet.getType(),
                                        pet.getName(),
                                        pet.getCustomer().getId(),
                                        pet.getBirthDate(),
                                        pet.getNotes()))
                );

       return petDTOS;
    }

    public List<PetDTO> getPetByOwnerId(long id){

        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petRepository.getPetsByCustomerOrderById(customerService.getCustomerById(id));
        System.out.println("-----------------------------------------------");
        System.out.println("Pets : " + pets.get(0).getName());
        pets.stream().forEach(
                pet-> petDTOS.add(
                        new PetDTO
                        (
                                pet.getId(),
                                pet.getType(),
                                pet.getName(),
                                pet.getCustomer().getId(),
                                pet.getBirthDate(),
                                pet.getNotes()
                        )
                )
        );
        return petDTOS;
    }
}
