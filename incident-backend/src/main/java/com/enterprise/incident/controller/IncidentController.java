package com.enterprise.incident.controller;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import com.enterprise.incident.dto.IncidentRequestDto;
import com.enterprise.incident.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createIncident(@Valid @RequestBody IncidentRequestDto request) {
        incidentService.createIncident(request);
    }

    @PostMapping("/query")
    public IncidentQueryResponseDto queryIncidents(
            @Valid @RequestBody IncidentQueryRequestDto request) {
        return incidentService.queryIncidents(request);
    }
}
