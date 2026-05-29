package com.enterprise.incident.service;

import com.enterprise.incident.client.RagClient;
import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import com.enterprise.incident.dto.IncidentRequestDto;
import com.enterprise.incident.entity.Incident;
import com.enterprise.incident.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository repository;
    private final RagClient ragClient;

    public IncidentServiceImpl(IncidentRepository repository, RagClient ragClient) {
        this.repository = repository;
        this.ragClient = ragClient;
    }

    @Override
    public void createIncident(IncidentRequestDto request) {
        Incident incident = new Incident();
        incident.setIncidentId(request.getIncidentId());
        incident.setService(request.getService());
        incident.setSeverity(request.getSeverity());
        incident.setIncidentType(request.getIncidentType());
        incident.setDescription(request.getDescription());
        incident.setImpact(request.getImpact());
        incident.setRootCause(request.getRootCause());
        incident.setResolution(request.getResolution());
        incident.setPrevention(request.getPrevention());
        incident.setTimestamp(Instant.now());

        repository.save(incident);
    }

    @Override
    public IncidentQueryResponseDto queryIncidents(
            IncidentQueryRequestDto request) {

        return ragClient.query(request);
    }
}
