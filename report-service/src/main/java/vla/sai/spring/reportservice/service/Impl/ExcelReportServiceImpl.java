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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.stream.IntStream;


@Service
public class ExcelReportServiceImpl implements ExcelReportService {

    private static final int MAX_ROWS_FOR_SHEET = 1000;

    @Override
    public StreamingResponseBody dataToExcel(Object object) {
        return outputStream -> {
            try (Workbook workbook = new SXSSFWorkbook(100)) { // создание потоковой книги (по умолчанию 100 строк)
                int sheetCount = 0;
                int rowCount = 0;
                Field[] fields = Object.class.getDeclaredFields(); // получение полей для заголовков и ячеек
                Sheet sheet = createSheet(workbook, "List-%d".formatted(++sheetCount), fields); // создание листа с нужным названием и полями
//                while (true) {
//
//                    if (objectDtoContent.isEmpty()) {
//                        break;
//                    }
//                    for (objectDto objectDto : objectDtoContent) { // перебор записей из дто
//                        if (rowCount >= MAX_ROWS_FOR_XLSX_SHEET) {
//                            sheet = createSheet(workbook, "List-%d".formatted(++sheetCount), fields);
//                            rowCount = 0;
//                        }
//                        Row row = sheet.createRow(++rowCount);
//                        fillRow(row, objectDto, fields); // заполнение данных из дто в строку файла
//                    }
//
//                    filterParameters.setPage(filterParameters.getPage() + 1);
//                }
                workbook.write(outputStream);
                outputStream.flush();
            } catch (IOException e) {
                throw new ExcelExportException("Failed export dto data to Excel file", e);
            }
        };
    }


    @Override
    public StreamingResponseBody smoothingGraphToExcel(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody holtWintersGraphToExcel(Object object) {
        return null;
    }

    @Override
    public StreamingResponseBody arimaToExcel(Object object) {
        return null;
    }


    // Создание стриницы с заголовками
    private Sheet createSheet(Workbook workbook, String sheetName, Field[] fields) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, fields.length).forEach(index -> headerRow
                .createCell(index)
                .setCellValue(fields[index].getName())
        );
        return sheet;
    }

    //Заполнение строки данными
    private void fillRow(Row row, Object objectDto, Field[] fields) {
        IntStream.range(0, fields.length)
                .forEach(index -> {
                            try {
                                fields[index].setAccessible(true);
                                Object value = fields[index].get(objectDto);
                                row.createCell(index).setCellValue(String.valueOf(value));
                            } catch (IllegalAccessException e) {
                                throw new FieldAccessException(fields[index].getName(), e);
                            }
                        }
                );
    }
}
