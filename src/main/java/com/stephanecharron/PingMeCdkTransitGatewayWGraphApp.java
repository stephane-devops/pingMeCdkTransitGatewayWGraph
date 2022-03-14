package com.stephanecharron;

import com.stephanecharron.model.VpcSetup;
import com.stephanecharron.service.GraphPlotService;
import com.stephanecharron.service.NetworkTopologyService;
import com.stephanecharron.stack.InstanceStack;
import com.stephanecharron.stack.RoutesToTransitGatewayStack;
import com.stephanecharron.stack.TransitGatewayStack;
import com.stephanecharron.stack.VpcStack;
import com.stephanecharron.stackProps.VpcStackProps;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;

public class PingMeCdkTransitGatewayWGraphApp {
    public static void main(final String[] args) {
        App app = new App();

        Environment environment = Environment.builder()
                .region((String) app.getNode().tryGetContext("region"))
                .account((String) app.getNode().tryGetContext("account"))
                .build();

        VpcStackProps vpcStackProps = VpcStackProps.builder()
                .environment(environment)
                .vpcSetup(VpcSetup.builder()
                        .maxAzs(1)
                        .networkTopology(NetworkTopologyService.get("src/main/resources/infra.json"))
                        .build())
                .build();

        VpcStack vpcStack = VpcStack.builder()
                .scope(app)
                .id("VpcStack")
                .vpcStackProps(vpcStackProps)
                .build();

        InstanceStack.builder()
                .scope(app)
                .id("InstancePeersStack")
                .vpcStackProps(vpcStackProps)
                .build();

        TransitGatewayStack transitGatewayStack = TransitGatewayStack.builder()
                .scope(app)
                .id("TransitGatewayStack")
                .vpcStackProps(vpcStackProps)
                .build();

       RoutesToTransitGatewayStack routesToTransitGatewayStack = RoutesToTransitGatewayStack.builder()
                .scope(app)
                .id("RoutesToTransitGatewayStack")
                .vpcStackProps(vpcStackProps)
                .build();

        routesToTransitGatewayStack.addDependency(transitGatewayStack);

       GraphPlotService.builder()
               .networkTopology(vpcStackProps.getVpcSetup().getNetworkTopology())
               .build()
               .execute();

        app.synth();
    }
}


