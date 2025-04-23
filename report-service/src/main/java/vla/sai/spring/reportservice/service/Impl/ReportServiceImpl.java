package vla.sai.spring.reportservice.service.Impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import vla.sai.spring.reportservice.dto.FilterParameters;
import vla.sai.spring.reportservice.service.ReportService;

import java.io.IOException;
import java.lang.reflect.Field;


@Service
public class ReportServiceImpl implements ReportService {

    private static final int MAX_ROWS_FOR_SHEET = 1000;

    @Override
    public void toExcel(FilterParameters filterParameters) {

        try (Workbook workbook = new SXSSFWorkbook(100) ){
            Field [] fields = FilterParameters.class.getDeclaredFields();
            Sheet sheet = createSheet(workbook, fields, "first-page");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Sheet createSheet(Workbook workbook, Field[] fields, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        for(Field field : fields) {
            headerRow.createCell(0).setCellValue(field.getName());
        }
        return sheet;
    }
}
