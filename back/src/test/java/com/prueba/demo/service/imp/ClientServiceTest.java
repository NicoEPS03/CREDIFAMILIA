package com.prueba.demo.service.imp;

import com.prueba.demo.entity.Client;
import com.prueba.demo.enums.Occupation;
import com.prueba.demo.exception.IntegridadException;
import com.prueba.demo.exception.ModelNotFoundException;
import com.prueba.demo.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setDocument("123");
        client.setNames("Juan");
        client.setLastNames("Pérez");
        client.setBornDate(LocalDate.of(1990, 1, 1));
        client.setCity("Bogotá");
        client.setEmail("juan@example.com");
        client.setPhone("3001234567");
        client.setOccupation(Occupation.EMPLOYEE);
        client.setViable(true);
        client.setActive(true);
    }

    @Test
    void shouldReturnClientWhenDocumentExists() throws ModelNotFoundException {
        Mockito.when(clientRepository.findByDocument("123")).thenReturn(Optional.of(client));

        Client result = clientService.getOne("123");

        Assertions.assertEquals("Juan", result.getNames());
    }

    @Test
    void shouldThrowModelNotFoundExceptionWhenClientNotExists() {
        Mockito.when(clientRepository.findByDocument("456")).thenReturn(Optional.empty());

        Assertions.assertThrows(ModelNotFoundException.class, () -> {
            clientService.getOne("456");
        });
    }

    @Test
    void shouldSaveClientWhenDocumentIsNotRegistered() throws IntegridadException {
        Mockito.when(clientRepository.countByDocument("123")).thenReturn(0L);
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client saved = clientService.save(client);

        Assertions.assertTrue(saved.getViable());
        Mockito.verify(clientRepository).save(Mockito.any(Client.class));
    }

    @Test
    void shouldThrowIntegridadExceptionWhenDocumentIsAlreadyRegistered() {
        Mockito.when(clientRepository.countByDocument("123")).thenReturn(1L);

        Assertions.assertThrows(IntegridadException.class, () -> {
            clientService.save(client);
        });
    }

    @Test
    void shouldUpdateClientWhenEditing() throws ModelNotFoundException, IntegridadException {
        Mockito.when(clientRepository.findByDocument("123")).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        client.setEmail("nuevo@example.com");
        Client updated = clientService.edit(client);

        Assertions.assertEquals("nuevo@example.com", updated.getEmail());
    }

    @Test
    void shouldDeactivateClient() throws ModelNotFoundException {
        Mockito.when(clientRepository.findByDocument("123")).thenReturn(Optional.of(client));

        clientService.delete("123");

        Mockito.verify(clientRepository).save(Mockito.argThat(c -> !c.getActive()));
    }

    @Test
    void shouldCalculateViabilityTrue() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        Assertions.assertTrue(clientService.viability(birthDate));
    }

    @Test
    void shouldCalculateViabilityFalseIfTooYoung() {
        LocalDate birthDate = LocalDate.now().minusYears(16);
        Assertions.assertFalse(clientService.viability(birthDate));
    }

    @Test
    void shouldCalculateViabilityFalseIfTooOld() {
        LocalDate birthDate = LocalDate.now().minusYears(70);
        Assertions.assertFalse(clientService.viability(birthDate));
    }
}
