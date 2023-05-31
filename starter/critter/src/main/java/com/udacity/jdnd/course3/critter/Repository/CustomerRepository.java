package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Data.pet.Pet;
import com.udacity.jdnd.course3.critter.Data.user.Customer;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    public List<Customer> getCustomersByPets(List<Pet> pets);

    public Customer getCustomerByPets(Pet pet);

//    public void updateCustomer();
}

