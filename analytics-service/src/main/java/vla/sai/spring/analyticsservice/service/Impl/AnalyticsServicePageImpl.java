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

    // заменить на подстановочное имя файла и пользователя
    @Override
    public void smoothingGraphPage(SmoothingParameters smoothingParameters) {
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/smoothingGraphPage.py";
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv"; // Имя файла заменить на подстановочное
        String secondArgument = "secondArgument"; // Второй тестовый аргумент
        ProcessBuilder processBuilder = new ProcessBuilder("py", pythonScriptPath, dataFilePath, secondArgument);
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

    // заменить на подстановочное имя файла и пользователя
    @Override
    public void acfPacfPage(AcfPacfParameters parameters) throws IOException, ScriptException {
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/acfPacfGraphPage.py";
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                pythonScriptPath,
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
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/holtWintersGraph.py";
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv";
        parameters.setNPreds(10);
        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                pythonScriptPath,
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getSeasonLength()),
                String.valueOf(parameters.getNPreds())
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
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/arimaAnalytics.py";
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                pythonScriptPath,
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getNPreds())
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
