package com.water.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WaterService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public WeatherResponse searchByCity(String city) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Object responseRedis = redisTemplate.opsForValue().get(city);
        if (responseRedis instanceof String weatherDataJson) {
            return objectMapper.readValue(weatherDataJson, WeatherResponse.class);
        }
        return null;
    }

    public Object getWeather(String city) {
        String api_url = apiUrl + city + "?key=" + apiKey + "&lang=pt&unitGroup=metric";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String responseApi = restTemplate.getForObject(api_url, String.class);
            if (responseApi != null && !responseApi.isEmpty()) {

                WeatherResponse weatherResponse = objectMapper.readValue(responseApi, WeatherResponse.class);

                String weatherResponseJson = objectMapper.writeValueAsString(weatherResponse);

                redisTemplate.opsForValue().set(city, weatherResponseJson, 20, TimeUnit.MINUTES);
                return ResponseEntity.ok(weatherResponseJson);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro a buscar a cidade");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dados não encontrados para a cidade: " + city);
    }
}
