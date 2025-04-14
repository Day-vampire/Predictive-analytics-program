package vla.sai.spring.analyticsservice.service.Impl;

import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.service.AnalyticsService;

import javax.script.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public void smoothingGraph(String fileName) {
        String pythonScriptPath = "analytics-service/anal/main.py";
        StringWriter writer = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("python");

        context.setAttribute("fileName", fileName, ScriptContext.ENGINE_SCOPE);
        context.setAttribute("secondArgument", "secondArgument", ScriptContext.ENGINE_SCOPE);

        try (FileReader fileReader = new FileReader(pythonScriptPath)) {
            Object eval = engine.eval(fileReader, context);
            System.out.println(writer.toString().trim());
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
    }
}
