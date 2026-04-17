package com.pets.controllers;

import com.pets.dto.PetDTO;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.data.domain.Pageable;
import java.net.URI;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value="/pets")
@Tag(name = "Pets", description = "Pets available for adoption management")
public class PetController {

    @Autowired
    private PetService service;

    @Operation(summary = "List all pets")
    @ApiResponse(responseCode = "200", description = "List was successfully retrieved")
    @GetMapping
    public ResponseEntity<Page<PetDTO>> findAll(@ParameterObject Pageable pageable) {
        Page<PetDTO> page = service.findAll(pageable).map(PetDTO::new);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Find by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Integer id) {
        Pet obj = service.findById(id);
        return ResponseEntity.ok().body(new PetDTO(obj));
    }


    @Operation(summary = "Update pet data")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pet successfully updated"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody PetDTO objDto, @PathVariable Integer id){
        Pet obj = service.fromDTO(objDto);
        obj.setId(id);
        service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Register a new pet")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pet successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
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

    @Operation(summary = "Remove a pet from system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pet removed"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @Operation(summary = "Find a pet adopter")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Adopter found"),
            @ApiResponse(responseCode = "404", description = "Adopter not found")
    })
    @GetMapping("/{id}/adopters")
    public ResponseEntity<List<Adopter>> findAdopter(@PathVariable Integer id){
        Pet obj = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(obj.getAdopter()));
    }

    @Operation(summary = "Find a pet with name")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<PetDTO>> search(@RequestParam String name) {
        List<Pet> pets = service.findByNameLike(name);

        List<PetDTO> dtoPet = pets.stream()
                .map(PetDTO::new)
                .toList();

        return ResponseEntity.ok().body(dtoPet);
    }

    @Operation(summary = "List pet available to be adopted")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List was successfully retrieved")
    })
    @GetMapping("/available")
    public ResponseEntity<List<PetDTO>> listAvailable() {
        List<Pet> pets = service.listAvailableToAdoptPet();

        List<PetDTO> dtoPet = pets.stream()
                .map(PetDTO::new)
                .toList();
        return ResponseEntity.ok().body(dtoPet);
    }
}
