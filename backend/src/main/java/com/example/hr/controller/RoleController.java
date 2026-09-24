package com.example.hr.controller;

import com.example.hr.model.Role;
import com.example.hr.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Validated
public class RoleController {
    private final RoleService service;
    public RoleController(RoleService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody Role r) {
        Role created = service.create(r);
        return ResponseEntity.created(URI.create("/api/roles/" + created.getId())).body(created);
    }

    @GetMapping
    public List<Role> list() { return service.list(); }

    @GetMapping("/{id}")
    public Role get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @Valid @RequestBody Role r) { return service.update(id, r); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
