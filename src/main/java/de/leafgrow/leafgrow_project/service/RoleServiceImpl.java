package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.Role;
import de.leafgrow.leafgrow_project.repository.RoleRepository;
import de.leafgrow.leafgrow_project.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRoleUser() {
        Role role = repository.findByTitle("ROLE_USER");

        if(role == null){
            throw new RuntimeException("Database doesn't contain ROLE_USER");
        }

        return role;
    }
}
