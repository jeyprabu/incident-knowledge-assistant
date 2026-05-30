package com.enterprise.incident.service;

import com.enterprise.incident.client.RagClient;
import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import org.springframework.stereotype.Service;

@Service
public class IncidentServiceImpl implements IncidentService {

	private final RagClient ragClient;

	public IncidentServiceImpl(RagClient ragClient) {

		this.ragClient = ragClient;
	}

	@Override
	public IncidentQueryResponseDto queryIncidents(IncidentQueryRequestDto request) {

		return ragClient.query(request);
	}
}