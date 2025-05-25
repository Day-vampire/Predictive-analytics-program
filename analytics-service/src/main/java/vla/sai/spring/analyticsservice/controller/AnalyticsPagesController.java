package vla.sai.spring.analyticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.ArimaParameters;
import vla.sai.spring.analyticsservice.dto.HoltWintersParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;
import vla.sai.spring.analyticsservice.service.AnalyticsServicePage;

import javax.script.ScriptException;
import java.io.IOException;


@RestController
@RequestMapping(value = "/analytics-pages")
@RequiredArgsConstructor
public class AnalyticsPagesController {

    private final AnalyticsServicePage analyticsServicePage;

    @PostMapping(path = "/smoothing-graph-page")
    public void smoothing(@RequestBody SmoothingParameters smoothingParameters) throws ScriptException, IOException {
        analyticsServicePage.smoothingGraphPage(smoothingParameters);
    }

    @PostMapping(path = "/holt-winters-graph-page")
    public void holtWinters(@RequestBody HoltWintersParameters holtWintersParameters) throws ScriptException, IOException {
        analyticsServicePage.holtWintersGraphPage(holtWintersParameters);
    }

    @PostMapping(path = "/arima-model-page")
    public void arima(@RequestBody ArimaParameters arimaParameters) throws ScriptException, IOException {
        analyticsServicePage.arimaAnalyticsPage(arimaParameters);
    }

    @PostMapping(path = "/sarima-model-page")
    public void sarima(@RequestBody SmoothingParameters smoothingParameters) throws ScriptException, IOException {
        analyticsServicePage.sarimaAnalyticsPage(smoothingParameters);
    }

    @PostMapping(path = "/acf-pacf-page")
    public void sarima(@RequestBody AcfPacfParameters acfPacfParameters) throws ScriptException, IOException {
        analyticsServicePage.acfPacfPage(acfPacfParameters);
    }

}