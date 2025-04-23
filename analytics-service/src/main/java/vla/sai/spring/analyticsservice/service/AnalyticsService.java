package vla.sai.spring.analyticsservice.service;

import javax.script.ScriptException;
import java.io.IOException;

public interface AnalyticsService {
    public void smoothingGraph(String fileName) throws IOException, ScriptException;
}
