package com.stephanecharron.stack;

import com.stephanecharron.model.VpcTopology;
import com.stephanecharron.model.VpcVertex;
import com.stephanecharron.stackProps.VpcStackProps;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.*;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;

public class VpcStack extends Stack {

    public VpcStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    @lombok.Builder
    public VpcStack(final Construct scope, final String id, final VpcStackProps vpcStackProps) {
        super(scope, id, vpcStackProps);

        Map<String, VpcVertex> vpcVertexMap = new HashMap<>();
        for (VpcTopology vpcTopology: vpcStackProps.getVpcSetup().getNetworkTopology().getVpcs()){

            SubnetConfiguration subnetConfigurationPrivate = SubnetConfiguration.builder()
                    .name("private-sub-"+vpcTopology.getName())
                    .subnetType(SubnetType.PRIVATE_WITH_NAT)
                    .cidrMask(27)
                    .build();
            SubnetConfiguration subnetConfigurationPublic = SubnetConfiguration.builder()
                    .name("public-sub-"+vpcTopology.getName())
                    .subnetType(SubnetType.PUBLIC)
                    .cidrMask(27)
                    .build();

            Vpc vpc = Vpc.Builder.create(this, "ping-"+vpcTopology.getName())
                    .subnetConfiguration(
                            com.sun.tools.javac.util.List.of(
                                    subnetConfigurationPrivate,subnetConfigurationPublic
                            )
                    )
                    .cidr(vpcTopology.getCidr())
                    .maxAzs(vpcStackProps.getVpcSetup().getMaxAzs())
                    .build();

            SecurityGroup.fromSecurityGroupId(this, "DefaultSecurityGroupVpc-"+vpcTopology.getName(), vpc.getVpcDefaultSecurityGroup())
                    .addIngressRule(Peer.anyIpv4(), Port.icmpPing(),"ping rule "+vpcTopology.getName());

            VpcVertex vpcVertex = VpcVertex.builder()
                    .vpc(vpc)
                    .vpcTopology(vpcTopology).build();

            vpcVertexMap.put(vpcTopology.getName(),vpcVertex);
        }
        vpcStackProps.getVpcSetup().getNetworkTopology().setVpcMap(vpcVertexMap);
    }
}
