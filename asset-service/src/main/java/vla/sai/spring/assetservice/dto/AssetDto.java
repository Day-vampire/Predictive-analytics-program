package vla.sai.spring.assetservice.dto;

import lombok.*;

import java.time.LocalDate;

@Value
@Setter
public class AssetDto {
    Long id;
    String name;
    String country;
    Long minPrice;
    Long totalPrice;
    Long volume;
    String assetType;
    Boolean deleted;
    LocalDate creationDate;
    String source;
}
