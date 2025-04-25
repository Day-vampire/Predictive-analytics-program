package vla.sai.spring.reportservice.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


public interface ExcelReportService {
    StreamingResponseBody dataToExcel(Object object);
    StreamingResponseBody smoothingGraphToExcel(Object object);
    StreamingResponseBody holtWintersGraphToExcel(Object object);
}
