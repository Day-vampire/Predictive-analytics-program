package vla.sai.spring.reportservice.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.FilterParameters;


public interface ExcelReportService {
    StreamingResponseBody dataToExcel(FilterParameters filterParameters);
    StreamingResponseBody smoothingGraphToExcel(Object object);
    StreamingResponseBody holtWintersGraphToExcel(Object object);
    StreamingResponseBody arimaToExcel(Object object);
    StreamingResponseBody sarimaToExcel(Object object);
}
