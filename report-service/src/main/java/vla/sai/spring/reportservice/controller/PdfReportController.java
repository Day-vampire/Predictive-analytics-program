package vla.sai.spring.reportservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.IOException;


@RestController
@RequestMapping(value = "/report/pdf")
@RequiredArgsConstructor
public class PdfReportController {

    private final PdfReportService pdfReportService;

    @PostMapping(path = "/data")
    public ResponseEntity<StreamingResponseBody> exportDataToPdf(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Data-report.pdf");
        StreamingResponseBody stream = pdfReportService.dataToPdf(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/smoothingGraph", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportSmoothingGraphToPdf(@RequestPart("file") MultipartFile file,
                                          @RequestParam("dataFileName") String dataFileName,
                                          @RequestParam("authorName") String authorName,
                                          @RequestParam("analyticColumn") int analyticColumn,
                                          @RequestParam("smoothingWindow") int smoothingWindow,
                                          @RequestParam("parameters") String parameters) throws IOException {
        pdfReportService.smoothingGraphToPdf(file, SmoothingReportDto
                .builder()
                .dataFileName(dataFileName)
                .authorName(authorName)
                .analyticColumn(analyticColumn)
                .smoothingWindow(smoothingWindow)
                .parameters(parameters)
                .build());
    }

    @PostMapping(path = "/arima", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportArimaToPdf(@RequestPart("file") MultipartFile file,
                                 @RequestParam("dataFileName") String dataFileName,
                                 @RequestParam("authorName") String authorName,
                                 @RequestParam("analyticColumn") int analyticColumn,
                                 @RequestParam("periods") int periods,
                                 @RequestParam("parameters") String parameters) throws IOException {
        pdfReportService.arimaToPdf(file, ArimaReportDto
                .builder()
                .dataFileName(dataFileName)
                .authorName(authorName)
                .analyticColumn(analyticColumn)
                .periods(periods)
                .parameters(parameters)
                .build());
    }

    @PostMapping(path = "/holtWintersGraph", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportHoltWintersGraphToPdf(@RequestPart("file") MultipartFile file,
                                            @RequestParam("dataFileName") String dataFileName,
                                            @RequestParam("authorName") String authorName,
                                            @RequestParam("analyticColumn") int analyticColumn,
                                            @RequestParam("seasonLength") int seasonLength,
                                            @RequestParam("periods") int periods,
                                            @RequestParam("parameters") String parameters) throws IOException {
        pdfReportService.holtWintersGraphToPdf(file, HoltWintersReportDto
                .builder()
                .dataFileName(dataFileName)
                .authorName(authorName)
                .analyticColumn(analyticColumn)
                .seasonLength(seasonLength)
                .periods(periods)
                .parameters(parameters)
                .build());
    }

    @PostMapping(path = "/acfPacfGraph", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportAcfPacfGraphToPdf(@RequestPart("file") MultipartFile file,
                                        @RequestParam("dataFileName") String dataFileName,
                                        @RequestParam("authorName") String authorName,
                                        @RequestParam("analyticColumn") int analyticColumn,
                                        @RequestParam("analyticLags") int analyticLags,
                                        @RequestParam("parameters") String parameters) throws IOException {
        pdfReportService.acfPacfGraphToPdf(file, AcfPacfReportDto.builder()
                .dataFileName(dataFileName)
                .authorName(authorName)
                .analyticColumn(analyticColumn)
                .analyticLags(analyticLags)
                .parameters(parameters)
                .build());
    }

    @PostMapping(path = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StreamingResponseBody> exportTestToPdf(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "authName") String name) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test-report.pdf");
        StreamingResponseBody stream = pdfReportService.testToPdf(file, name);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/download-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StreamingResponseBody> exportTestToPdf(ReportIdDto reportId)  {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s".formatted(reportId.getReportName()));
        org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody stream = pdfReportService.downloadReport(reportId);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
}

    @PostMapping(path = "/delete-report")
    @Operation(summary = "Удаление файла пользователя из дериктории", description = "Удаляет файл из директории")
    public void deleteReport(ReportIdDto reportIdDto) throws IOException {
      pdfReportService.deleteReport(reportIdDto);
    }

    @PostMapping(path = "/delete-all-author-reports")
    @Operation(summary = "Удаление всех отчетов пользователя из дериктории", description = "Удаляет отчеты из директории")
    public void deleteAllAuthorReports(String authorName)  {
        pdfReportService.deleteByAuthor(authorName);
    }

}