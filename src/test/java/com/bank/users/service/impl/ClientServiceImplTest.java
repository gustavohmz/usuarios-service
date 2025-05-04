package com.bank.users.service.impl;

import com.bank.users.dto.ClientRequestDTO;
import com.bank.users.dto.ClientUpdateDTO;
import com.bank.users.exception.ResourceNotFoundException;
import com.bank.users.mapper.ClientMapper;
import com.bank.users.model.Client;
import com.bank.users.model.Person;
import com.bank.users.repository.ClientRepository;
import com.bank.users.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private com.bank.users.service.impl.ClientServiceImpl clientService;

    private ClientRequestDTO sampleDTO;
    private ClientUpdateDTO updateDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleDTO = new ClientRequestDTO();
        sampleDTO.setName("John");
        sampleDTO.setGender("Male");
        sampleDTO.setAge(30);
        sampleDTO.setIdentification("ID123");
        sampleDTO.setAddress("123 Main St");
        sampleDTO.setPhone("123456789");
        sampleDTO.setClientId("john01");
        sampleDTO.setPassword("pass123");
        sampleDTO.setStatus(true);
        updateDTO = new ClientUpdateDTO();
        updateDTO.setAddress("New Address");
        updateDTO.setPhone("987654321");

    }

    @Test
    void testCreateClient() {
        Person savedPerson = new Person();
        savedPerson.setId(1L);
        when(personRepository.save(any())).thenReturn(savedPerson);

        Client savedClient = new Client();
        savedClient.setId(1L);
        when(clientRepository.save(any())).thenReturn(savedClient);

        Client result = clientService.createClient(sampleDTO);

        assertNotNull(result);
        verify(personRepository).save(any());
        verify(clientRepository).save(any());
    }

    @Test
    void testUpdateClient_Success() {
        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setPerson(new Person());

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any())).thenReturn(existingClient);

        Client updated = clientService.updateClient(1L, updateDTO);

        assertNotNull(updated);
        verify(clientRepository).save(any());
    }

    @Test
    void testUpdateClient_ClientNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(99L, updateDTO));
    }

    @Test
    void testDeleteClient_Success() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    void testDeleteClient_NotFound() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(99L));
    }

    @Test
    void testGetClientById_Success() {
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client found = clientService.getClientById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(99L));
    }

    @Test
    void testGetAllClients() {
        List<Client> mockList = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(mockList);

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
    }
}
