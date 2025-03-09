package com.taktakci.brokerapi.controller;

import com.taktakci.brokerapi.controller.dto.MatchDto;
import com.taktakci.brokerapi.service.MatchService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
@Slf4j
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<String> matchOrder(@RequestBody @Valid MatchDto matchDto) {
        log.info("matchOrder request received - {}", matchDto);

        service.matchOrder(matchDto);
        log.info("matchOrder request is returning - {}", matchDto);
        return new ResponseEntity<>("Orders matched successfully", HttpStatus.OK);
    }

}
