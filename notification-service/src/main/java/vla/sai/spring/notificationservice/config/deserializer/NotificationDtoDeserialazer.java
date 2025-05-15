package vla.sai.spring.notificationservice.config.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.exception.DeserializeException;

import java.util.Map;

public class NotificationDtoDeserialazer implements Deserializer<NotificationDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public NotificationDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, NotificationDto.class);
        } catch (Exception e) {
            throw new DeserializeException("Error deserializing NotificationDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}
