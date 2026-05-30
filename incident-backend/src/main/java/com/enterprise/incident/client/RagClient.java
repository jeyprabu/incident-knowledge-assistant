package com.enterprise.incident.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;

@Component
public class RagClient {

	private final WebClient webClient;

	public RagClient(WebClient ragWebClient) {
		this.webClient = ragWebClient;
	}

	public IncidentQueryResponseDto query(IncidentQueryRequestDto request) {

		Map<String, Object> payload = new HashMap<>();

		payload.put("query", request.getQuestion());

		if (request.getService() != null) {
			payload.put("service", request.getService());
		}

		if (request.getSeverity() != null) {
			payload.put("severity", request.getSeverity());
		}

		if (request.getIncidentType() != null) {
			payload.put("incidentType", request.getIncidentType());
		}

		return webClient.post().uri("/rag/query").bodyValue(payload).retrieve()
				.bodyToMono(IncidentQueryResponseDto.class).block();
	}
}