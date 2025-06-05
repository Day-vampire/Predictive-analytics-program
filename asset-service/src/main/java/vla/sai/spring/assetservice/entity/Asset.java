package vla.sai.spring.assetservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="country", nullable = false)
    private String country;

    @Column(name ="min_price", nullable = false)
    private Long minPrice;

    @Column(name ="total_price", nullable = false)
    private Long totalPrice;

    @Column(name ="volume", nullable = false)
    private Long volume;

    @Column(name ="asset_type", nullable = false)
    private String assetType;

    @Column(name ="deleted", nullable = false)
    private Boolean deleted;

    @Column(name ="creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name ="source", nullable = false)
    private String source;

    @OneToMany (mappedBy = "asset")
    private Set<Price> price;
}
