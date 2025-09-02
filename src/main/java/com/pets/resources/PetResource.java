package com.pets.resources;

import com.pets.dto.PetDTO;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.services.PetService;
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
public class PetResource {

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
    public ResponseEntity<Void> update(@RequestBody PetDTO objDto, @PathVariable Integer id){
        Pet obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> update(@RequestBody PetDTO objDto){
        Pet obj = service.fromDTO(objDto);
        obj = service.update(obj);

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
}
