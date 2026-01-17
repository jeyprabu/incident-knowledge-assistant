package com.enterprise.incident.dto;

import jakarta.validation.constraints.NotBlank;

public class IncidentQueryRequestDto {

    @NotBlank
    private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

    
}
