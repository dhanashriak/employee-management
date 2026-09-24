package com.example.hr.repository;

import com.example.hr.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByDepartmentIgnoreCase(String department, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE " +
           "(:q IS NULL OR lower(e.firstName) LIKE lower(concat('%',:q,'%')) OR lower(e.lastName) LIKE lower(concat('%',:q,'%')) OR lower(e.email) LIKE lower(concat('%',:q,'%')))"
    )
    Page<Employee> search(@Param("q") String q, Pageable pageable);
}
