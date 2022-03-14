package com.stephanecharron.stack;

import com.stephanecharron.model.VpcVertex;
import com.stephanecharron.stackProps.VpcStackProps;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.CfnTransitGateway;
import software.amazon.awscdk.services.ec2.CfnTransitGatewayAttachment;
import software.amazon.awscdk.services.ec2.ISubnet;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import java.util.stream.Collectors;

public class TransitGatewayStack extends Stack {
    public TransitGatewayStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    @lombok.Builder
    public TransitGatewayStack(final Construct scope, final String id, final VpcStackProps vpcStackProps) {
        super(scope, id, vpcStackProps);
        CfnTransitGateway tgw = CfnTransitGateway.Builder.create(this,"Tgw").build();

        for(VpcVertex vpcVertex : vpcStackProps.getVpcSetup().getNetworkTopology().getVpcMap().values()){
            Vpc vpc = vpcVertex.getVpc();
            CfnTransitGatewayAttachment.Builder.create(this, "TgwVpcAttachment_"+vpcVertex.getVpcTopology().getName())
                    .vpcId(vpc.getVpcId())
                    .subnetIds(vpc.getPrivateSubnets()
                            .stream()
                            .map(ISubnet::getSubnetId)
                            .collect(Collectors.toList()))
                    .transitGatewayId(tgw.getRef())
                    .build();
        }

        CfnOutput.Builder.create(this, "TransitGatewayId")
                .value(tgw.getRef())
                .exportName("TransitGatewayId")
                .build();
    }
}
