package vla.sai.spring.analyticsservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import vla.sai.spring.analyticsservice.config.deserializer.FileDataDtoDeserializer;
import vla.sai.spring.analyticsservice.config.serializer.AcfPacfReportDtoSerializer;
import vla.sai.spring.analyticsservice.config.serializer.HoltWintersReportDtoSerializer;
import vla.sai.spring.analyticsservice.dto.AcfPacfReportDto;
import vla.sai.spring.analyticsservice.dto.FileDataDto;
import vla.sai.spring.analyticsservice.dto.HoltWintersReportDto;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    // Consumer part
    @Bean
    public ConsumerFactory<FileDataDto, byte[]> consumerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, FileDataDtoDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<FileDataDto, byte[]> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<FileDataDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


    // Producer part
    @Bean
    public ProducerFactory<HoltWintersReportDto, byte[]> holtWintersPhotoReportProducerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, HoltWintersReportDtoSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<AcfPacfReportDto, byte[]> acfPacfPhotoReportProducerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, AcfPacfReportDtoSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }



    @Bean
    public KafkaTemplate<HoltWintersReportDto, byte[]> kafkaTemplateHoltWintersPhotoReport() {
        return new KafkaTemplate<>(holtWintersPhotoReportProducerFactory());
    }

    @Bean
    public KafkaTemplate<AcfPacfReportDto, byte[]> kafkaTemplateAcfPacfPhotoReport() {
        return new KafkaTemplate<>(acfPacfPhotoReportProducerFactory());
    }



    @Bean
    public NewTopic holtWintersPdfReportTopic() {
        return new NewTopic("holt_winters_pdf_report_topic", 1, (short) 1);
    }

    @Bean
    public NewTopic holtWintersExcelReportTopic() { return new NewTopic("holt_winters_excel_report_topic", 1, (short) 1);}

    @Bean
    public NewTopic AcfPacfPdfReportTopic() {
        return new NewTopic("acf_pacf_pdf_report_topic", 1, (short) 1);
    }

    @Bean
    public NewTopic AcfPacfExcelReportTopic() {
        return new NewTopic("acf_pacf_excel_report_topic", 1, (short) 1);
    }

}