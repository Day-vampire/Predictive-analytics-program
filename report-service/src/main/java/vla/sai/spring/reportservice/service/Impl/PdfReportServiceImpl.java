package vla.sai.spring.reportservice.service.Impl;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


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
    public StreamingResponseBody holtWintersGraphToPdf(Object object) {
        return null;
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
            }
            else document.add(new Paragraph("Фото не было обнаружено"));

            document.close();
            byteArrayOutputStream.writeTo(outputStream);
            outputStream.flush();
        };
    }

    private void savePdfFileOnDisk(ByteArrayOutputStream outputStream) {
    }
}
