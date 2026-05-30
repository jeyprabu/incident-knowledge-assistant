package com.enterprise.incident.controller;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import com.enterprise.incident.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin
public class IncidentController {

	private final IncidentService incidentService;

	public IncidentController(IncidentService incidentService) {

		this.incidentService = incidentService;
	}

	@PostMapping("/query")
	public IncidentQueryResponseDto queryIncidents(@Valid @RequestBody IncidentQueryRequestDto request) {

		return incidentService.queryIncidents(request);
	}
}