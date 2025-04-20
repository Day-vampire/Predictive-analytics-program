package vla.sai.spring.reportservice.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Data
public class FilterParameters {
    private Integer page;
    private Integer rows;
    private String searchText = "";
    private Map<String, Sort.Direction> sortDirection = new HashMap<>();

    public boolean hasSearchText() { return searchText != null && !searchText.isEmpty(); }
}
