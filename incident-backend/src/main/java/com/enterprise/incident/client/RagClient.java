package com.enterprise.incident.client;

import com.enterprise.incident.dto.IncidentQueryRequestDto;
import com.enterprise.incident.dto.IncidentQueryResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class RagClient {

	private final WebClient webClient;

	public RagClient(WebClient ragWebClient) {
		this.webClient = ragWebClient;
	}

	public IncidentQueryResponseDto query(IncidentQueryRequestDto request) {
//		return webClient.post().uri("/rag/query")
//				.bodyValue(Map.of("query", request.getQuestion(), "service", request.getService(), "severity",
//						request.getSeverity(), "incidentType", request.getIncidentType()))
//				.retrieve().bodyToMono(IncidentQueryResponseDto.class).block();

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
