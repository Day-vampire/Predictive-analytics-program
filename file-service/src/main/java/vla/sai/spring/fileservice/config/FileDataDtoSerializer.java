package vla.sai.spring.fileservice.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import vla.sai.spring.fileservice.dto.FileDataDto;

public class FileDataDtoSerializer implements Serializer<FileDataDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, FileDataDto data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing FileDataDto", e);
        }
    }

}