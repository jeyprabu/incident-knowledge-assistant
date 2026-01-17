package com.enterprise.incident.service;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import com.enterprise.incident.dto.IncidentRequestDto;

public interface IncidentService {

    void createIncident(IncidentRequestDto request);

    IncidentQueryResponseDto queryIncidents(IncidentQueryRequestDto request);
    
}
