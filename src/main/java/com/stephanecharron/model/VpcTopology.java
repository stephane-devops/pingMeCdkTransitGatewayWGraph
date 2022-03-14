package com.stephanecharron.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cidr",
        "name",
        "links"
})
@Generated("jsonschema2pojo")
public class VpcTopology {

    @JsonProperty("cidr")
    private String cidr;
    @JsonProperty("name")
    private String name;
    @JsonProperty("links")
    private List<String> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cidr")
    public String getCidr() {
        return cidr;
    }

    @JsonProperty("cidr")
    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("links")
    public List<String> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<String> links) {
        this.links = links;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}