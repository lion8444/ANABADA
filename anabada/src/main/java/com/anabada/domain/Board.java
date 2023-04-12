package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    String board_no;
    String board_type;
    String img;
	String title;
	String seller;
    String price;
    String high_price;
    String info;
}