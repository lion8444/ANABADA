CREATE DEFINER=`root`@`localhost` FUNCTION `func_seq_10`(`seq_str` VARCHAR(4)) RETURNS varchar(10) CHARSET utf8mb3
BEGIN
    DECLARE RTN_VAL VARCHAR(10);
	
    INSERT INTO anabada_seq (seq_name, seq_no)
         values (seq_str, 1)
    ON DUPLICATE KEY UPDATE seq_no=seq_no+1;
	
    set @ret = row_count();
	
    if @ret = 0 then 
        set RTN_VAL = '0'; 
    else
		set @seq = (SELECT seq_no FROM anabada_seq WHERE seq_name LIKE seq_str || '%' ORDER BY seq_no LIMIT 1);
        SET RTN_VAL = (
			SELECT CONCAT(
				seq_str
                , LPAD(
					@seq
                    , 6
                    , '0'))); 
    end if;
	
	RETURN RTN_VAL;
END