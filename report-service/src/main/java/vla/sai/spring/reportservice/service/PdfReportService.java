package vla.sai.spring.reportservice.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


public interface PdfReportService {
    StreamingResponseBody dataToPdf(Object object);
    StreamingResponseBody smoothingGraphToPdf(Object object);
    StreamingResponseBody holtWintersGraphToPdf(Object object);
    StreamingResponseBody arimaToPdf(Object object);
}
