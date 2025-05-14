package vla.sai.spring.reportservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;

import java.time.LocalDateTime;

public interface ReportInfoService {
    Page<ReportInfo> findAll(Pageable pageable);
    Page<ReportInfo> findAllByReportType(ReportType reportType, Pageable pageable);
    Page<ReportInfo> findAllByReportDataFileName(String reportDataFileName, Pageable pageable);

    void deleteByReportId(ReportIdDto reportId);
    void deleteAllByReportId_ReportAuthorName(String reportIdFileAuthorName);

    Page<ReportInfo> findAllByReportId_ReportAuthorName(String reportIdFileAuthorName, Pageable pageable);
    Page<ReportInfo> findAllByReportId_ReportName(String reportIdFileName, Pageable pageable); // все по имени (могут быть разные расширения)
    Page<ReportInfo> findByReportTypeAndReportId_ReportAuthorName(ReportType reportType, String reportIdReportAuthorName,Pageable pageable); // все по типу
    Page<ReportInfo> findByReportDataFileNameAndReportId_ReportAuthorName(String reportDataFileName, String reportIdReportAuthorName,Pageable pageable); // все отчеты с 1 файла с данными
    Page<ReportInfo> findByReportCreateTimeAfterAndReportId_ReportAuthorName(LocalDateTime reportCreateTimeAfter, String reportIdReportAuthorName, Pageable pageable); // все отчеты пользователя с какогото периода

    Page<ReportInfo> findByReportCreateTimeAfterAndReportCreateTimeBefore(LocalDateTime reportCreateTimeAfter, LocalDateTime reportCreateTimeBefore, String reportAuthorName, Pageable pageable);
    void save(ReportInfo reportInfo);
}
