package vla.sai.spring.analyticsservice.config.serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import vla.sai.spring.analyticsservice.dto.HoltWintersReportDto;

public class HoltWintersReportDtoSerializer implements Serializer<HoltWintersReportDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, HoltWintersReportDto data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing HoltWintersReportDto", e);
        }
    }

}