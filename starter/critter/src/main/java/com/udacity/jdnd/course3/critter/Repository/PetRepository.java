package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    public List<Pet> getPetsByCustomerOrderById(Customer customer);
}
