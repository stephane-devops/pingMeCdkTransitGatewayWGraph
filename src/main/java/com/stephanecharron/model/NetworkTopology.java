package com.stephanecharron.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vpcs"
})
@Generated("jsonschema2pojo")
public class NetworkTopology {

    @JsonProperty("vpcs")
    private List<VpcTopology> vpcs = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("vpcs")
    public List<VpcTopology> getVpcs() {
        return vpcs;
    }

    @JsonProperty("vpcs")
    public void setVpcs(List<VpcTopology> vpcs) {
        this.vpcs = vpcs;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    private Map<String, VpcVertex> vpcMap = new HashMap<>();

    public Map<String, VpcVertex> getVpcMap() {
        return vpcMap;
    }

    public void setVpcMap(Map<String, VpcVertex> vpcMap) {
        this.vpcMap = vpcMap;
    }
}