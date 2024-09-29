package com.water;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WaterResponse  {

    private String resolveAddress;
    private String description;
    private CurrentConditions currentConditions;

    @JsonCreator
    public WaterResponse(
            @JsonProperty("resolvedAddress") String resolveAddress,
            @JsonProperty("description") String description,
            @JsonProperty("currentConditions") CurrentConditions currentConditions
    ) {
        this.resolveAddress = resolveAddress;
        this.description = description;
        this.currentConditions = currentConditions;
    }


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentConditions {

        private Integer temperature;
        private Integer humidity;


    }

}
