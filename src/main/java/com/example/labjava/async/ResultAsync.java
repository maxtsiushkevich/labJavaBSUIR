package com.example.labjava.async;

import com.example.labjava.model.TimeModel;
import com.example.labjava.service.TimeControllerService;
import com.example.labjava.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ResultAsync {
    private final TimeControllerService resultService;
    public final TimeService timeService;

    @Autowired
    public ResultAsync(TimeControllerService resultService, TimeService timeService) {
        this.resultService = resultService;
        this.timeService = timeService;
    }


    public int saveModel(TimeModel result)
    {
        resultService.save(result);
        return result.getId();
    }


    public CompletableFuture<Integer> computeAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeModel result = resultService.findOne(id);
                Thread.sleep(10000);

                result.setTime(timeService.calculate(result.getDistance(), result.getSpeed()));
                resultService.save(result);
                return result.getId();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}