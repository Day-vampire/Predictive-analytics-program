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
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;
import vla.sai.spring.reportservice.service.ReportInfoService;

import java.time.LocalDateTime;

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


    @GetMapping("/findByReportTypeAndAuthor")
    public Page<ReportInfo> findAllByDataFileName ( @RequestParam("reportType") ReportType reportType,
                                                   @RequestParam("authorName") String authorName,
                                                   @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findByReportTypeAndReportId_ReportAuthorName(reportType,authorName, pageable);
    }

    @GetMapping("/findByDataFileNameAndAuthor")
    public Page<ReportInfo> findAllByDataFileName (@RequestParam("dataFileName") String dataFileName,
                                                   @RequestParam("authorName") String authorName,
                                                   @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findByReportDataFileNameAndReportId_ReportAuthorName(dataFileName,authorName, pageable);
    }

    @GetMapping("/findByAfterDateCreationAndAuthor")
    public Page<ReportInfo> findAllByDataFileName (@RequestParam("reportCreateTimeAfter") LocalDateTime reportCreateTimeAfter,
                                                   @RequestParam("authorName") String authorName,
                                                   @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findByReportCreateTimeAfterAndReportId_ReportAuthorName(reportCreateTimeAfter,authorName, pageable);
    }

    @GetMapping("/findByPeriodAndAuthor")
    public Page<ReportInfo> findAllByDataFileName (@RequestParam("reportCreateTimeAfter") LocalDateTime reportCreateTimeAfter,
                                                   @RequestParam("reportCreateTimeBefore") LocalDateTime reportCreateTimeBefore,
                                                   @RequestParam("authorName") String authorName,
                                                   @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return reportInfoService.findByReportCreateTimeAfterAndReportCreateTimeBefore(reportCreateTimeAfter,reportCreateTimeBefore,authorName, pageable);
    }

    // Удалить определенный отчет из БД
    @DeleteMapping("deleteByReportId")
    public void deleteByReportId(@RequestParam("reportId") ReportIdDto reportIdDto) {
        reportInfoService.deleteByReportId(reportIdDto);
    }

    // Удалить все отчеты пользователя из БД
    @DeleteMapping("deleteByReportAuthorName")
    public void deleteByReportAuthorName(@RequestParam("reportAuthorName") String reportAuthorName) {
        reportInfoService.deleteAllByReportId_ReportAuthorName(reportAuthorName);
    }

}
