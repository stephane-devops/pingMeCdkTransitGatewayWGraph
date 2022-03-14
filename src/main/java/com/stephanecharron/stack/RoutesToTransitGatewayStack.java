package com.stephanecharron.stack;

import com.stephanecharron.model.VpcTopology;
import com.stephanecharron.model.VpcVertex;
import com.stephanecharron.stackProps.VpcStackProps;
import software.amazon.awscdk.Fn;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.CfnRoute;
import software.amazon.awscdk.services.ec2.ISubnet;
import software.constructs.Construct;

import java.util.Map;


public class RoutesToTransitGatewayStack extends Stack {

    public RoutesToTransitGatewayStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    @lombok.Builder
    public RoutesToTransitGatewayStack(final Construct scope, final String id, final VpcStackProps vpcStackProps) {
        super(scope, id, vpcStackProps);

        Map<String, VpcVertex> vpcVertexMap = vpcStackProps.getVpcSetup().getNetworkTopology().getVpcMap();

        for (VpcTopology vpcTopology : vpcStackProps.getVpcSetup().getNetworkTopology().getVpcs())
            for (String vpcTargetName : vpcTopology.getLinks()) {
                VpcVertex source = vpcVertexMap.get(vpcTopology.getName());
                VpcVertex target = vpcVertexMap.get(vpcTargetName);
                createRoute(source, target);
            }
    }

    private void createRoute(VpcVertex source, VpcVertex target) {

        for (ISubnet subnet : source.getVpc().getPrivateSubnets()) {
            CfnRoute.Builder.create(this, String.format("RouteFromPrivateSubnetOf%sTo%s", source.getVpcTopology().getName(), target.getVpcTopology().getName()))
                    .routeTableId(subnet.getRouteTable().getRouteTableId())
                    .destinationCidrBlock(target.getVpc().getVpcCidrBlock())
                    .transitGatewayId(Fn.importValue("TransitGatewayId"))
                    .build();
        }
    }
}
