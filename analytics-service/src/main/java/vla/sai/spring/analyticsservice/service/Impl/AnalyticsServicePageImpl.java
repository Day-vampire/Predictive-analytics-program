package vla.sai.spring.analyticsservice.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.ArimaParameters;
import vla.sai.spring.analyticsservice.dto.HoltWintersParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;
import vla.sai.spring.analyticsservice.service.AnalyticsServicePage;

import javax.script.ScriptException;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class AnalyticsServicePageImpl implements AnalyticsServicePage {

    @Override
    public void smoothingGraphPage(SmoothingParameters parameters) {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s".formatted(parameters.getAuthorName(),parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/smoothingGraphPage.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getSmoothingWindow())
        );
        processBuilder.redirectErrorStream(true); // Объедененные потоки вывода и ошибок

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("Exit code: {}", exitCode);

            // Чтение вывода
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acfPacfPage(AcfPacfParameters parameters) throws IOException, ScriptException {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s".formatted(parameters.getAuthorName(),parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/acfPacfGraphPage.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticLags()),
                String.valueOf(parameters.getAnalyticColumn())
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("Exit code: {}", exitCode);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
            }

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    log.error(errorLine);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // заменить на подстановочное имя файла и пользователя
    @Override
    public void holtWintersGraphPage(HoltWintersParameters parameters) throws IOException, ScriptException {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s".formatted(parameters.getAuthorName(),parameters.getDataFileName());
        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/holtWintersGraphPage.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getSeasonLength()),
                String.valueOf(parameters.getPeriods())
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("Exit code: {}", exitCode);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line); // Здесь будет JSON со значениями прогноза и параметрами модели
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void arimaAnalyticsPage(ArimaParameters parameters) {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s".formatted(parameters.getAuthorName(),parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/arimaAnalyticsPage.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getPeriods())
        );

        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.error("ARIMA script exited with code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sarimaAnalyticsPage(SmoothingParameters smoothingParameters) {}

}
