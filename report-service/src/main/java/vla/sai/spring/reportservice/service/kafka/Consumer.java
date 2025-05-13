package vla.sai.spring.reportservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.reportservice.dto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.SmoothingReportDto;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final PdfReportService pdfReportService;

    @KafkaListener(
            topics = "holt_winters_pdf_report_topic",
            groupId = "report_consumers",
            containerFactory ="kafkaListenerContainerFactory"
    )
    public void listenHoltWintersPdfReportData(ConsumerRecord<HoltWintersReportDto, byte[]> record) throws IOException {
        HoltWintersReportDto reportDto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile image = new MockMultipartFile(
                "image",                  // имя поля
                "image.png",             // имя файла
                "image/png",            // MIME-тип
                fileContent               // сами байты
        );
        pdfReportService.holtWintersGraphToPdf(image,reportDto);
    }

    @KafkaListener(
            topics = "acf_pacf_pdf_report_topic",
            groupId = "report_consumers",
            containerFactory = "kafkaListenerAcfPacfReportDtoContainerFactory"
    )
    public void listenAcfPacfReportData(ConsumerRecord<AcfPacfReportDto, byte[]> record) throws IOException {
        AcfPacfReportDto reportDto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile image = new MockMultipartFile(
                "image",                  // имя поля
                "image.png",             // имя файла
                "image/png",            // MIME-тип
                fileContent               // сами байты
        );
        pdfReportService.acfPacfGraphToPdf(image,reportDto);
    }

    @KafkaListener(
            topics = "arima_pdf_report_topic",
            groupId = "report_consumers",
            containerFactory = "kafkaListenerArimaReportDtoContainerFactory"
    )
    public void listenArimaPdfReportData(ConsumerRecord<ArimaReportDto, byte[]> record) throws IOException {
        ArimaReportDto reportDto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile image = new MockMultipartFile(
                "image",                  // имя поля
                "image.png",             // имя файла
                "image/png",            // MIME-тип
                fileContent               // сами байты
        );
        pdfReportService.arimaToPdf(image,reportDto);
    }

    @KafkaListener(
            topics = "smoothing_pdf_report_topic",
            groupId = "report_consumers",
            containerFactory = "kafkaListenerSmoothingReportDtoContainerFactory"
    )
    public void listenSmoothingPdfReportData(ConsumerRecord<SmoothingReportDto, byte[]> record) throws IOException {
        SmoothingReportDto reportDto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile image = new MockMultipartFile(
                "image",                  // имя поля
                "image.png",             // имя файла
                "image/png",            // MIME-тип
                fileContent               // сами байты
        );
        pdfReportService.smoothingGraphToPdf(image,reportDto);
    }



}