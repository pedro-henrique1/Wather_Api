package com.water.Controller;

import com.water.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rest")
public class HttpWater {
    @Autowired
    WaterService waterService = new WaterService();

    @GetMapping("/{city}")
    public Object getWaterData(@PathVariable String city) {
        Object cachedData = waterService.searchByCity(city);

        // existe no cache
        if (cachedData != null) {
            return cachedData;
        }
        return waterService.getWeather(city);
    }
}