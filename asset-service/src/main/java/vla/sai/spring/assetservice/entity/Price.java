package vla.sai.spring.assetservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="open_price", nullable = false)
    private String openPrice;

    @Column(name ="high_price", nullable = false)
    private String highPrice;

    @Column(name ="low_price", nullable = false)
    private String lowPrice;

    @Column(name ="close_price", nullable = false)
    private String closePrice;

    @Column(name ="date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
}
