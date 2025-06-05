package vla.sai.spring.assetservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.assetservice.entity.Asset;

import java.util.List;


@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    Asset findByName(String name);
    Asset findById(long id);
    List<Asset> findAllByAssetType(String assetType);
    List<Asset> findAllByDeleted(boolean deleted);
    List<Asset> findAllBySource(String source);
    List<Asset> findByVolumeBetween(Long start, Long end);

    void deleteById(long id);
    Asset save(Asset asset);
}