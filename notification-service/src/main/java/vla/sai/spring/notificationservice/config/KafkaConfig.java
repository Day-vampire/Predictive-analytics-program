package vla.sai.spring.notificationservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import vla.sai.spring.notificationservice.config.deserializer.NotificationDtoDeserialazer;
import vla.sai.spring.notificationservice.dto.NotificationDto;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<NotificationDto, byte[]> consumerFileNotificationFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, NotificationDtoDeserialazer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String,NotificationDto> consumerNotificationFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, NotificationDtoDeserialazer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<NotificationDto, byte[]> kafkaListenerFileNotificationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<NotificationDto, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFileNotificationFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,NotificationDto> kafkaListenerNotificationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String,NotificationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerNotificationFactory());
        return factory;
    }



}