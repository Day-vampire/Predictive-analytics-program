package vla.sai.spring.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vla.sai.spring.reportservice.dto.FilterParameters;
import vla.sai.spring.reportservice.service.ReportService;



@RestController
@RequestMapping(value = "/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping(path = "/Excel")
    public void toExcel(FilterParameters filterParameters) {
        reportService.toExcel(filterParameters);
    }
}