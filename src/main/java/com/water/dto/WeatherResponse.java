package com.water.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private String resolvedAddress;
    private String address;
    private String timezone;
    private String description;
    private List<Day> days;
    private CurrentConditions currentConditions;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Day {
    private String datetime;           // Data no formato "YYYY-MM-DD"
    private double tempmax;            // Temperatura máxima em graus Celsius
    private double tempmin;            // Temperatura mínima em graus Celsius
    private double temp;               // Temperatura atual em graus Celsius
    private double feelslikemax;       // Temperatura percebida máxima em graus Celsius
    private double feelslikemin;       // Temperatura percebida mínima em graus Celsius
    private double feelslike;          // Temperatura percebida atual em graus Celsius
    private double dew;                // Ponto de orvalho em graus Celsius
    private double humidity;           // Umidade em porcentagem
    private double precip;             // Precipitação em mm
    private double precipprob;         // Probabilidade de precipitação em porcentagem
    private double precipcover;        // Cobertura de precipitação em porcentagem
    private List<String> preciptype;         // Tipo de precipitação (ex: chuva, neve)
    private double snow;               // Neve em mm
    private double snowdepth;          // Profundidade da neve em mm
    private double windgust;           // Rajada de vento em km/h ou m/s
    private double windspeed;          // Velocidade do vento em km/h ou m/s
    private double winddir;            // Direção do vento em graus
    private double cloudcover;         // Cobertura de nuvens em porcentagem
    private double visibility;         // Visibilidade em km
    private double uvindex;            // Índice UV
    private double severerisk;         // Risco severo (escala de 1 a 10)
    private String conditions;         // Condições meteorológicas (ex: "Parcialmente nublado")
    private String description;        // Descrição detalhada das condições meteorológicas
    private String icon;               // Ícone que representa as condições
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrentConditions {
    private String datetime;           // Hora no formato "HH:mm:ss"
    private double temp;               // Temperatura atual em graus Celsius
    private double feelslike;          // Temperatura percebida atual em graus Celsius
    private double humidity;            // Umidade em porcentagem
    private double dew;                // Ponto de orvalho em graus Celsius
    private double precip;             // Precipitação em mm
    private double precipprob;         // Probabilidade de precipitação em porcentagem
    private double snow;               // Neve em mm
    private double snowdepth;          // Profundidade da neve em mm
    private List<String> preciptype;         // Tipo de precipitação (ex: chuva, neve)
    private double windgust;           // Rajada de vento em km/h ou m/s
    private double windspeed;          // Velocidade do vento em km/h ou m/s
    private double winddir;            // Direção do vento em graus
    private double visibility;         // Visibilidade em km
    private double cloudcover;         // Cobertura de nuvens em porcentagem
    private double solarradiation;     // Radiação solar em W/m²
    private double uvindex;            // Índice UV
    private double severerisk;         // Risco severo (escala de 1 a 10)
    private String conditions;         // Condições meteorológicas (ex: "Parcialmente nublado")
    private String icon;               // Ícone que representa as condições
}