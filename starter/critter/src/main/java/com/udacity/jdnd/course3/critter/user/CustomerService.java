package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;

    List<Long> petIdsList = new ArrayList<>();
public void saveCustomer (Customer customer){
    customerRepository.save(customer);
}
    public List<Customer> getAllCustomers(){
   return customerRepository.findAll();
}


public Customer getCustomerById(long id){
    return customerRepository.findById(id).get();
}

public Customer getCustomerByPetId(long petId){
  Pet pet = petRepository.findById(petId).get();
    return customerRepository.findByPetsContaining(pet);
}

public List<Long> addToPetId(long customerId, long petId){
    Customer customer = customerRepository.findById(customerId).get();

    petIdsList.add(petId);
    return petIdsList;
    }

}

    

