package com.water.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.WaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rest")
public class HttpWater {
    @Value("${api_key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private WaterResponse waterResponse;


    private static final String CACHE_NAME = "water-data";



    @Cacheable(cacheNames = CACHE_NAME, key = "#city")
    @GetMapping("/{city}")
    public String getWaterData(@PathVariable String city) throws JsonProcessingException {
        String cacheKey = CACHE_NAME + ":" + city;
        System.out.println(cacheKey);
        String cachedData = redisTemplate.opsForValue().get(cacheKey);

        System.out.println(cachedData);
        ObjectMapper objectMapper = new ObjectMapper();
        if (cachedData != null) {
            System.out.println(cachedData);
//            return cachedData;
//            WaterResponse waterResponseFromCache = objectMapper.readValue(cachedData, WaterResponse.class);
            System.out.println("Dados do cache: " + cachedData);
        }
        String apiUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + apiKey + "&lang=pt&unitGroup=metric";
        String responseApi = restTemplate.getForObject(apiUrl, String.class);
        ResponseRedis(responseApi, cacheKey);
        System.out.println(responseApi);
//        return ResponseEntity.ok(responseApi.getBody());
//        if (responseApi.getStatusCode() == HttpStatus.OK && responseApi.getBody() != null) {
//            ResponseRedis(responseApi.getBody(), cacheKey);
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return cacheKey;
    }

    public void ResponseRedis(String response, String cacheKey) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            WaterResponse waterResponse = objectMapper.readValue(response, WaterResponse.class);

            String formattedData = getFormattedData(waterResponse);

            redisTemplate.opsForValue().set(cacheKey, formattedData, 15, TimeUnit.MINUTES);

            System.out.println("Dados formatados: " + formattedData);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }


    private static String getFormattedData(WaterResponse waterResponse) {
        String address = waterResponse.getResolveAddress() != null ? waterResponse.getResolveAddress() : "Endereço não disponível";
        String description = waterResponse.getDescription() != null ? waterResponse.getDescription() : "Descrição não disponível";
        int temperature = waterResponse.getCurrentConditions().getTemperature() != null ? waterResponse.getCurrentConditions().getTemperature() : 0;
        int humidity = waterResponse.getCurrentConditions().getHumidity() != null ? waterResponse.getCurrentConditions().getHumidity() : 0;

        return "Address: " + address + " Description: " + description + " Temperature: " + temperature + " Humidity: " + humidity;
    }
}