package com.example.labjava.controller;

import java.util.List;

public class CalcResponse {
    private StatResponse statResponse;
    private List<TimeResponse> responses;

    public CalcResponse(StatResponse statResponse, List<TimeResponse> responses) {
        this.statResponse = statResponse;
        this.responses = responses;
    }

    public StatResponse getStatResponse() {
        return statResponse;
    }

    public void setStatResponse(StatResponse statResponse) {
        this.statResponse = statResponse;
    }

    public List<TimeResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<TimeResponse> responses) {
        this.responses = responses;
    }
}