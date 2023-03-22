package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired PetService petService;

    @Autowired
    CustomerService customerService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        petService.createPet(pet);
        Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
        customer.addPetToCustomer(pet);
        customerService.saveCustomer(customer);
        return convertPettoPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPettoPetDTO(pet);
  
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (int i=0; i<pets.size();i++){
            petDTOs.add(convertPettoPetDTO(pets.get(i)));
        }
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<Pet> pets = petService.getPetsByOwner(ownerId);
    return  pets.stream().map(this::convertPettoPetDTO).collect(Collectors.toList());
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public PetDTO convertPettoPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }

}


