package com.example.hr.service;

import com.example.hr.model.Department;
import com.example.hr.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository repo;
    public DepartmentService(DepartmentRepository repo) { this.repo = repo; }

    public Department create(Department d) { return repo.save(d); }
    public Department update(Long id, Department d) {
        Department existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Department not found"));
        existing.setName(d.getName());
        return repo.save(existing);
    }
    public Department get(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Department not found")); }
    public List<Department> list() { return repo.findAll(); }
    public void delete(Long id) { repo.deleteById(id); }
}
