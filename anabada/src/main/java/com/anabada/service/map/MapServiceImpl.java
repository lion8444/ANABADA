package com.anabada.service.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.MapDAO;
import com.anabada.domain.Location;

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
    
}
