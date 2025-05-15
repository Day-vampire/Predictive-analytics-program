package vla.sai.spring.reportservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import vla.sai.spring.reportservice.dto.datadto.FileInfoDto;

@FeignClient(
        name = "fileFeignClient",
        url = "${spring.cloud.openfeign.client.config.fileFeignClient.url}",
        path = "/files")
public interface FileServiceClient {
        @GetMapping
        Page<FileInfoDto> getAllFiles(@PageableDefault(size = 10,page = 0) Pageable pageable);
}
