package com.devsuperior.clientcrud.services;

import com.devsuperior.clientcrud.dto.ClientDTO;
import com.devsuperior.clientcrud.entities.Client;
import com.devsuperior.clientcrud.mapper.ClientMapper;
import com.devsuperior.clientcrud.repositories.ClientRepository;
import com.devsuperior.clientcrud.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return clientMapper.clientToClientDTO(clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Register not found")));
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(product -> clientMapper.clientToClientDTO(product));
    }


    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        return clientMapper.clientToClientDTO(clientRepository.save(clientMapper.clientDTOToClient(clientDTO)));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        try {
            Client client = clientRepository.getReferenceById(id);
            clientMapper.copyClientDTOToClient(clientDTO, client);
            return clientMapper.clientToClientDTO(clientRepository.save(client));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Register not found");
        }
        clientRepository.deleteById(id);
    }
}
