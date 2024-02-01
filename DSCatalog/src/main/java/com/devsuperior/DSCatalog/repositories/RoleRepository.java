package com.devsuperior.DSCatalog.repositories;

import com.devsuperior.DSCatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
