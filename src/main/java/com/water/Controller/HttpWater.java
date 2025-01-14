package com.water.Controller;

import com.water.dto.WeatherResponse;
import com.water.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@RestController
@RequestMapping("/rest")
public class HttpWater {


    @Autowired
    WaterService waterService = new WaterService();

    @GetMapping("/{city}")
    public Object getWaterData(@PathVariable String city) throws IOException {

        WeatherResponse cachedData = waterService.searchByCity(city);

        // existe no cache
        if (cachedData != null) {
            return cachedData;
        }
        return waterService.getWeather(city);
    }

    @GetMapping("/health")
    public ResponseEntity<String> getHealthData()  {
        return  ResponseEntity.ok("Healthy");
    }
}