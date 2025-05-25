package vla.sai.spring.reportservice.service.Impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;
import vla.sai.spring.reportservice.service.ReportInfoService;

import static org.junit.jupiter.api.Assertions.*;


class PdfReportServiceImplTest {


    @Mock
    ReportInfoService reportInfoService;


    @InjectMocks
    PdfReportServiceImpl pdfReportService;

    public PdfReportServiceImplTest() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSmoothingGraphToPdfDoesNotThrow() {
        SmoothingReportDto dto = new SmoothingReportDto();
        dto.setAuthorName("test");
        dto.setDataFileName("file.csv");
        dto.setSmoothingWindow(5);
        assertDoesNotThrow(() -> pdfReportService.smoothingGraphToPdf(null, dto));
    }

    @Test
    void testDataToPdfReturnsNull() {
        assertNull(pdfReportService.dataToPdf(null));
    }

    @Test
    void testSarimaToPdfReturnsNull() {
        assertNull(pdfReportService.sarimaToPdf(null));
    }

    @Test
    void testHoltWintersGraphToPdfDoesNotThrow() {
        HoltWintersReportDto dto = new HoltWintersReportDto();
        dto.setAuthorName("test");
        dto.setDataFileName("file.csv");
        assertDoesNotThrow(() -> pdfReportService.holtWintersGraphToPdf(null, dto));
    }


    @Test
    void testAcfPacfGraphToPdfDoesNotThrow() {
        AcfPacfReportDto dto = new AcfPacfReportDto();
        dto.setAuthorName("test");
        dto.setDataFileName("file.csv");
        assertDoesNotThrow(() -> pdfReportService.acfPacfGraphToPdf(null, dto));
    }

    @Test
    void testArimaToPdfDoesNotThrow() {
        ArimaReportDto dto = new ArimaReportDto();
        dto.setAuthorName("test");
        dto.setDataFileName("file.csv");
        assertDoesNotThrow(() -> pdfReportService.arimaToPdf(null, dto));
    }

    @Test
    void testTestToPdfReturnsStreamingResponseBody() {
        StreamingResponseBody body = pdfReportService.testToPdf(new MockMultipartFile("photo", new byte[0]), "test");
        assertNotNull(body);
    }

    @Test
    void testDownloadReportThrowsNotFound() {
        ReportIdDto reportIdDto = new ReportIdDto("nonexistent", "missing.pdf");
        assertThrows(Exception.class, () -> pdfReportService.downloadReport(reportIdDto).getClass());
    }
}