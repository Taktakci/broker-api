package com.taktakci.brokerapi.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ASSET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="CUSTOMER_ID")
    private Integer customerId;

    @Column(name="ASSET_NAME")
    private String assetName;

    @Column(name="SIZE")
    private Integer size;

    @Column(name="USABLE_SIZE")
    private Integer usableSize;

}
