package vla.sai.spring.analyticsservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 Класс для передачи пользователем параметров необхадимых при проверки автокорреляции и частичной автокорреляции:
 dataFileName - название выбранного пользователем файла с данными
 authorName - имя пользователя (устанавливается автоматически на фронте)
 analyticColumn - выбранный по счету столбец для аналитики
 analyticLags - установленные пользователем лаги (количество предыдущих временных точек,
 которые учитываются при вычислении автокорреляции и частичной автокорреляции)
 */

@Data
@Builder
public class AcfPacfParameters {
    private String dataFileName;
    private String authorName;
    private int analyticColumn;
    private int analyticLags;
}
