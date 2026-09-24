package com.example.hr.controller;

import com.example.hr.model.Employee;
import com.example.hr.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee e) {
        Employee saved = repo.save(e);
        return ResponseEntity.created(URI.create("/api/employees/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody Employee e) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(e.getFirstName());
            existing.setLastName(e.getLastName());
            existing.setEmail(e.getEmail());
            existing.setDepartment(e.getDepartment());
            existing.setYearsOfExperience(e.getYearsOfExperience());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    D@eleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<Employee> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Sort sorting = Sort.by(Sort.Order.by("id"));
        if (sort.length>0) {
            String[] s = sort[0].split(",");
            sorting = Sort.by(new Sort.Order(Sort.Direction.fromString(s.length>1? s[1]:"asc"), s[0]));
        }
        Pageable pageable = PageRequest.of(page, size, sorting);
        if (department != null && !department.isBlank()) {
            return repo.findByDepartmentIgnoreCase(department, pageable);
        }
        return repo.search(q, pageable);
    }
}
