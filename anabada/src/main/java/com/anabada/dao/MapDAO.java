package com.anabada.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Board;
import com.anabada.domain.Location;

@Mapper
public interface MapDAO {

    Location findLocation(String loc_id);

    int insertLocation(Location location);
    
    Location findBoardLocation(String board_no);

    int updateLocation(Location location);

    Board findboard(String board_no);

    List<Location> allLocation();

    List<Location> findBoardTypeLocation(String type);

    List<String> findSearchBoard(String str);

    List<Location> findBoardLocations(List<String> list);
}
