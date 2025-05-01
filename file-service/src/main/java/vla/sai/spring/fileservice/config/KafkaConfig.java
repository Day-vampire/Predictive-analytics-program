package vla.sai.spring.fileservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import vla.sai.spring.fileservice.dto.FileDataDto;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<FileDataDto, byte[]> createFileProducerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, FileDataDtoSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, FileDataDto> deleteFileProducerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, FileDataDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<FileDataDto, byte[]> kafkaTemplateCreateFile() {
        return new KafkaTemplate<>(createFileProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, FileDataDto> kafkaTemplateDeleteFile() {
        return new KafkaTemplate<>(deleteFileProducerFactory());
    }

    @Bean
    public NewTopic fileDeleteTopic() {
        return new NewTopic("file_delete_topic", 1, (short) 1);
    }

    @Bean
    public NewTopic fileCreateTopic() {
        return new NewTopic("file_create_topic", 1, (short) 1);
    }


}