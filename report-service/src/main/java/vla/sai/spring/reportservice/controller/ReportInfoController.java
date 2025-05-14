package vla.sai.spring.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;
import vla.sai.spring.reportservice.service.PdfReportService;
import vla.sai.spring.reportservice.service.ReportInfoService;

@RestController
@RequestMapping(value = "/report/info")
@RequiredArgsConstructor
public class ReportInfoController {
    private final ReportInfoService reportInfoService;


    // Найти отчеты файлы
    @GetMapping
    public Page<ReportInfo> findAll(Pageable pageable) {
        return reportInfoService.findAll(pageable);
    }

    // Найти все отчеты автора
    @GetMapping("/findByAuthorName")
    public Page<ReportInfo> findByAuthorName(@RequestParam("authorName") String authorName,
                                             @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findAllByReportId_ReportAuthorName(authorName, pageable);
    }

    // Найти все отчеты по типу
    @GetMapping("/findByReportType")
    public Page<ReportInfo> findAllByReportType (@RequestParam("reportType") ReportType reportType,
                                                 @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findAllByReportType(reportType, pageable);
    }

    // Найти все отчеты автора по файлу с данными
    @GetMapping("/findByDataFileName")
    public Page<ReportInfo> findAllByDataFileName (@RequestParam("reportDataFileName") String reportDataFileName,
                                                 @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findAllByReportDataFileName(reportDataFileName, pageable);
    }





    // Удалить определенный отчет
    @DeleteMapping("deleteByReportId")
    public void deleteByReportId(@RequestParam("reportId") ReportId reportId) {
        reportInfoService.deleteByReportId(reportId);
    }

    // Удалить все отчет пользователя
    @DeleteMapping("deleteByReportAuthorName")
    public void deleteByReportAuthorName(@RequestParam("reportAuthorName") String reportAuthorName) {
        reportInfoService.deleteAllByReportId_ReportAuthorName(reportAuthorName);
    }

}
