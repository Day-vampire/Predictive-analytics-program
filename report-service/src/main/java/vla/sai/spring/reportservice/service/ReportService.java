package vla.sai.spring.reportservice.service;

import vla.sai.spring.reportservice.dto.FilterParameters;


public interface ReportService {
    void toExcel(FilterParameters filterParameters);
}
