package vla.sai.spring.reportservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.dto.ReportInfoDto;
import vla.sai.spring.reportservice.entity.ReportInfo;
import vla.sai.spring.reportservice.entity.ReportType;
import vla.sai.spring.reportservice.mapper.ReportIdMapper;
import vla.sai.spring.reportservice.mapper.ReportInfoMapper;
import vla.sai.spring.reportservice.repository.ReportInfoRepository;
import vla.sai.spring.reportservice.service.ReportInfoService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportInfoServiceImpl implements ReportInfoService {

    private final ReportInfoRepository reportInfoRepository;
    private final ReportInfoMapper reportInfoMapper;
    private final ReportIdMapper reportIdMapper;

    @Override
    public Page<ReportInfoDto> findAll(Pageable pageable) {
        return reportInfoRepository.findAll(pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findAllByReportId_ReportAuthorName(String reportIdFileAuthorName, Pageable pageable) {
        return reportInfoRepository.findAllByReportId_ReportAuthorName(reportIdFileAuthorName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findAllByReportType(ReportType reportType, Pageable pageable) {
        return reportInfoRepository.findAllByReportType(reportType, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findAllByReportDataFileName(String reportDataFileName, Pageable pageable) {
        return reportInfoRepository.findAllByReportDataFileName(reportDataFileName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    @Transactional
    public void deleteByReportId(ReportIdDto reportIdDto) {

        String reportAuthorName = Optional.ofNullable(reportIdDto.getReportAuthorName())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("reportAuthorName is null or empty"));
        String reportName = Optional.ofNullable(reportIdDto.getReportName())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("reportName is null or empty"));
        reportInfoRepository.deleteByReportId(reportIdMapper.toEntity(reportIdDto));
    }

    @Override
    public void deleteAllByReportId_ReportAuthorName(String reportIdFileAuthorName) {
        reportInfoRepository.deleteAllByReportId_ReportAuthorName(reportIdFileAuthorName);
    }

    @Override
    public Page<ReportInfoDto> findAllByReportId_ReportName(String reportIdFileName, Pageable pageable) {
        return reportInfoRepository.findAllByReportId_ReportName(reportIdFileName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findByReportTypeAndReportId_ReportAuthorName(ReportType reportType, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportTypeAndReportId_ReportAuthorName(reportType, reportIdReportAuthorName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findByReportDataFileNameAndReportId_ReportAuthorName(String reportDataFileName, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportDataFileNameAndReportId_ReportAuthorName(reportDataFileName, reportIdReportAuthorName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findByReportCreateTimeAfterAndReportId_ReportAuthorName(LocalDateTime reportCreateTimeAfter, String reportIdReportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportCreateTimeAfterAndReportId_ReportAuthorName(reportCreateTimeAfter, reportIdReportAuthorName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public Page<ReportInfoDto> findByReportCreateTimeAfterAndReportCreateTimeBefore(LocalDateTime reportCreateTimeAfter, LocalDateTime reportCreateTimeBefore, String reportAuthorName, Pageable pageable) {
        return reportInfoRepository.findByReportCreateTimeAfterAndReportCreateTimeBeforeAndReportId_ReportAuthorName(reportCreateTimeAfter, reportCreateTimeBefore,reportAuthorName, pageable).map(reportInfoMapper::toDto);
    }

    @Override
    public void save(ReportInfo reportInfo) {
        reportInfoRepository.save(reportInfo);
    }
}
