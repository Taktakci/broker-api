package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.exception.BrokerException;
import com.taktakci.brokerapi.repository.AssetRepository;
import com.taktakci.brokerapi.repository.entity.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AssetService {

    public static final String ASSET_TRY = "TRY";
    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> listAssets(Integer customerId) {
        return assetRepository.findByCustomerId(customerId);
    }

    public void increaseUsableSize(Integer customerId, String assetName, int amount) {
        Asset asset = findCustomerAsset(customerId, assetName);
        asset.setUsableSize(asset.getUsableSize() + amount);
        assetRepository.save(asset);
    }

    public void decreaseUsableSize(Integer customerId, String assetName, Integer amount) {
        Asset asset = findCustomerAsset(customerId, assetName);
        if (asset.getUsableSize() < amount) {
            throw new BrokerException("not enough usable " + assetName);
        }
        asset.setUsableSize(asset.getUsableSize() - amount);
        assetRepository.save(asset);
    }

    private Asset findCustomerAsset(Integer customerId, String assetName) {
        Optional<Asset> assetOptional = assetRepository.findByCustomerIdAndAssetName(customerId, assetName);

        if (assetOptional.isEmpty()) {
            throw new BrokerException(" Asset name " + assetName + " for the customer id " + customerId + " is not available");
        }

        return assetOptional.get();
    }

    public void increaseAssetSize(Integer customerId, String assetName, int amount) {
        Asset asset = findCustomerAsset(customerId, assetName);
        asset.setSize(asset.getSize() + amount);
        assetRepository.save(asset);
    }

    public void decreaseAssetSize(Integer customerId, String assetName, Integer amount) {
        Asset asset = findCustomerAsset(customerId, assetName);
        asset.setSize(asset.getSize() - amount);
        assetRepository.save(asset);
    }
}
