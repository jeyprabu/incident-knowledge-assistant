package com.enterprise.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.incident.entity.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

}
