package com.example.labjava.response;

import java.util.List;

public class ResultResponse {

    public List<Double> resultlist;

    public double max;
    public double min;
    public double avg;

    public ResultResponse(List<Double> resultlist, double max, double min, double avg){
        this.resultlist = resultlist;
        this.max = max;
        this.min = min;
        this.avg = avg;
    }
}
