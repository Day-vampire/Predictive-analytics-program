package vla.sai.spring.analyticsservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.dto.AcfPacfParameters;
import vla.sai.spring.analyticsservice.dto.AcfPacfReportDto;
import vla.sai.spring.analyticsservice.dto.ArimaParameters;
import vla.sai.spring.analyticsservice.dto.ArimaReportDto;
import vla.sai.spring.analyticsservice.dto.HoltWintersParameters;
import vla.sai.spring.analyticsservice.dto.HoltWintersReportDto;
import vla.sai.spring.analyticsservice.dto.SmoothingParameters;
import vla.sai.spring.analyticsservice.dto.SmoothingReportDto;
import vla.sai.spring.analyticsservice.service.AnalyticsServicePhoto;
import vla.sai.spring.analyticsservice.service.kafka.Producer;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsServicePhotoImpl implements AnalyticsServicePhoto {

    private final Producer producer;

    @Override
    public void smoothingGraphPhoto(SmoothingParameters parameters) throws IOException, ScriptException {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s"
                .formatted(parameters.getAuthorName(), parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/smoothingGraphPhoto.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getSmoothingWindow())
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            StringBuilder base64Builder = new StringBuilder();
            boolean base64Started = false;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("BASE64_IMAGE_START")) {
                        base64Started = true;
                    } else if (base64Started) {
                        base64Builder.append(line);
                    } else {
                        log.info(line);
                    }
                }
            }

            int exitCode = process.waitFor();
            log.info("Python завершился с кодом: {}", exitCode);

            if (!base64Builder.isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(base64Builder.toString());
                producer.sendSmoothingPdfReportData(imageBytes, SmoothingReportDto
                        .builder()
                        .authorName(parameters.getAuthorName())
                        .dataFileName(parameters.getDataFileName())
                        .smoothingWindow(parameters.getSmoothingWindow())
                        .analyticColumn(parameters.getAnalyticColumn())
                        .build());
                log.info("График сглаживания отправлен");
            } else {
                log.warn("Base64 график сглаживания не получен.");
            }

        } catch (IOException | InterruptedException e) {
            log.error("Ошибка выполнения smoothingGraphPage", e);
        }
    }

    @Override
    public void acfPacfPhoto(AcfPacfParameters parameters) throws IOException, ScriptException {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s"
                .formatted(parameters.getAuthorName(), parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/acfPacfGraphPhoto.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticLags()),
                String.valueOf(parameters.getAnalyticColumn())
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            StringBuilder paramsBuilder = new StringBuilder();
            StringBuilder base64Builder = new StringBuilder();
            boolean base64Started = false;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("BASE64_IMAGE_START")) {
                        base64Started = true;
                    } else if (base64Started) {
                        base64Builder.append(line);
                    } else {
                        paramsBuilder.append(line).append(System.lineSeparator());
                    }
                }
            }

            int exitCode = process.waitFor();
            log.info("Python завершился с кодом: {}", exitCode);
            String paramsString = paramsBuilder.toString().trim();

            if (!base64Builder.isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(base64Builder.toString());
                producer.sendAcfPacfPdfReportData(imageBytes, AcfPacfReportDto
                        .builder()
                        .dataFileName(parameters.getDataFileName())
                        .authorName(parameters.getAuthorName())
                        .analyticColumn(parameters.getAnalyticColumn())
                        .analyticLags(parameters.getAnalyticLags())
                        .parameters(paramsString)
                        .build());
                log.info("Изображение и параметры отправлены: ");
            } else {
                log.warn("Base64 изображение не было получено.");
            }

        } catch (IOException | InterruptedException e) {
            log.error("Ошибка выполнения Python-скрипта", e);
        }
    }

    @Override
    public void holtWintersGraphPhoto(HoltWintersParameters parameters) throws IOException, ScriptException, InterruptedException {
        String pythonScriptPath = "analytics-service/python_programs/holtWintersGraphPhoto.py";
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s".formatted(parameters.getAuthorName(), parameters.getDataFileName());
        ProcessBuilder processBuilder = new ProcessBuilder(
                "py", pythonScriptPath, dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getSeasonLength()),
                String.valueOf(parameters.getPeriods())
        );
        processBuilder.redirectErrorStream(true);
        String paramsString = null;
        String base64Image = null;

        try {
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                List<String> lines = reader.lines().toList();
                if (lines.size() >= 2) {
                    paramsString = lines.get(0);
                    base64Image = lines.get(1);
                }
            }
            int exitCode = process.waitFor();
            log.info("Python exit code: {}", exitCode);

            if (paramsString != null && base64Image != null) {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                producer.sendHoltWintersPdfReportData(imageBytes, HoltWintersReportDto
                        .builder()
                        .dataFileName(parameters.getDataFileName())
                        .authorName(parameters.getAuthorName())
                        .analyticColumn(parameters.getAnalyticColumn())
                        .seasonLength(parameters.getSeasonLength())
                        .periods(parameters.getPeriods())
                        .parameters(paramsString)
                        .build());
                log.info("Holt-Winters параметры: %s и фото отправлены", paramsString);
            } else throw new FileNotFoundException("Holt-Winters фото пусто");

        } catch (IOException e) {
            log.error("Ошибка при выполнении Python скрипта", e);
        } catch (InterruptedException e) {
            log.error("Ошибка при выполнении Python скрипта", e);
        }
    }

    @Override
    public void arimaAnalyticsPhoto(ArimaParameters parameters) {
        String dataFilePath = "analytics-service/src/main/resources/Files/financial_assert_story/%s/%s"
                .formatted(parameters.getAuthorName(), parameters.getDataFileName());

        ProcessBuilder processBuilder = new ProcessBuilder(
                "py",
                "analytics-service/python_programs/arimaAnalyticsPhoto.py",
                dataFilePath,
                String.valueOf(parameters.getAnalyticColumn()),
                String.valueOf(parameters.getPeriods())
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            StringBuilder paramsBuilder = new StringBuilder();
            StringBuilder base64Builder = new StringBuilder();
            boolean base64Started = false;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("BASE64_IMAGE_START")) {
                        base64Started = true;
                    } else if (base64Started) {
                        base64Builder.append(line);
                    } else {
                        paramsBuilder.append(line).append(System.lineSeparator());
                    }
                }
            }

            int exitCode = process.waitFor();
            log.info("Python завершился с кодом: {}", exitCode);
            String paramsOutput = paramsBuilder.toString().trim();


            if (!base64Builder.isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(base64Builder.toString());
                producer.sendArimaPdfReportData(imageBytes, ArimaReportDto
                        .builder()
                        .authorName(parameters.getAuthorName())
                        .dataFileName(parameters.getDataFileName())
                        .analyticColumn(parameters.getAnalyticColumn())
                        .periods(parameters.getPeriods())
                        .parameters(paramsOutput)
                        .build());
                log.info("ARIMA изображение и параметры отправлены");
            } else {
                log.warn("Base64 ARIMA изображение не получено.");
            }

        } catch (IOException | InterruptedException e) {
            log.error("Ошибка выполнения ARIMA-скрипта", e);
        }
    }

    @Override
    public void sarimaAnalyticsPhoto(SmoothingParameters smoothingParameters) {

    }
}
