package vla.sai.spring.reportservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;
import vla.sai.spring.reportservice.repository.ReportInfoRepository;
import vla.sai.spring.reportservice.service.ReportInfoService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportInfoServiceImpl implements ReportInfoService {

    private final ReportInfoRepository reportInfoRepository;

    @Override
    public Page<ReportInfo> findAll(Pageable pageable) {
        return reportInfoRepository.findAll(pageable);
    }

    @Override
    public Page<ReportInfo> findAllByReportId_ReportAuthorName(String reportIdFileAuthorName, Pageable pageable) {
        return reportInfoRepository.findAllByReportId_ReportAuthorName(reportIdFileAuthorName, pageable);
    }

    @Override
    public Page<ReportInfo> findAllByReportType(ReportType reportType, Pageable pageable) {
        return reportInfoRepository.findAllByReportType(reportType, pageable);
    }

    @Override
    public Page<ReportInfo> findAllByReportDataFileName(String reportDataFileName, Pageable pageable) {
        return reportInfoRepository.findAllByReportDataFileName(reportDataFileName, pageable);
    }

    @Override
    public void deleteByReportId(ReportId reportId) {
        reportInfoRepository.deleteByReportId(reportId);

    }

    @Override
    public void deleteAllByReportId_ReportAuthorName(String reportIdFileAuthorName) {
        reportInfoRepository.deleteAllByReportId_ReportAuthorName(reportIdFileAuthorName);
    }

    @Override
    public Page<ReportInfo> findAllByReportId_ReportName(String reportIdFileName, Pageable pageable) {
        return reportInfoRepository.findAllByReportId_ReportName(reportIdFileName, pageable);
    }

    @Override
    public Page<ReportInfo> findByReportTypeAndReportId_ReportAuthorName(ReportType reportType, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportTypeAndReportId_ReportAuthorName(reportType, reportIdReportAuthorName, pageable);
    }

    @Override
    public Page<ReportInfo> findByReportDataFileNameAndReportId_ReportAuthorName(String reportDataFileName, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportDataFileNameAndReportId_ReportAuthorName(reportDataFileName, reportIdReportAuthorName, pageable);
    }

    @Override
    public Page<ReportInfo> findByReportCreateTimeAfterAndReportId_ReportAuthorName(LocalDateTime reportCreateTimeAfter, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportCreateTimeAfterAndReportId_ReportAuthorName(reportCreateTimeAfter, reportIdReportAuthorName, pageable);
    }

    @Override
    public Page<ReportInfo> findByReportCreateTimeAfterAndReportCreateTimeBefore(LocalDateTime reportCreateTimeAfter, LocalDateTime reportCreateTimeBefore, Pageable pageable) {
        return reportInfoRepository.findByReportCreateTimeAfterAndReportCreateTimeBefore(reportCreateTimeAfter, reportCreateTimeBefore, pageable);
    }
}
