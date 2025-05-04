package vla.sai.spring.analyticsservice.service;

import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;

import javax.script.ScriptException;
import java.io.IOException;

public interface AnalyticsServicePage {
    void smoothingGraphPage(SmoothingParameters smoothingParameters) throws IOException, ScriptException;
    void acfPacfPage(AcfPacfParameters acfPacfParameters) throws IOException, ScriptException;
    void holtWintersGraphPage(SmoothingParameters smoothingParameters) throws IOException, ScriptException;
    void arimaAnalyticsPage(SmoothingParameters smoothingParameters);
    void sarimaAnalyticsPage(SmoothingParameters smoothingParameters);

}
