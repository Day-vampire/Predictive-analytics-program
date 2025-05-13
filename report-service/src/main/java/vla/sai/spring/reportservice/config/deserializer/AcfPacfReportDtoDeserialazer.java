package vla.sai.spring.reportservice.config.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.reportservice.dto.analyticsdto.AcfPacfReportDto;

import java.util.Map;

public class AcfPacfReportDtoDeserialazer implements Deserializer<AcfPacfReportDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public AcfPacfReportDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, AcfPacfReportDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing AcfPacfReportDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}
