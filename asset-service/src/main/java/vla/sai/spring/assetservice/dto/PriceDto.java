package vla.sai.spring.assetservice.dto;

import lombok.*;
import java.time.LocalDate;

@Value
@Setter
public class PriceDto {
    Long id;
    String openPrice;
    String highPrice;
    String lowPrice;
    String closePrice;
    LocalDate date;
    Long assetId;
}
