package com.taktakci.brokerapi.controller;

import com.taktakci.brokerapi.controller.dto.AssetDto;
import com.taktakci.brokerapi.repository.entity.Asset;
import com.taktakci.brokerapi.service.AssetService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asset")
@Slf4j
public class AssetController {

    private final ModelMapper mapper;
    private final AssetService service;

    public AssetController(ModelMapper mapper, AssetService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AssetDto>> listAssets(@PathVariable("customerId") Integer customerId) {
        log.info("listAsset request received for customer id: {}", customerId);

        List<Asset> assetList = service.listAssets(customerId);
        List<AssetDto> assetDtoList = mapper.map(assetList, List.class);

        log.info("listAsset request returning for customer id: {}", customerId);
        return new ResponseEntity<>(assetDtoList, HttpStatus.OK);
    }

}
