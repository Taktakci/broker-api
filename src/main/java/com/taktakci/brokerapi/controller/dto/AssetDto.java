package com.taktakci.brokerapi.controller.dto;

import lombok.Data;

@Data
public class AssetDto {
    private Integer id;
    private Integer customerId;
    private String assetName;
    private Integer size;
    private Integer usableSize;
}
