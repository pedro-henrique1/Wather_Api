package com.water.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.WaterResponse;
import com.water.service.RedisSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/rest")
public class HttpWater {
    @Value("${api_key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisSave redisSave;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String CACHE_NAME = "water-data";

    @Cacheable(cacheNames = CACHE_NAME, key = "#city")
    @GetMapping("/{city}")
    public String getWaterData(@PathVariable String city) throws JsonProcessingException {
        String cacheKey = CACHE_NAME + ":" + city;
        Object cachedData = redisSave.getFromCache(cacheKey);
        if (cachedData != null) {
            return ResponseEntity.ok().body(cachedData).toString();
        }
        String apiUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + apiKey + "&lang=pt&unitGroup=metric";
        try {
            String responseApi = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(responseApi);
            if (responseApi != null) {
                ResponseRedis(responseApi, cacheKey);
            } else {
                return ResponseEntity.internalServerError().body("Resposta da API está vazia ou nula.").toString();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return objectMapper.writeValueAsString(ResponseEntity.internalServerError().body(e.getResponseBodyAsString()));
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao processar a requisição", e);
        }

        return objectMapper.writeValueAsString(ResponseEntity.ok().body("Dado salvo"));
    }

    public void ResponseRedis(String response, String cacheKey) {
        try {
            WaterResponse waterResponse = objectMapper.readValue(response, WaterResponse.class);
            WaterResponse formattedData = getFormattedData(waterResponse);
            String jsonData = objectMapper.writeValueAsString(formattedData);
            redisTemplate.opsForValue().set(cacheKey, jsonData, 15, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }


    private static WaterResponse getFormattedData(WaterResponse waterResponse) {
        String address = waterResponse.getResolveAddress() != null ? waterResponse.getResolveAddress() : "Endereço não disponível";
        String description = waterResponse.getDescription() != null ? waterResponse.getDescription() : "Descrição não disponível";
        int temperature = waterResponse.getTemperature() != null ? waterResponse.getTemperature() : 0;
        int humidity = waterResponse.getHumidity() != null ? waterResponse.getHumidity() : 0;

        return new WaterResponse(address, description, temperature, humidity);
    }
}