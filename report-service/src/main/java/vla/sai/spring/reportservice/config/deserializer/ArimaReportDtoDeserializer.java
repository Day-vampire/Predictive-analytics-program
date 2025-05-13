package vla.sai.spring.reportservice.config.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import vla.sai.spring.reportservice.dto.analyticsdto.ArimaReportDto;
import vla.sai.spring.reportservice.exception.DeserializeException;

import java.util.Map;

public class ArimaReportDtoDeserializer implements Deserializer<ArimaReportDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если требуется
    }

    @Override
    public ArimaReportDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ArimaReportDto.class);
        } catch (Exception e) {
            throw new DeserializeException("Error deserializing ArimaReportDto", e);
        }
    }

    @Override
    public void close() {
        // Закрытие, если требуется
    }
}
