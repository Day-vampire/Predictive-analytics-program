package vla.sai.spring.reportservice.service.Impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.exception.ExcelExportException;
import vla.sai.spring.reportservice.exception.FieldAccessException;
import vla.sai.spring.reportservice.service.ExcelReportService;
import vla.sai.spring.reportservice.service.PdfReportService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.stream.IntStream;


@Service
public class PdfReportServiceImpl implements PdfReportService {
    @Override
    public StreamingResponseBody dataToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody smoothingGraphToPdf(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody holtWintersGraphToPdf(Object object) {
        return null;
    }
}
