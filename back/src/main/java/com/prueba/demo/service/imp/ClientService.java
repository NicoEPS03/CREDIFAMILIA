package com.prueba.demo.service.imp;

import com.prueba.demo.entity.Client;
import com.prueba.demo.exception.IntegridadException;
import com.prueba.demo.exception.ModelNotFoundException;
import com.prueba.demo.repository.ClientRepository;
import com.prueba.demo.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientService implements IClientService {

    public static final String ERROR_CLIENT_NOT_FOUND = "Cliente no encontrado";
    public static final String ERROR_CLIENT_DOCUMENT = "Documento ya resgistrado";

    private final ClientRepository clientRepository;

    @Override
    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client getOne(String document) throws ModelNotFoundException {
        return clientRepository.findByDocument(document)
                .orElseThrow(() -> new ModelNotFoundException(ERROR_CLIENT_NOT_FOUND));
    }

    @Override
    public Client save(Client request) throws IntegridadException {
        if(clientRepository.countByDocument(request.getDocument()) >= 1) {
            throw new IntegridadException(ERROR_CLIENT_DOCUMENT);
        }

        request.setViable(viability(request.getBornDate()));
        return clientRepository.save(request);

    }

    @Override
    public Client edit(Client request) throws ModelNotFoundException, IntegridadException {
        Client client = clientRepository.findByDocument(request.getDocument())
                .orElseThrow(() -> new ModelNotFoundException(ERROR_CLIENT_NOT_FOUND));

        Optional.ofNullable(request.getBornDate())
                .filter(newDate -> !newDate.equals(client.getBornDate()))
                .ifPresent(newDate -> {
                    request.setBornDate(newDate);
                    request.setViable(viability(newDate));
                });

        client.updateFromRequest(request);
        return clientRepository.save(client);
    }

    @Override
    public void delete(String document) throws ModelNotFoundException {
        Client client = clientRepository.findByDocument(document)
                .orElseThrow(() -> new ModelNotFoundException(ERROR_CLIENT_NOT_FOUND));

        client.setActive(false);
        clientRepository.save(client);
    }

    /**
     * Determina si una persona es viable según su edad.
     * <p>
     * Este método calcula la edad a partir de la fecha de nacimiento y verifica si
     * se encuentra en el rango permitido (entre 18 y 65 años inclusive).
     *
     * @param bornDate la fecha de nacimiento de la persona.
     * @return {@code true} si la edad está entre 18 y 65 años inclusive; {@code false} en caso contrario.
     */
    public Boolean viability(LocalDate bornDate) {
        int age = Period.between(bornDate, LocalDate.now()).getYears();
        return age >= 18 && age <= 65;
    }

}
