package com.bank.users.mapper;

import com.bank.users.dto.ClientRequestDTO;
import com.bank.users.dto.ClientUpdateDTO;
import com.bank.users.model.Client;
import com.bank.users.model.Person;

public class ClientMapper {

    public static Client fromDTO(ClientRequestDTO dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setGender(dto.getGender());
        person.setAge(dto.getAge());
        person.setIdentification(dto.getIdentification());
        person.setAddress(dto.getAddress());
        person.setPhone(dto.getPhone());

        Client client = new Client();
        client.setClientId(dto.getClientId());
        client.setPassword(dto.getPassword());
        client.setStatus(dto.getStatus());
        client.setPerson(person);

        return client;
    }

    public static void updateClientFromDTO(ClientUpdateDTO dto, Client client) {
        if (dto.getName() != null) client.getPerson().setName(dto.getName());
        if (dto.getGender() != null) client.getPerson().setGender(dto.getGender());
        if (dto.getAge() != null) client.getPerson().setAge(dto.getAge());
        if (dto.getIdentification() != null) client.getPerson().setIdentification(dto.getIdentification());
        if (dto.getAddress() != null) client.getPerson().setAddress(dto.getAddress());
        if (dto.getPhone() != null) client.getPerson().setPhone(dto.getPhone());

        if (dto.getClientId() != null) client.setClientId(dto.getClientId());
        if (dto.getPassword() != null) client.setPassword(dto.getPassword());
        if (dto.getStatus() != null) client.setStatus(dto.getStatus());
    }

}
