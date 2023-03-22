package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public void createPet(Pet pet){
        petRepository.save(pet);
    }

    public Pet getPetById(Long id){
       return petRepository.findById(id).get();
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();

       }

       public List<Pet> findAllPetsByIds(List<Long> petIds){
        return petRepository.findAllById(petIds);
       }

       public List<Pet> getPetsByOwner(long ownerId){
      Customer customer =  customerRepository.findById(ownerId).get();
      return petRepository.findByCustomer(customer);
       }

}
