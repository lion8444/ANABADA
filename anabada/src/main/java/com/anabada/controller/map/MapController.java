package com.anabada.controller.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anabada.domain.Location;
import com.anabada.service.map.MapService;

@Controller
public class MapController {

    @Autowired
    MapService service;

    @GetMapping("/map")
    public String mapIndex(
        @RequestParam(required = false) String loc_id
        ,Model model
    ) {
        if(loc_id == null) {
            return "map/mapPage";    
        }
        Location location = service.findLocation(loc_id);
        model.addAttribute("location", location);
        return "map/mapPage";
    }
    
}
