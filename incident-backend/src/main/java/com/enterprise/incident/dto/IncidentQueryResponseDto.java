package com.enterprise.incident.dto;

import java.util.List;

public class IncidentQueryResponseDto {

    private List<IncidentResultDto> results;

    public List<IncidentResultDto> getResults() {
        return results;
    }

    public void setResults(List<IncidentResultDto> results) {
        this.results = results;
    }
}