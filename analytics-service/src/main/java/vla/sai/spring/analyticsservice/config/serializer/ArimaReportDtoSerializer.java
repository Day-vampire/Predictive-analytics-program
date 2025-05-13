package vla.sai.spring.analyticsservice.config.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import vla.sai.spring.analyticsservice.dto.ArimaReportDto;
import vla.sai.spring.analyticsservice.exception.SerializeException;

public class ArimaReportDtoSerializer implements Serializer<ArimaReportDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, ArimaReportDto data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializeException("Error serializing ArimaReportDto", e);
        }
    }

}
