package vla.sai.spring.reportservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import vla.sai.spring.reportservice.dto.FileInfoDto;
import vla.sai.spring.reportservice.dto.FilterParameters;

@FeignClient(
        name = "fileFeignClient",
        url = "${spring.cloud.openfeign.client.config.fileFeignClient.url}",
        path = "/files")
public interface FileServiceClient {
        @GetMapping("/search")
        Page<FileInfoDto> search (FilterParameters filterParameters);
}
