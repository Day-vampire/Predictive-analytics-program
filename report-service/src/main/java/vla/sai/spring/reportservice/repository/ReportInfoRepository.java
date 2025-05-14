package vla.sai.spring.reportservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportInfoRepository extends JpaRepository<ReportInfo, ReportId> {

    ReportInfo save(ReportInfo fileInfo);

    // Отчеты "все"
    List<ReportInfo> findAll();
    Page<ReportInfo> findAll(Pageable pageable);
    Page<ReportInfo> findAllByReportType(ReportType reportType, Pageable pageable);
    Page<ReportInfo> findAllByReportDataFileName(String reportDataFileName, Pageable pageable);

    // Отчеты "определенного пользователя"
    Page<ReportInfo> findAllByReportId_ReportAuthorName(String reportIdFileAuthorName, Pageable pageable); // все пользователя
    Page<ReportInfo> findAllByReportId_ReportName(String reportIdFileName, Pageable pageable); // все по имени (могут быть разные расширения)
    Page<ReportInfo> findByReportTypeAndReportId_ReportAuthorName(ReportType reportType, String reportIdReportAuthorName,Pageable pageable); // все по типу
    Page<ReportInfo> findByReportDataFileNameAndReportId_ReportAuthorName(String reportDataFileName, String reportIdReportAuthorName,Pageable pageable); // все отчеты с 1 файла с данными
    Page<ReportInfo> findByReportCreateTimeAfterAndReportId_ReportAuthorName(LocalDateTime reportCreateTimeAfter, String reportIdReportAuthorName, Pageable pageable); // все отчеты пользователя с какогото периода
    Page<ReportInfo> findByReportCreateTimeAfterAndReportCreateTimeBeforeAndReportId_ReportAuthorName (LocalDateTime reportCreateTimeAfter, LocalDateTime reportCreateTimeBefore, String reportAuthorName,  Pageable pageable); // все отчеты пользователя за какойто период

    Boolean existsByReportId(ReportId reportId);
    Boolean existsByReportId_ReportAuthorName(String reportIdFileAuthorName);

    void deleteByReportId(ReportId reportId);
    void deleteAllByReportId_ReportAuthorName(String reportIdFileAuthorName);

}