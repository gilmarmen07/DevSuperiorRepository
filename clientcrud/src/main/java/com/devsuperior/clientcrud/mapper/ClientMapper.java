package com.devsuperior.clientcrud.mapper;

import com.devsuperior.clientcrud.dto.ClientDTO;
import com.devsuperior.clientcrud.entities.Client;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ClientDTO clientToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    public Client clientDTOToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }

    public void copyClientDTOToClient(ClientDTO clientDTO, Client client) {
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setChildren(clientDTO.getChildren());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setIncome(clientDTO.getIncome());
    }
}
