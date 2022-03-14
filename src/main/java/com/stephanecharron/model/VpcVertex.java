package com.stephanecharron.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awscdk.services.ec2.BastionHostLinux;
import software.amazon.awscdk.services.ec2.Vpc;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class VpcVertex {
    private VpcTopology vpcTopology;
    private Vpc vpc;
    private BastionHostLinux bastion;
}
