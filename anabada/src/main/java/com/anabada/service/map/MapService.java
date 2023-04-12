package com.anabada.service.map;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anabada.domain.Board;
import com.anabada.domain.Location;

@Service
public interface MapService {

    Location findLocation(String loc_id);

    int insertLocation(Location location);

    Location findBoardLocation(String board_no);

    int updateLocation(Location location);

    Board findBoard(String board_no);

    List<Location> allLocation();

    List<Location> findBoardTypeLocation(String type);

    List<Location> findSearchBoardLocations(String str);
    
}
