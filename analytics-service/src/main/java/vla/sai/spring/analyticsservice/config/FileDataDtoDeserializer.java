package vla.sai.spring.analyticsservice.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.analyticsservice.dto.FileDataDto;

import java.util.Map;

public class FileDataDtoDeserializer implements Deserializer<FileDataDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public FileDataDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, FileDataDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing FileDataDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}