package com.anabada.controller.map;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anabada.domain.Board;
import com.anabada.domain.File;
import com.anabada.domain.Location;
import com.anabada.service.map.MapService;
import com.anabada.service.used.UsedService;

@Controller
public class MapController {

    @Autowired
    MapService mservice;

    @Autowired
    UsedService fservice;

    @Value("${spring.servlet.multipart.location}")
	String uploadPath;

    @GetMapping("/map")
    public String mapIndex() {
        return "map/mapPage";
    }
    @GetMapping("/roadMap")
    public String mapViewRoad(
        @RequestParam(required = false) String loc_id
        ,Model model
    ) {
        Location location = mservice.findLocation(loc_id);
        model.addAttribute("location", location);
        return "map/roadMap";
    }

    @GetMapping({"/imgShow"})
    public String imgShow(HttpServletResponse response, String board_no) {
		ArrayList<File> fileList = fservice.fileList();

		int index = 0;

		for (int i = 0; i < fileList.size(); ++i) {
			if (board_no.equals(fileList.get(i).getBoard_no())) {
				index = i;
			}
		}

		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;
		ServletOutputStream out = null;

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		} catch (Exception e) {
			return "redirect:/";

		}
		return "redirect:/";
	}
}
