package com.enterprise.incident.service;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;

public interface IncidentService {

    IncidentQueryResponseDto queryIncidents(
            IncidentQueryRequestDto request);
}