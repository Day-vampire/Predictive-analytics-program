package vla.sai.spring.fileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileDataType {
    COMPANY_FINANCIAL_ASSERTS("company_financial_asserts"),      // финансовые активы компании
    GROUPED_FINANCIAL_ASSERTS("grouped_financial_asserts"),      // финансовые активы одной группы
    FINANCIAL_ASSERT_STORY("financial_assert_story");// истроия курсов финансового актива

    private final String value;
}
