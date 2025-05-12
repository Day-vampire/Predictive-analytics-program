package vla.sai.spring.reportservice.service.Impl;

import com.lowagie.text.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.HoltWintersReportDto;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class PdfReportServiceImpl implements PdfReportService {
    @Override
    public StreamingResponseBody dataToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody smoothingGraphToPdf(Object object) {
        return null;
    }

    @Override
    public void holtWintersGraphToPdf(MultipartFile photo, HoltWintersReportDto holtWintersReportDto) throws IOException {
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
        document.add(new Paragraph("Analytics parameters of Holt Winters: %s".formatted(holtWintersReportDto.getParameters())));
        document.add(new Paragraph("Number of analytics column for Holt Winters: %s".formatted(holtWintersReportDto.getAnalyticColumn())));
        document.add(new Paragraph("Season of Holt Winters: %s".formatted(holtWintersReportDto.getSeasonLength())));
        document.add(new Paragraph("Periods of Holt Winters: %s".formatted(holtWintersReportDto.getPeriods())));
        document.add(new Paragraph("Data from file: %s".formatted(holtWintersReportDto.getDataFileName())));
        document.add(new Paragraph("Author of report: %s".formatted(holtWintersReportDto.getAuthorName())));
        document.add(new Paragraph("Creation report date: %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))));
        document.close();

        String directoryPath = "report-service/src/main/resources/Reports/%s".formatted(holtWintersReportDto.getAuthorName());
        File dir = new File(directoryPath);
        if (!dir.exists() && !dir.mkdirs()) System.err.println("Не удалось создать директорию для сохранения PDF-файлов пользователя: " + directoryPath);
        else {
        Path filePath = Paths.get(directoryPath,"holt_winters_report_%d.pdf".formatted(System.currentTimeMillis()));
        Files.write(filePath, byteArrayOutputStream.toByteArray());
        }
    }

    @Override
    public StreamingResponseBody arimaToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody sarimaToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody testToPdf(MultipartFile photo, String authName) throws IOException {
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

    private void savePdfFileOnDisk(ByteArrayOutputStream outputStream) {
    }
}
