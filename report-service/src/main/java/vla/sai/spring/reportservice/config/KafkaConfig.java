package vla.sai.spring.reportservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import vla.sai.spring.reportservice.config.deserializer.AcfPacfReportDtoDeserialazer;
import vla.sai.spring.reportservice.config.deserializer.ArimaReportDtoDeserializer;
import vla.sai.spring.reportservice.config.deserializer.HoltWintersReportDtoDeserializer;
import vla.sai.spring.reportservice.config.deserializer.SmoothingReportDtoDeserializer;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.HoltWintersReportDto;
import vla.sai.spring.reportservice.dto.analyticsdto.SmoothingReportDto;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<HoltWintersReportDto, byte[]> consumerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, HoltWintersReportDtoDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<AcfPacfReportDto, byte[]> consumerAcfPacfReportDtoFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, AcfPacfReportDtoDeserialazer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<ArimaReportDto, byte[]> consumerArimaReportDtoFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ArimaReportDtoDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<SmoothingReportDto, byte[]> consumerSmoothingReportDtoFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, SmoothingReportDtoDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<HoltWintersReportDto, byte[]> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<HoltWintersReportDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<AcfPacfReportDto, byte[]> kafkaListenerAcfPacfReportDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<AcfPacfReportDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerAcfPacfReportDtoFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<ArimaReportDto, byte[]> kafkaListenerArimaReportDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<ArimaReportDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerArimaReportDtoFactory());
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<SmoothingReportDto, byte[]> kafkaListenerSmoothingReportDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<SmoothingReportDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerSmoothingReportDtoFactory());
        return factory;
    }

}