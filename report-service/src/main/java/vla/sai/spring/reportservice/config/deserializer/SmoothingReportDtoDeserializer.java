package vla.sai.spring.reportservice.config.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.reportservice.dto.SmoothingReportDto;
import vla.sai.spring.reportservice.exception.DeserializeException;

import java.util.Map;

public class SmoothingReportDtoDeserializer implements Deserializer<SmoothingReportDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public SmoothingReportDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, SmoothingReportDto.class);
        } catch (Exception e) {
            throw new DeserializeException("Error deserializing SmoothingReportDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}
