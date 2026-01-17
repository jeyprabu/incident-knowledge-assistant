package com.enterprise.incident.repository;

import com.enterprise.incident.model.Incident;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class IncidentRepository {

    private final Map<String, Incident> store = new ConcurrentHashMap<>();

    public void save(Incident incident) {
        store.put(incident.getIncidentId(), incident);
    }

    public Incident findById(String incidentId) {
        return store.get(incidentId);
    }
}
