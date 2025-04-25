package vla.sai.spring.reportservice.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.FilterParameters;


public interface ReportService {
    StreamingResponseBody toExcel(FilterParameters filterParameters);
}
