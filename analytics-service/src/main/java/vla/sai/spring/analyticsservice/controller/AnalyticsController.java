package vla.sai.spring.analyticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vla.sai.spring.analyticsservice.dto.AnalyticsDto;
import vla.sai.spring.analyticsservice.service.AnalyticsService;

import javax.script.ScriptException;
import java.io.IOException;


@RestController
@RequestMapping(value = "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping(path = "/smoothing-graph")
    public void smoothing(AnalyticsDto analyticsDto) throws ScriptException, IOException {
        analyticsService.smoothingGraph(analyticsDto);
    }

    @PostMapping(path = "/holt-winters-graph")
    public void holtWinters(AnalyticsDto analyticsDto) throws ScriptException, IOException {
        analyticsService.holtWintersGraph(analyticsDto);
    }

    @PostMapping(path = "/arima-model")
    public void arima(AnalyticsDto analyticsDto) throws ScriptException, IOException {
        analyticsService.arimaAnalytics(analyticsDto);
    }

    @PostMapping(path = "/sarima-model")
    public void sarima(AnalyticsDto analyticsDto) throws ScriptException, IOException {
        analyticsService.sarimaAnalytics(analyticsDto);
    }

}