package vla.sai.spring.reportservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import vla.sai.spring.reportservice.dto.datadto.FileInfoDto;
import vla.sai.spring.reportservice.dto.datadto.FilterParameters;
import vla.sai.spring.reportservice.service.ExcelReportService;
import vla.sai.spring.reportservice.service.feign.FileServiceClient;

import java.lang.reflect.Field;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class ExcelReportServiceImpl implements ExcelReportService {

    private static final int MAX_ROWS_FOR_SHEET = 1000;
    private final FileServiceClient fileServiceClient;

    @Override
    public StreamingResponseBody dataToExcel(FilterParameters filterParameters) {
//            return outputStream -> {
//                try (Workbook workbook = new SXSSFWorkbook(100)) { // создание потоковой книги (по умолчанию 100 строк)
//                    int sheetCount = 0;
//                    int rowCount = 0;
//                    filterParameters.setPage(0);
//                    Field[] fields = FileInfoDto.class.getDeclaredFields();
//                    SXSSFSheet sheet = createSheet (workbook, "List-%d".formatted(++sheetCount), fields);
//
//                    while (true) {
//                        Page<FileInfoDto> fileInfoDtoPage = fileServiceClient.search(filterParameters); // получение данных из микросервиса
//                        List<FileInfoDto> fileInfoDtoPageContent = fileInfoDtoPage.getContent();
//                        if (fileInfoDtoPageContent.isEmpty()) {
//                            autoSizeColumns(sheet, fields);
//                            break;
//                        }
//                        for (FileInfoDto fileInfoDto : fileInfoDtoPageContent) { // перебор записей из дто
//                            if (rowCount >= MAX_ROWS_FOR_SHEET) {
//                                autoSizeColumns(sheet, fields);
//                                sheet = createSheet(workbook, "List-%d".formatted(++sheetCount), fields);
//                                rowCount = 0;
//                            }
//                            Row row = sheet.createRow(++rowCount);
//                            fillRow(row, fileInfoDto); // заполнение данных из дто в строку файла
//                        }
//                        filterParameters.setPage(filterParameters.getPage() + 1);
//                    }
//
//                    workbook.write(outputStream);
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new ExcelExportException("Failed export dto data to Excel file", e);
//                }
//            };
        return null;
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
        private void fillRow(Row row, FileInfoDto fileInfoDto) {
            row.createCell(0).setCellValue(fileInfoDto.getFileId().toString());
            row.createCell(1).setCellValue(fileInfoDto.getFileSize().longValue());
            row.createCell(0).setCellValue(fileInfoDto.getIsDeleted());
            row.createCell(0).setCellValue(fileInfoDto.getCreateTime().toString());
            row.createCell(0).setCellValue(fileInfoDto.getLastModificationTime().toString());
        }

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
