package com.prueba.demo.controller;

import com.prueba.demo.dto.ClientPathParamDto;
import com.prueba.demo.entity.Client;
import com.prueba.demo.exception.ModelNotFoundException;
import com.prueba.demo.service.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clientes", description = "Operaciones relacionadas con la entidad Cliente")
public class ClientController {

    private final IClientService clientService;

    @Operation(
            summary = "Listar todos los clientes",
            description = "Obtiene una lista paginada de todos los clientes registrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
            }
    )
    @GetMapping
    public ResponseEntity<List<Client>> getClients(Pageable pageable) {
        Page<Client> responseDtos = clientService.getAll(pageable);
        return ResponseEntity.ok().body(responseDtos.stream().toList());
    }

    @Operation(
            summary = "Obtener cliente por documento",
            description = "Obtiene la información de un cliente específico usando su número de documento.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
    @GetMapping(path = "/{document}")
    public ResponseEntity<Client> getClient(
            @Parameter(description = "Número de documento del cliente", required = true)
            @Valid ClientPathParamDto pathParamDto
    ) throws ModelNotFoundException {
        return ResponseEntity.ok().body(clientService.getOne(pathParamDto.getDocument()));
    }

    @Operation(
            summary = "Crear un nuevo cliente",
            description = "Guarda un nuevo cliente en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente")
            }
    )
    @PostMapping
    public ResponseEntity<Void> createClient(
            @RequestBody @Valid Client request
    ) {
        clientService.save(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Editar un cliente",
            description = "Edita los datos de un cliente existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
    @PutMapping
    public ResponseEntity<Client> editClient(
            @RequestBody @Valid Client request
    ) throws ModelNotFoundException {
        Client client = clientService.edit(request);
        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina o desactiva un cliente por su número de documento.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
    @DeleteMapping(path = "/{document}")
    public ResponseEntity<Void> deactivateUser(
            @Parameter(description = "Número de documento del cliente", required = true)
            @Valid ClientPathParamDto pathParamDto
    ) throws ModelNotFoundException {
        clientService.delete(pathParamDto.getDocument());
        return ResponseEntity.noContent().build();
    }
}
