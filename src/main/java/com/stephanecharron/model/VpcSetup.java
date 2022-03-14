package com.stephanecharron.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class VpcSetup {
    private NetworkTopology networkTopology;
    private int maxAzs;
}
