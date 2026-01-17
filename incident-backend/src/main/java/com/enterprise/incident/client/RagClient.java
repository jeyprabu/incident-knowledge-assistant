package com.enterprise.incident.client;

import com.enterprise.incident.dto.IncidentQueryResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

@Component
public class RagClient {

    private final WebClient webClient;

    public RagClient(WebClient ragWebClient) {
        this.webClient = ragWebClient;
    }

    public IncidentQueryResponseDto query(String question) {
        return webClient.post()
                .uri("/rag/query")
                .bodyValue(Map.of("query", question))
                .retrieve()
                .bodyToMono(IncidentQueryResponseDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}
