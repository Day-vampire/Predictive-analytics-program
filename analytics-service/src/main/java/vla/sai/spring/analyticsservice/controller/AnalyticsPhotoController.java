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
import vla.sai.spring.analyticsservice.service.AnalyticsServicePhoto;

import javax.script.ScriptException;
import java.io.IOException;


@RestController
@RequestMapping(value = "/analytics-photo")
@RequiredArgsConstructor
public class AnalyticsPhotoController {

    private final AnalyticsServicePhoto analyticsServicePhoto;

    @PostMapping(path = "/smoothing-graph-photo")
    public void smoothing(@RequestBody SmoothingParameters smoothingParameters) throws ScriptException, IOException {
        analyticsServicePhoto.smoothingGraphPhoto(smoothingParameters);
    }


    @PostMapping(path = "/holt-winters-graph-photo")
    public void holtWintersPhoto(@RequestBody HoltWintersParameters holtWintersParameters) throws ScriptException, IOException, InterruptedException {
        analyticsServicePhoto.holtWintersGraphPhoto(holtWintersParameters);
    }

    @PostMapping(path = "/arima-analyrics-photo")
    public void arimaPhoto(@RequestBody ArimaParameters arimaParameters) throws ScriptException, IOException {
        analyticsServicePhoto.arimaAnalyticsPhoto(arimaParameters);
    }

//    @PostMapping(path = "/sarima-model-page")
//    public void sarima(SmoothingParameters smoothingParameters) throws ScriptException, IOException {
//        analyticsServicePage.sarimaAnalyticsPage(smoothingParameters);
//    }
//
    @PostMapping(path = "/acf-pacf-graph-photo")
    public void acfPacfPhoto(@RequestBody AcfPacfParameters acfPacfParameters) throws ScriptException, IOException {
        analyticsServicePhoto.acfPacfPhoto(acfPacfParameters);
    }

}