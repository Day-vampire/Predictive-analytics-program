package vla.sai.spring.analyticsservice.service.Impl;

import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;
import vla.sai.spring.analyticsservice.service.AnalyticsServicePage;

import javax.script.ScriptException;
import java.io.*;

@Service
public class AnalyticsServicePageImpl implements AnalyticsServicePage {

    // заменить на подстановочное имя файла и пользователя
    @Override
    public void smoothingGraphPage(SmoothingParameters smoothingParameters) {
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/smoothingGraphPage.py";
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv"; // Имя файла заменить на подстановочное
        String secondArgument = "secondArgument"; // Второй аргумент
        ProcessBuilder processBuilder = new ProcessBuilder("py", pythonScriptPath, dataFilePath, secondArgument);
        processBuilder.redirectErrorStream(true); // Объединяем потоки вывода и ошибок

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

            // Чтение вывода
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
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
        String dataFilePath = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv"; // Замените на ваш путь к python.exe

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
            System.out.println("Exit code: " + exitCode);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void holtWintersGraphPage(SmoothingParameters smoothingParameters) throws IOException, ScriptException {
    }

    @Override
    public void arimaAnalyticsPage(SmoothingParameters smoothingParameters) {}
    @Override
    public void sarimaAnalyticsPage(SmoothingParameters smoothingParameters) {}

}
