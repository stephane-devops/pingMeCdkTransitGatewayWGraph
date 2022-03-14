package com.stephanecharron.stack;

import com.stephanecharron.model.VpcVertex;
import com.stephanecharron.stackProps.VpcStackProps;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.*;
import software.constructs.Construct;

public class InstanceStack extends Stack {
    public InstanceStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    @lombok.Builder
    public InstanceStack(final Construct scope, final String id, final VpcStackProps vpcStackProps) {
        super(scope, id, vpcStackProps);

        for (VpcVertex vpcVertex : vpcStackProps.getVpcSetup().getNetworkTopology().getVpcMap().values()) {

            Vpc vpc = vpcVertex.getVpc();
            String instanceName = String.format("Instance-%s", vpcVertex.getVpcTopology().getName());
            BastionHostLinux instance = BastionHostLinux.Builder.create(this, instanceName)
                    .instanceName(instanceName)
                    .instanceType(InstanceType.of(InstanceClass.BURSTABLE3,InstanceSize.MICRO))
                    .vpc(vpc)
                    .securityGroup(
                            SecurityGroup.fromSecurityGroupId(this,String.format("%sSecurityGroup",instanceName), vpc.getVpcDefaultSecurityGroup()
                            ))
                    .build();

            vpcVertex.setBastion(instance);

            CfnOutput.Builder.create(this,String.format("%sPrivateIp",instance))
                    .value(instance.getInstancePrivateIp())
                    .build();
        }
    }
}
