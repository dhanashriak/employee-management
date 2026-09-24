package com.example.hr.service;

import com.example.hr.model.Role;
import com.example.hr.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {
    private final RoleRepository repo;
    public RoleService(RoleRepository repo) { this.repo = repo; }

    public Role create(Role r) { return repo.save(r); }
    public Role update(Long id, Role r) {
        Role existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        existing.setName(r.getName());
        return repo.save(existing);
    }
    public Role get(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found")); }
    public List<Role> list() { return repo.findAll(); }
    public void delete(Long id) { repo.deleteById(id); }
}
