package vla.sai.spring.analyticsservice.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vla.sai.spring.analyticsservice.dto.*;
import vla.sai.spring.analyticsservice.service.kafka.Producer;

import java.io.IOException;

import static org.mockito.Mockito.*;

class AnalyticsServicePhotoImplTest {

    private AnalyticsServicePhotoImpl service;
    private Producer producer;

    @BeforeEach
    void setUp() {
        producer = mock(Producer.class);
        service = new AnalyticsServicePhotoImpl(producer);
    }

    @Test
    void testSmoothingGraphPhoto() throws Exception {
        SmoothingParameters parameters = SmoothingParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(1)
                .smoothingWindow(5)
                .build();

        service.smoothingGraphPhoto(parameters);
    }

    @Test
    void testAcfPacfPhoto() throws Exception {
        AcfPacfParameters parameters = AcfPacfParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(2)
                .analyticLags(10)
                .build();

        service.acfPacfPhoto(parameters);
    }

    @Test
    void testHoltWintersGraphPhoto() throws Exception {
        HoltWintersParameters parameters = HoltWintersParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(0)
                .seasonLength(12)
                .periods(24)
                .build();

        service.holtWintersGraphPhoto(parameters);
    }

    @Test
    void testArimaAnalyticsPhoto() {
        ArimaParameters parameters = ArimaParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(0)
                .periods(5)
                .build();

        service.arimaAnalyticsPhoto(parameters);
    }

    @Test
    void testSarimaAnalyticsPhotoStub() {
        SmoothingParameters parameters = SmoothingParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(0)
                .smoothingWindow(3)
                .build();

        service.sarimaAnalyticsPhoto(parameters);
    }

    // Повторяющиеся тесты для покрытия каждого метода в разном контексте
    @Test
    void testSmoothingGraphPhotoWithEmptyFileName() throws Exception {
        SmoothingParameters parameters = SmoothingParameters.builder()
                .authorName("user")
                .dataFileName("")
                .analyticColumn(1)
                .smoothingWindow(5)
                .build();

        service.smoothingGraphPhoto(parameters);
    }

    @Test
    void testAcfPacfPhotoWithZeroLags() throws Exception {
        AcfPacfParameters parameters = AcfPacfParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(0)
                .analyticLags(0)
                .build();

        service.acfPacfPhoto(parameters);
    }

    @Test
    void testHoltWintersGraphPhotoWithZeroPeriod() throws Exception {
        HoltWintersParameters parameters = HoltWintersParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(1)
                .seasonLength(0)
                .periods(0)
                .build();

        service.holtWintersGraphPhoto(parameters);
    }

    @Test
    void testArimaAnalyticsPhotoWithLargePeriod() {
        ArimaParameters parameters = ArimaParameters.builder()
                .authorName("user")
                .dataFileName("file.csv")
                .analyticColumn(0)
                .periods(100)
                .build();

        service.arimaAnalyticsPhoto(parameters);
    }

    @Test
    void testSarimaAnalyticsPhotoWithNullFilename() {
        SmoothingParameters parameters = SmoothingParameters.builder()
                .authorName("user")
                .dataFileName(null)
                .analyticColumn(0)
                .smoothingWindow(3)
                .build();

        service.sarimaAnalyticsPhoto(parameters);
    }
}
