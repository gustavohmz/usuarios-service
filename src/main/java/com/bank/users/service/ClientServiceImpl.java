package com.bank.users.service.impl;

import com.bank.users.dto.ClientRequestDTO;
import com.bank.users.dto.ClientUpdateDTO;
import com.bank.users.exception.ResourceNotFoundException;
import com.bank.users.mapper.ClientMapper;
import com.bank.users.model.Client;
import com.bank.users.model.Person;
import com.bank.users.repository.ClientRepository;
import com.bank.users.repository.PersonRepository;
import com.bank.users.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Client createClient(ClientRequestDTO dto) {
        Client client = ClientMapper.fromDTO(dto);

        Person savedPerson = personRepository.save(client.getPerson());
        client.setPerson(savedPerson);

        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, ClientUpdateDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));

        ClientMapper.updateClientFromDTO(dto, client);
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with ID: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
    }
}
