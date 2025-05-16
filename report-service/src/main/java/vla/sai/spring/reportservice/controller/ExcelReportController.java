package vla.sai.spring.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.datadto.FileInfoDto;
import vla.sai.spring.reportservice.service.ExcelReportService;
import vla.sai.spring.reportservice.service.feign.FileServiceClient;


@RestController
@RequestMapping(value = "/report/excel",  produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExcelReportController {

    private final ExcelReportService excelReportService;
    private final FileServiceClient fileServiceClient;

    @PostMapping(path = "/data")
    public <T> ResponseEntity<StreamingResponseBody> exportDataToExcel(@RequestBody T dto, Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Data-report.xlsx");
        StreamingResponseBody stream =null;
        if(FileInfoDto.class.isAssignableFrom(dto.getClass())){
            stream = excelReportService.dataToExcel(pageable,(FileInfoDto) dto, fileServiceClient::getAllFiles);
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/smoothingGraph")
    public ResponseEntity<StreamingResponseBody> exportSmoothingGraphToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Smoothing-Graph-report.xlsx");
        StreamingResponseBody stream = excelReportService.smoothingGraphToExcel(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/holtWintersGraph")
    public ResponseEntity<StreamingResponseBody> exportHoltWintersGraphToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Holt-Winters-Graph-report.xlsx");
        StreamingResponseBody stream = excelReportService.holtWintersGraphToExcel(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }

    @PostMapping(path = "/arima")
    public ResponseEntity<StreamingResponseBody> exportArimaToExcel(@RequestBody Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Arima-report.xlsx");
        StreamingResponseBody stream = excelReportService.arimaToExcel(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(stream);
    }
}