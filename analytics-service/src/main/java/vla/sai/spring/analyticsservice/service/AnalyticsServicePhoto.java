package vla.sai.spring.analyticsservice.service;

import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.ArimaParameters;
import vla.sai.spring.analyticsservice.dto.HoltWintersParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;

import javax.script.ScriptException;
import java.io.IOException;

public interface AnalyticsServicePhoto {
    void smoothingGraphPhoto(SmoothingParameters smoothingParameters) throws IOException, ScriptException;
    void acfPacfPhoto(AcfPacfParameters acfPacfParameters) throws IOException, ScriptException;
    void holtWintersGraphPhoto(HoltWintersParameters holtWintersParameters) throws IOException, ScriptException, InterruptedException;
    void arimaAnalyticsPhoto(ArimaParameters arimaParameters);
    void sarimaAnalyticsPhoto(SmoothingParameters smoothingParameters);

}
