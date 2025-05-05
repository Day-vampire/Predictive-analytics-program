package vla.sai.spring.reportservice.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public interface PdfReportService {
    StreamingResponseBody dataToPdf(Object object);
    StreamingResponseBody smoothingGraphToPdf(Object object);
    StreamingResponseBody holtWintersGraphToPdf(Object object);
    StreamingResponseBody arimaToPdf(Object object);
    StreamingResponseBody sarimaToPdf(Object object);
    StreamingResponseBody testToPdf(MultipartFile photo, String authName) throws IOException;
}
