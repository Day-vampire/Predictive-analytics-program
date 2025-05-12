package vla.sai.spring.reportservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.reportservice.dto.HoltWintersReportDto;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final PdfReportService pdfReportService;

    @KafkaListener(topics = "holt_winters_pdf_report_topic", groupId = "report_consumers")
    public void listenHoltWintersPdfReportData(ConsumerRecord<HoltWintersReportDto, byte[]> record) throws IOException {
        HoltWintersReportDto holtWintersReportDto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile image = new MockMultipartFile(
                "image",                  // имя поля
                "image.png",             // имя файла
                "image/png",            // MIME-тип
                fileContent               // сами байты
        );
        System.out.println("вызов holtWintersGraphToPdf");
        pdfReportService.holtWintersGraphToPdf(image,holtWintersReportDto);
        System.out.println("конец вызов holtWintersGraphToPdf");
    }

}