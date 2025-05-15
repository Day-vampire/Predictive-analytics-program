package vla.sai.spring.reportservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.exception.ExcelExportException;
import vla.sai.spring.reportservice.service.ExcelReportService;
import vla.sai.spring.reportservice.service.feign.FileServiceClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class ExcelReportServiceImpl implements ExcelReportService {

    private static final int MAX_ROWS_FOR_SHEET = 1000;

    @Override
    public <T> StreamingResponseBody dataToExcel(Pageable pageable, T objectType, Function<Pageable, Page<T>> feignClientMethod) {
            return outputStream -> {
                try (Workbook workbook = new SXSSFWorkbook(100)) { // создание потоковой книги (по умолчанию 100 строк)
                    int sheetCount = 0;
                    int rowCount = 0;
                    int pageNumber = 0;
                    Field[] fields = objectType.getClass().getDeclaredFields();
                    SXSSFSheet sheet = createSheet (workbook, "List-%d".formatted(++sheetCount), fields);

                    while (true) {
                        Page<T> pageContent = feignClientMethod.apply(PageRequest.of(pageNumber, pageable.getPageSize()));
                        List<T> content = pageContent.getContent();
                        if (content.isEmpty()) {
                            autoSizeColumns(sheet, fields);
                            break;
                        }
                        for (T tDto : content) { // перебор записей из дто
                            if (rowCount >= MAX_ROWS_FOR_SHEET) {
                                autoSizeColumns(sheet, fields);
                                sheet = createSheet(workbook, "List-%d".formatted(++sheetCount), fields);
                                rowCount = 0;
                            }
                            Row row = sheet.createRow(++rowCount);
                            fillRow(row, tDto); // заполнение данных из дто в строку файла
                        }
                        pageNumber++;
                    }

                    workbook.write(outputStream);
                    outputStream.flush();
                } catch (IOException e) {
                    throw new ExcelExportException("Failed export dto data to Excel file", e);
                }
            };
        }

        // Создание страницы с заголовками
        private SXSSFSheet createSheet(Workbook workbook, String sheetName, Field[] fields) {
            SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(sheetName);
            Row headerRow = sheet.createRow(0);
            IntStream.range(0, fields.length).forEach(index ->
                    headerRow
                            .createCell(index)
                            .setCellValue(fields[index].getName())
            );
            sheet.trackAllColumnsForAutoSizing();
            return sheet;
        }

        //Заполнение строки данными
        private <T> void fillRow(Row row, T t) {}

        //Автоматическое определение ширины столбца
        private void autoSizeColumns(SXSSFSheet sheet, Field[] fields) {
            IntStream.range(0, fields.length).forEach(sheet::autoSizeColumn);
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

    @Override
    public StreamingResponseBody sarimaToExcel(Object object) {
        return null;
    }
}
