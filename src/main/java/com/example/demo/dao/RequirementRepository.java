package com.example.demo.dao;

import com.example.demo.model.Requirement;
import org.springframework.data.repository.CrudRepository;

public interface RequirementRepository extends CrudRepository<Requirement, Long> {
}
