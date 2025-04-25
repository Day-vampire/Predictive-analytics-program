package vla.sai.spring.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.service.ExcelReportService;
import vla.sai.spring.reportservice.service.PdfReportService;


@RestController
@RequestMapping(value = "/report/pdf")
@RequiredArgsConstructor
public class PdfReportController {

    private final PdfReportService pdfReportService;

    @PostMapping(path = "/data")
    public ResponseEntity<StreamingResponseBody> exportDataToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Data-report.pdf");
        StreamingResponseBody stream = pdfReportService.dataToPdf(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/smoothingGraph")
    public ResponseEntity<StreamingResponseBody> exportSmoothingGraphToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Smoothing-Graph-report.pdf");
        StreamingResponseBody stream = pdfReportService.smoothingGraphToPdf(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/holtWintersGraph")
    public ResponseEntity<StreamingResponseBody> exportHoltWintersGraphToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Holt-Winters-Graph-report.pdf");
        StreamingResponseBody stream = pdfReportService.holtWintersGraphToPdf(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/arima")
    public ResponseEntity<StreamingResponseBody> exportArimaToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Arima-report.pdf");
        StreamingResponseBody stream = pdfReportService.arimaToPdf(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }
}