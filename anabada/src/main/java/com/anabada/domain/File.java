package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
	String file_id;
	String board_status;
	String board_no;
	String file_origin;
	String file_saved;
	String save_date;
}
