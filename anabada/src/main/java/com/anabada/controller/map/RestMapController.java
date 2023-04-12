package com.anabada.controller.map;


import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.Board;
import com.anabada.domain.Location;
import com.anabada.service.map.MapService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
public class RestMapController {
    
    @Autowired
    MapService service;

    @PostMapping("/locations")
    public List<Location> allLocation() {
        List<Location> list = service.allLocation();
        return list;
    }
    @PostMapping("/typeLocations")
    public List<Location> typeLocatioins(String type) {
        List<Location> list = service.findBoardTypeLocation(type);
        return list;
    }
    @PostMapping("/markerData")
    public Board viewMarkerData(String board_no) {
        Board board = service.findBoard(board_no);
        log.debug("{}",board);
        return board;
    }
    @PostMapping("/search")
    public List<Location> searchKeyword(String str) {
        List<Location> list = service.findSearchBoardLocations(str);
        return list;
    }
}