package com.taktakci.brokerapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrokerException extends RuntimeException{

    private final String description;

    public BrokerException(String description) {
        super(description);
        this.description = description;
    }

}