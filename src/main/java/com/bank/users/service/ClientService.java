package com.bank.users.service;

import com.bank.users.dto.ClientRequestDTO;
import com.bank.users.dto.ClientUpdateDTO;
import com.bank.users.model.Client;

import java.util.List;

public interface ClientService {
    Client createClient(ClientRequestDTO dto);
    Client updateClient(Long id, ClientUpdateDTO dto);
    void deleteClient(Long id);
    List<Client> getAllClients();
    Client getClientById(Long id);
}
