package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class location {
	String loc_id;
	String loc_name;
	double loc_lat;
	double loc_lon;
}
