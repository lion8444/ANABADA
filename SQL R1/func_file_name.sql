CREATE DEFINER=`root`@`localhost` FUNCTION `func_file_name`(`board_str` VARCHAR(4)) RETURNS varchar(100) CHARSET utf8mb3
BEGIN
    DECLARE RTN_VAL VARCHAR(100);
	
    INSERT INTO anabada_save_file (board_status, file_date, file_no)
         values (board_str, now(), 1)
    ON DUPLICATE KEY UPDATE file_no = file_no+1;
    
    set @ret = row_count();
	
    if @ret = 0 then 
        set RTN_VAL = '0'; 
    else
		set @fname = (SELECT file_no FROM anabada_save_file WHERE board_status LIKE board_str || '%' ORDER BY file_no LIMIT 1);
        SET RTN_VAL = (SELECT CONCAT((SELECT date_format(file_date, "%y%m%d_%H%i%s_%f") FROM anabada_save_file WHERE board_status LIKE board_str || '%'), '_', LPAD(@fname, 4, '0'))); 
    end if;
	
	RETURN RTN_VAL;
END