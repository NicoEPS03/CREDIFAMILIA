package com.prueba.demo.controller;

import com.prueba.demo.dto.ClientPathParamDto;
import com.prueba.demo.entity.Client;
import com.prueba.demo.exception.ModelNotFoundException;
import com.prueba.demo.service.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getClients(
            Pageable pageable
    ) {
        Page<Client> responseDtos = clientService.getAll(pageable);
        return  ResponseEntity.ok().body(responseDtos.stream().toList());
    }

    @GetMapping(path = "/{document}")
    public ResponseEntity<Client> getClient(@Valid ClientPathParamDto pathParamDto) throws ModelNotFoundException {
        return ResponseEntity.ok()
                .body(clientService.getOne(pathParamDto.getDocument()));
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody @Valid Client request) {
        clientService.save(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<Client> editClient(@RequestBody @Valid Client request) throws ModelNotFoundException {
        Client client = clientService.edit(request);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping(path = "/{document}")
    public ResponseEntity<Void> deactivateUser(@Valid ClientPathParamDto pathParamDto) throws ModelNotFoundException {
        clientService.delete(pathParamDto.getDocument());
        return ResponseEntity.noContent().build();
    }
}
