package com.anabada.service.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.MapDAO;
import com.anabada.domain.Board;
import com.anabada.domain.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapDAO dao;

    @Override
    public Location findLocation(String loc_id) {
        Location location = dao.findLocation(loc_id);
        return location;
    }

    @Override
    public int insertLocation(Location location) {
        int res = dao.insertLocation(location);
        return res;
    }

    @Override
    public Location findBoardLocation(String board_no) {
        Location location = dao.findBoardLocation(board_no);
        return location;
    }

    @Override
    public int updateLocation(Location location) {
        int res = dao.updateLocation(location);
        return res;
    }

    @Override
    public Board findBoard(String board_no) {
        Board board = dao.findboard(board_no);
        log.debug(board_no);
        return board;
    }

    @Override
    public List<Location> allLocation() {
        List<Location> list = dao.allLocation();
        return list;
    }

    @Override
    public List<Location> findBoardTypeLocation(String type) {
        List<Location> location = dao.findBoardTypeLocation(type);
        return location;
    }

    @Override
    public List<Location> findSearchBoardLocations(String str) {
        List<String> board_no = dao.findSearchBoard(str);
        log.debug("@MapServiceImpl List board_no : {}", board_no);
        List<Location> list = dao.findBoardLocations(board_no);
        log.debug("@MapServiceImpl List Location : {}", list);
        return list;
    }
}
