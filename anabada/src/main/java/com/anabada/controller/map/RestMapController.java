package com.anabada.controller.map;


import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.Location;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
public class RestMapController {
    
    @PostMapping("/getLatLng")
    public void insertLocation(Location location) {
        log.debug("@RestMapController insertLocation : {}", location);
    }
    
}