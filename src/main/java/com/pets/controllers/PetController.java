package com.pets.controllers;

import com.pets.dto.PetDTO;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/pets")
public class PetController {

    @Autowired
    private PetService service;

    @GetMapping
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


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody PetDTO objDto, @PathVariable Integer id){
        Pet obj = service.fromDTO(objDto);
        obj.setId(id);
        service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody PetDTO objDto){
        Pet obj = service.fromDTO(objDto);
        service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/{id}/adopters")
    public ResponseEntity<List<Adopter>> findAdopter(@PathVariable Integer id){
        Pet obj = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(obj.getAdopter()));
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<PetDTO>> search(@RequestParam String name) {
//        List<Pet> obj = service.findByNameLike(name);
//        return ResponseEntity.ok().body();
//    }

//    @GetMapping("/available")
//    public ResponseEntity<List<PetDTO>> listAvailable() { ... }
}
