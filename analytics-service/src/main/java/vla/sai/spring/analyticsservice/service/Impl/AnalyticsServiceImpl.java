package vla.sai.spring.analyticsservice.service.Impl;

import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.dto.AnalyticsDto;
import vla.sai.spring.analyticsservice.service.AnalyticsService;

import java.io.*;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    public void smoothingGraph(AnalyticsDto analyticsDto) {
        String pythonScriptPath = "G:/Projects/Predictive-analytics-program/analytics-service/python_programs/smoothingGraph.py";
        String dataFileName = "G:/Projects/Predictive-analytics-program/FilesAnalas/financial_assert_story/MaksZOV/currency_daily_BTC_EUR.csv"; // Имя файла заменить на подстановочное
        String secondArgument = "secondArgument"; // Второй аргумент
        ProcessBuilder processBuilder = new ProcessBuilder("py", pythonScriptPath, dataFileName, secondArgument);
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
}
