package vla.sai.spring.reportservice.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;

import java.io.IOException;


public interface PdfReportService {

    StreamingResponseBody dataToPdf(Object object);

    void smoothingGraphToPdf(MultipartFile photo, SmoothingReportDto smoothingReportDto) throws IOException;
    void holtWintersGraphToPdf(MultipartFile photo, HoltWintersReportDto holtWintersReportDto) throws IOException;
    void acfPacfGraphToPdf(MultipartFile photo, AcfPacfReportDto acfPacfReportDto) throws IOException;
    void arimaToPdf(MultipartFile photo, ArimaReportDto arimaReportDto) throws IOException;

    StreamingResponseBody sarimaToPdf(Object object);
    StreamingResponseBody testToPdf(MultipartFile photo, String authName) throws IOException;

}
