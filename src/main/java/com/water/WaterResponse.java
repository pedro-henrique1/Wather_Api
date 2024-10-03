package com.water;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WaterResponse implements Serializable {

    private String resolveAddress;
    private String description;
    private Integer temperature;
    private Integer humidity;

    @JsonCreator
    public WaterResponse(@JsonProperty("resolvedAddress") String resolveAddress, @JsonProperty("description") String description, @JsonProperty("temperature") Integer temperature, @JsonProperty("humidity") Integer humidity) {
        this.resolveAddress = resolveAddress;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WaterResponse{" +
                "resolveAddress='" + resolveAddress + '\'' +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
