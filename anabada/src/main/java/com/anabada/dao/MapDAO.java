package com.anabada.dao;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Location;

@Mapper
public interface MapDAO {

    Location findLocation(String loc_id);

    int insertLocation(Location location);
    
    Location findBoardLocation(String board_no);

    int updateLocation(Location location);
}
