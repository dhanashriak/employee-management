package com.example.hr.controller;

import com.example.hr.model.Department;
import com.example.hr.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Validated
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Department> create(@Valid @RequestBody Department d) {
        Department created = service.create(d);
        return ResponseEntity.created(URI.create("/api/departments/" + created.getId())).body(created);
    }

    @GetMapping
    public List<Department> list() { return service.list(); }

    @GetMapping("/{id}")
    public Department get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @Valid @RequestBody Department d) { return service.update(id, d); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
