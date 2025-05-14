package vla.sai.spring.reportservice.service.Impl;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;
import vla.sai.spring.reportservice.entity.ReportFileExtension;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;
import vla.sai.spring.reportservice.exception.NotFoundException;
import vla.sai.spring.reportservice.repository.ReportInfoRepository;
import vla.sai.spring.reportservice.service.PdfReportService;
import vla.sai.spring.reportservice.service.ReportInfoService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Service
@RequiredArgsConstructor
public class PdfReportServiceImpl implements PdfReportService {

    private final ReportInfoService reportInfoRepository;


    @Override
    public StreamingResponseBody dataToPdf(Object object) {
        return null;
    }

    @Override
    public void smoothingGraphToPdf(MultipartFile photo, SmoothingReportDto reportDto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        document.addTitle(("Smoothing Report"));
        Paragraph headerParagraphOfReport = new Paragraph("Smoothing Report", font);
        headerParagraphOfReport.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagraphOfReport);
        document.add(Chunk.NEWLINE);

        if (photo != null && !photo.isEmpty()) {
            Image image = Image.getInstance(photo.getBytes());
            image.scaleToFit(500, 500);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        } else {
            document.add(new Paragraph("Smoothing Graph not found"));
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Number of analytics column for Smoothing: %s".formatted(String.valueOf(reportDto.getAnalyticColumn()))));
        document.add(new Paragraph("Smoothing Window: %s".formatted(String.valueOf(reportDto.getSmoothingWindow()))));
        document.add(new Paragraph("Data from file: %s".formatted(reportDto.getDataFileName())));
        document.add(new Paragraph("Author of report: %s".formatted(reportDto.getAuthorName())));
        document.add(new Paragraph("Creation report date: %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))));

        document.close();

        savePdfFile(reportDto.getAuthorName(), reportDto.getDataFileName(), ReportType.SMOOTHING_GRAPH_REPORT_PDF, byteArrayOutputStream);
    }

    @Override
    public void holtWintersGraphToPdf(MultipartFile photo, HoltWintersReportDto reportDto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        document.addTitle(("Holt Winters Report"));
        Paragraph headerParagraphOfReport = new Paragraph("Holt Winters Report", font);
        headerParagraphOfReport.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagraphOfReport);
        document.add(Chunk.NEWLINE);

        if (photo != null && !photo.isEmpty()) {
            Image image = Image.getInstance(photo.getBytes());
            image.scaleToFit(500, 500);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        } else {
            document.add(new Paragraph("Graph not found"));
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Analytics parameters of Holt Winters: %s".formatted(reportDto.getParameters())));
        document.add(new Paragraph("Number of analytics column for Holt Winters: %s".formatted(reportDto.getAnalyticColumn())));
        document.add(new Paragraph("Season of Holt Winters: %s".formatted(reportDto.getSeasonLength())));
        document.add(new Paragraph("Periods of Holt Winters: %s".formatted(reportDto.getPeriods())));
        document.add(new Paragraph("Data from file: %s".formatted(reportDto.getDataFileName())));
        document.add(new Paragraph("Author of report: %s".formatted(reportDto.getAuthorName())));
        document.add(new Paragraph("Creation report date: %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))));
        document.close();

        savePdfFile(reportDto.getAuthorName(), reportDto.getDataFileName(), ReportType.HOLT_WINTERS_REPORT_PDF, byteArrayOutputStream);
    }

    @Override
    public void acfPacfGraphToPdf(MultipartFile photo, AcfPacfReportDto reportDto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        document.addTitle(("ACF/PACF Report"));
        Paragraph headerParagraphOfReport = new Paragraph("ACF/PACF Report", font);
        headerParagraphOfReport.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagraphOfReport);
        document.add(Chunk.NEWLINE);

        if (photo != null && !photo.isEmpty()) {
            Image image = Image.getInstance(photo.getBytes());
            image.scaleToFit(500, 500);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        } else {
            document.add(new Paragraph("Graph not found"));
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Number of analytics column for Holt Winters: %s".formatted(String.valueOf(reportDto.getAnalyticColumn()))));
        document.add(new Paragraph("Lags of ACF/PACF: %s".formatted(String.valueOf(reportDto.getAnalyticLags()))));
        document.add(new Paragraph("Data from file: %s".formatted(reportDto.getDataFileName())));
        document.add(new Paragraph("Author of report: %s".formatted(reportDto.getAuthorName())));
        document.add(new Paragraph("Creation report date: %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))));

        document.newPage();
        document.add(new Paragraph("Analytics parameters of ACF/PCF:"));
        document.add(new Paragraph(reportDto.getParameters()));

        document.close();
        savePdfFile(reportDto.getAuthorName(), reportDto.getDataFileName(), ReportType.ACF_PACF_REPORT_PDF, byteArrayOutputStream);
    }

    @Override
    public void arimaToPdf(MultipartFile photo, ArimaReportDto reportDto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        document.addTitle(("Arima Report"));
        Paragraph headerParagraphOfReport = new Paragraph("Arima Report", font);
        headerParagraphOfReport.setAlignment(Element.ALIGN_CENTER);
        document.add(headerParagraphOfReport);
        document.add(Chunk.NEWLINE);

        if (photo != null && !photo.isEmpty()) {
            Image image = Image.getInstance(photo.getBytes());
            image.scaleToFit(500, 500);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        } else {
            document.add(new Paragraph("Arima Graph not found"));
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Number of analytics column for Arima: %s".formatted(String.valueOf(reportDto.getAnalyticColumn()))));
        document.add(new Paragraph("Periods for Arima: %s".formatted(String.valueOf(reportDto.getPeriods()))));
        document.add(new Paragraph("Data from file: %s".formatted(reportDto.getDataFileName())));
        document.add(new Paragraph("Author of report: %s".formatted(reportDto.getAuthorName())));
        document.add(new Paragraph("Creation report date: %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))));

        document.newPage();

        document.add(new Paragraph("Analytics parameters of Arima:"));
        document.add(new Paragraph(reportDto.getParameters()));

        document.close();
        savePdfFile(reportDto.getAuthorName(), reportDto.getDataFileName(), ReportType.ARIMA_REPORT_PDF, byteArrayOutputStream);
    }

    @Override
    public StreamingResponseBody sarimaToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody testToPdf(MultipartFile photo, String authName) {
        return outputStream -> {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Строка с фонт+ Имя автора: %s".formatted(authName), font));
            document.add(new Paragraph("Строка с фонт2", font2));
            document.add(new Paragraph("Строка без фонт"));
            document.add(Chunk.NEWLINE);

            if (photo != null && !photo.isEmpty()) {
                Image image = Image.getInstance(photo.getBytes());
                image.scaleAbsolute(image.getWidth(), image.getHeight());
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            } else document.add(new Paragraph("Фото не было обнаружено"));

            document.close();
            byteArrayOutputStream.writeTo(outputStream);
            outputStream.flush();
        };
    }

    @Override
    public StreamingResponseBody downloadReport(ReportIdDto reportId) {
        Path path = Paths.get("report-service/src/main/resources/Reports/%s/%s".formatted(reportId.getReportAuthorName(), reportId.getReportName()));
        if (!Files.exists(path)) {throw new NotFoundException("отчет %s пользователя %s не найден".formatted(reportId.getReportName(),reportId.getReportAuthorName()));}
        else {return outputStream -> {
                try (InputStream inputStream = Files.newInputStream(path)) {inputStream.transferTo(outputStream);}
            };
        }
    }

    @Override
    public void deleteReport(ReportIdDto reportId) throws IOException {
        Files.delete(Path.of("report-service/src/main/resources/Reports", reportId.getReportAuthorName(),reportId.getReportName()));
    }

    @Override
    public void deleteByAuthor(String authorName) {
        try {
            Files.deleteIfExists(Path.of("report-service/src/main/resources/Reports/%s",authorName));
        } catch (IOException e) {
            throw new IllegalArgumentException("отчеты уже отсутствуют");
        }
    }

    private void savePdfFile(String authorName, String dataFileName, ReportType reportType, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        String directoryPath = "report-service/src/main/resources/Reports/%s".formatted(authorName);
        File dir = new File(directoryPath);
        if (!dir.exists() && !dir.mkdirs())
            log.error("Не удалось создать директорию для сохранения PDF-файлов пользователя: %s".formatted(directoryPath));
        else {
            Path filePath = Paths.get(directoryPath, "%s_%d.pdf".formatted(reportType.getValue(), System.currentTimeMillis()));
            Files.write(filePath, byteArrayOutputStream.toByteArray());
            reportInfoRepository.save(ReportInfo
                    .builder()
                    .reportId(ReportId
                            .builder()
                            .reportAuthorName(authorName)
                            .reportName(filePath.getFileName().toString())
                            .build())
                    .reportDataFileName(dataFileName)
                    .isDeleted(false)
                    .reportCreateTime(LocalDateTime.now())
                    .reportLastModificationTime(LocalDateTime.now())
                    .reportFileSize(BigDecimal.valueOf(Files.size(filePath)))
                    .reportFileExtension(ReportFileExtension.PDF)
                    .reportType(reportType)
                    .build());
        }
    }
}
