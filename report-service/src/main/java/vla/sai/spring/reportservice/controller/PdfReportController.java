package vla.sai.spring.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.IOException;


@RestController
@RequestMapping(value = "/report/pdf") //,  produces = MediaType.APPLICATION_JSON_VALUE)
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
    public void exportSmoothingGraphToPdf(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestBody SmoothingReportDto reportDto) throws IOException {
        pdfReportService.smoothingGraphToPdf(file, reportDto);

    }

    @PostMapping(path = "/arima", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportArimaToPdf(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestBody ArimaReportDto reportDto) throws IOException {
        pdfReportService.arimaToPdf(file, reportDto);
    }

    @PostMapping(path = "/holtWintersGraph", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void exportHoltWintersGraphToPdf(@RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestBody HoltWintersReportDto reportDto) throws IOException {
        pdfReportService.holtWintersGraphToPdf(file, reportDto);
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
}