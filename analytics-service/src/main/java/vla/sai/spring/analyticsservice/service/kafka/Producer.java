package vla.sai.spring.analyticsservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vla.sai.spring.analyticsservice.dto.AcfPacfReportDto;
import vla.sai.spring.analyticsservice.dto.HoltWintersReportDto;

@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<HoltWintersReportDto, byte[]> kafkaTemplateHoltWintersPhotoReport;
    private final KafkaTemplate<AcfPacfReportDto, byte[]> kafkaTemplateAcfPacfPhotoReport;

    public void sendHoltWintersPdfReportData(byte[] file, HoltWintersReportDto data) {
        ProducerRecord<HoltWintersReportDto, byte[]> producerRecord = new ProducerRecord<>("holt_winters_pdf_report_topic", data,file);
        kafkaTemplateHoltWintersPhotoReport.send(producerRecord);
    }
    public void sendHoltWintersExcelReportData(byte[] file, HoltWintersReportDto data) {
        ProducerRecord<HoltWintersReportDto, byte[]> producerRecord = new ProducerRecord<>("holt_winters_excel_report_topic", data,file);
        kafkaTemplateHoltWintersPhotoReport.send(producerRecord);
    }
    
    public void sendAcfPacfPdfReportData(byte[] file, AcfPacfReportDto data) {
        ProducerRecord<AcfPacfReportDto, byte[]> producerRecord = new ProducerRecord<>("acf_pacf_pdf_report_topic", data,file);
        kafkaTemplateAcfPacfPhotoReport.send(producerRecord);
    }
    public void sendAcfPacfExcelReportData(byte[] file, AcfPacfReportDto data) {
        ProducerRecord<AcfPacfReportDto, byte[]> producerRecord = new ProducerRecord<>("acf_pacf_excel_report_topic", data,file);
        kafkaTemplateAcfPacfPhotoReport.send(producerRecord);
    }

}