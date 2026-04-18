package com.pets.controllers;



import com.pets.dto.AdopterDTO;
import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.services.AdopterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="/adopters")
@Tag(name = "Adopters", description = "Registered adopters management")
@RequiredArgsConstructor
public class AdopterController {

    private final AdopterService service;

    @Operation(summary = "List all adopters")
    @ApiResponse(responseCode = "200", description = "List was successfully retrieved")
    @GetMapping
    public ResponseEntity<Page<AdopterDTO>> findAll(@ParameterObject Pageable pageable) {
        Page<AdopterDTO> page = service.findAllPageable(pageable).map(AdopterDTO::new);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Find by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Adopter found"),
            @ApiResponse(responseCode = "404", description = "Adopter not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdopterDTO> findById(@PathVariable Integer id) {
        Adopter obj = service.findById(id);
        return ResponseEntity.ok().body(new AdopterDTO(obj));
    }

    @Operation(summary = "Update adopter data")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Adopter successfully updated"),
            @ApiResponse(responseCode = "404", description = "Adopter not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody AdopterDTO objDto, @PathVariable Integer id){
        Adopter obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Register a new adopter")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Adopter successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody AdopterDTO objDto){
        Adopter obj = service.fromDTO(objDto);
        service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Remove a adopter from system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Adopter removed"),
            @ApiResponse(responseCode = "404", description = "Adopter not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @Operation(summary = "Find a adopter address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Invalid ID")
    })
    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<Address>> findAdopter(@PathVariable Integer id){
        Adopter obj = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(obj.getAddress()));
    }

}
