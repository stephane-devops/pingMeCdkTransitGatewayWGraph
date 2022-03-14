package com.stephanecharron.stackProps;

import com.stephanecharron.model.VpcSetup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class VpcStackProps implements StackProps {

    private VpcSetup vpcSetup;
    private Environment environment;

    @Override
    public @Nullable Environment getEnv() {
        return this.environment;
    }
}
