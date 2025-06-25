package vla.sai.spring.assetservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.assetservice.entity.Price;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByAsset_Id(Long id);
    List<Price> findByDateBetweenAndAsset_Id(LocalDateTime from, LocalDateTime to, Long id);
    List<Price> findByHighPriceBetweenAndAsset_Id(String highPrice, String highPrice2, Long asset_id);
    List<Price> findByLowPriceBetweenAndAsset_Id(String lowPrice, String lowPrice2, Long asset_id);
    List<Price> findByOpenPriceBetweenAndAsset_Id(String openPrice, String openPrice2, Long asset_id);
    List<Price> findByClosePriceBetweenAndAsset_Id(String closePrice, String closePrice2, Long asset_id);

    Price save(Price price);
    void deleteById(Long id);
    void deleteByDateBetweenAndAsset_Id (LocalDateTime from, LocalDateTime to, Long id);
    void deleteByAsset_Id(Long id);
}