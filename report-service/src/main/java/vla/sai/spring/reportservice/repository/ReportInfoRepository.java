package vla.sai.spring.reportservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;

import java.util.List;

@Repository
public interface ReportInfoRepository extends JpaRepository<ReportInfo, ReportId> {

    ReportInfo save(ReportInfo fileInfo);

    List<ReportInfo> findAll();
    Page<ReportInfo> findAll(Pageable pageable);
    Page<ReportInfo> findAllByReportId_ReportAuthorName(String reportIdFileAuthorName, Pageable pageable);
    Page<ReportInfo> findAllByReportType(ReportType reportType, Pageable pageable);
    Page<ReportInfo> findAllByReportDataFileName(String reportDataFileName, Pageable pageable);

    Boolean existsByReportId(ReportId reportId);
    Boolean existsByReportId_ReportAuthorName(String reportIdFileAuthorName);

    void deleteByReportId(ReportId reportId);
    void deleteAllByReportId_ReportAuthorName(String reportIdFileAuthorName);
}