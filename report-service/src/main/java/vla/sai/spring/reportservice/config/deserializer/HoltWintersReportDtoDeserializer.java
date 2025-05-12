package vla.sai.spring.reportservice.config.deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.reportservice.dto.HoltWintersReportDto;

import java.util.Map;

public class HoltWintersReportDtoDeserializer implements Deserializer<HoltWintersReportDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public HoltWintersReportDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, HoltWintersReportDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing FileDataDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}