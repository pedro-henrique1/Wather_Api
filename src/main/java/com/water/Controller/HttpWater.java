package com.water.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.WaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private RedisTemplate<String, String> redisTemplate;

    private WaterResponse waterResponse;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String CACHE_NAME = "water-data";

    @Cacheable(cacheNames = CACHE_NAME, key = "#city")
    @GetMapping("/{city}")
    public ResponseEntity<?> getWaterData(@PathVariable String city) throws JsonProcessingException {
        String cacheKey = CACHE_NAME + ":" + city;
        String cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData != null) {
             waterResponse = objectMapper.readValue(cachedData, WaterResponse.class);
            return new ResponseEntity<>(waterResponse, HttpStatus.OK);
        }
        String apiUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city + "?key=" + apiKey + "&lang=pt&unitGroup=metric";
        String responseApi = restTemplate.getForObject(apiUrl, String.class);
        ResponseRedis(responseApi, cacheKey);
        System.out.println(responseApi);
//        if (responseApi.getStatusCode() == HttpStatus.OK && responseApi.getBody() != null) {
//            ResponseRedis(responseApi.getBody(), cacheKey);
//        }
        return new ResponseEntity<>("Resposta salva no redis", HttpStatus.OK);
    }

    public void ResponseRedis(String response, String cacheKey) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            WaterResponse waterResponse = objectMapper.readValue(response, WaterResponse.class);

            WaterResponse formattedData = getFormattedData(waterResponse);

            redisTemplate.opsForValue().set(cacheKey, String.valueOf(formattedData), 15, TimeUnit.MINUTES);

            System.out.println("Dados formatados: " + formattedData);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }


    private static WaterResponse getFormattedData(WaterResponse waterResponse) {
        String address = waterResponse.getResolveAddress() != null ? waterResponse.getResolveAddress() : "Endereço não disponível";
        String description = waterResponse.getDescription() != null ? waterResponse.getDescription() : "Descrição não disponível";
        int temperature = waterResponse.getTemperature() != null ? waterResponse.getTemperature() : 0;
        int humidity = waterResponse.getHumidity() != null ? waterResponse.getHumidity() : 0;



        //        return "Address: " + address + " Description: " + description + " Temperature: " + temperature + " Humidity: " + humidity;
        return new WaterResponse(address,description, temperature, humidity);
    }
}