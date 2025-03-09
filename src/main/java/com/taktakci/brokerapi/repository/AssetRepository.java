package com.taktakci.brokerapi.repository;

import com.taktakci.brokerapi.repository.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByCustomerId(Integer customerId);

    Optional<Asset> findByCustomerIdAndAssetName(Integer customerId, String assetName);

}
