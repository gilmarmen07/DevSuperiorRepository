package com.devsuperior.clientcrud.repositories;

import com.devsuperior.clientcrud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ClientRepository extends JpaRepository<Client, Long> {
}
