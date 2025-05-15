package vla.sai.spring.reportservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.function.Function;


public interface ExcelReportService {
    <T> StreamingResponseBody dataToExcel(Pageable pageable, T objectType, Function<Pageable, Page<T>> feignClientMethod);
    StreamingResponseBody smoothingGraphToExcel(Object object);
    StreamingResponseBody holtWintersGraphToExcel(Object object);
    StreamingResponseBody arimaToExcel(Object object);
    StreamingResponseBody sarimaToExcel(Object object);

}
