package com.example.hr.controller;

import com.example.hr.model.Employee;
import com.example.hr.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository repo;
    public EmployeeController(EmployeeRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Employee> list() { return repo.findAll(); }

    @PostMapping
    public Employee create(@RequestBody Employee e) { return repo.save(e); }
}
