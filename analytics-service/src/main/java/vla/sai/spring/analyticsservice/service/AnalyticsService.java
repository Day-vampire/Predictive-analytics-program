package vla.sai.spring.analyticsservice.service;

import vla.sai.spring.analyticsservice.dto.AnalyticsDto;

import javax.script.ScriptException;
import java.io.IOException;

public interface AnalyticsService {
    void smoothingGraph(AnalyticsDto analyticsDto) throws IOException, ScriptException;
}
