package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.exception.BrokerException;
import com.taktakci.brokerapi.repository.AssetRepository;
import com.taktakci.brokerapi.repository.entity.Asset;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AssetServiceTest {

    @Mock
    private AssetRepository mockedAssetRepository;

    @Test
    void testIncreaseUsableSize_IncreasesSuccessfully() {
        AssetService assetService = new AssetService(mockedAssetRepository);
        Asset asset = new Asset(5, 123, "asset1", 20, 10);
        when(mockedAssetRepository.findByCustomerIdAndAssetName(123, "asset1"))
                .thenReturn(Optional.of(asset));

        assetService.increaseUsableSize(123, "asset1", 5);

        verify(mockedAssetRepository, times(1)).save(asset);
        assertEquals(15, asset.getUsableSize());
    }

    @Test
    void testIncreaseUsableSize_ThrowsExceptionWhenAssetRepositoryReturnsEmptyResult() {
        AssetService assetService = new AssetService(mockedAssetRepository);
        when(mockedAssetRepository.findByCustomerIdAndAssetName(123, "asset1"))
                .thenReturn(Optional.empty());

        assertThrows(BrokerException.class, () ->
                assetService.increaseUsableSize(123, "asset1", 5)
        );
    }

    @Test
    void testDecreaseUsableSize_IncreasesSuccessfully() {
        AssetService assetService = new AssetService(mockedAssetRepository);
        Asset asset = new Asset(5, 123, "asset1", 20, 10);
        when(mockedAssetRepository.findByCustomerIdAndAssetName(123, "asset1"))
                .thenReturn(Optional.of(asset));

        assetService.decreaseUsableSize(123, "asset1", 5);

        verify(mockedAssetRepository, times(1)).save(asset);
        assertEquals(5, asset.getUsableSize());
    }


    @Test
    void testDecreaseUsableSize_ThrowsExceptionWhenUsableSizeIsLessThanAmount() {
        AssetService assetService = new AssetService(mockedAssetRepository);
        Asset asset = new Asset(5, 123, "asset1", 20, 10);
        when(mockedAssetRepository.findByCustomerIdAndAssetName(123, "asset1"))
                .thenReturn(Optional.of(asset));

        assertThrows(BrokerException.class, () ->
                assetService.decreaseUsableSize(123, "asset1", 15)
        );
    }
}