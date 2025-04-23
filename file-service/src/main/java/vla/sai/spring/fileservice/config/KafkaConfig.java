package vla.sai.spring.fileservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic fileDeleteTopic() {
        return new NewTopic("file_delete_topic", 1, (short) 1);
    }

    @Bean
    public NewTopic fileCreateTopic() {
        return new NewTopic("file_create_topic", 1, (short) 1);
    }
}