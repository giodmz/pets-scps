package com.pets.resources;



import com.pets.dto.AdopterDTO;
import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.services.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value="/adopters")
public class AdopterResource {
    @Autowired
    private AdopterService service;

    @GetMapping
    public ResponseEntity<List<AdopterDTO>> findAll() {
        List<Adopter> list = service.findAll();
        // Adopter -> AdopterDTO
        List<AdopterDTO> listDto = list.stream().map(AdopterDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdopterDTO> findById(@PathVariable Integer id) {
        Adopter obj = service.findById(id);
        return ResponseEntity.ok().body(new AdopterDTO(obj));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody AdopterDTO objDto, @PathVariable Integer id){
        Adopter obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> update(@RequestBody AdopterDTO objDto){
        Adopter obj = service.fromDTO(objDto);
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

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<Address>> findAdopter(@PathVariable Integer id){
        Adopter obj = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(obj.getAddress()));
    }

}
