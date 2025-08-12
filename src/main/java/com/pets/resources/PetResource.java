package com.pets.resources;

import com.pets.dto.PetDTO;
import com.pets.entities.Pet;
import com.pets.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/pets")
public class PetResource {

    @Autowired
    private PetService service;

    public ResponseEntity<List<PetDTO>> findAll() {
        List<Pet> list = service.findAll();
        // Pet -> PetDTO
        List<PetDTO> listDto = list.stream().map(PetDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Integer id) {
        Pet obj = service.findById(id);
        return ResponseEntity.ok().body(new PetDTO(obj));
    }


}
