SELECT `atrade`.`aTrade_id`,
    `atrade`.`auction_id`,
    `atrade`.`aDetail_id`
FROM `anabada`.`atrade`;

SELECT `auction`.`auction_id`,
    `auction`.`user_email`,
    `auction`.`category_id`,
    `auction`.`auction_title`,
    `auction`.`auction_date`,
    `auction`.`auction_finish`,
    `auction`.`auction_price`,
    `auction`.`auction_content`,
    `auction`.`auction_quality`,
    `auction`.`auction_status`,
    `auction`.`uloc_id`,
    `auction`.`sloc_id`
FROM `anabada`.`auction`;

SELECT `auction_bid`.`aBid_id`,
    `auction_bid`.`auction_id`,
    `auction_bid`.`user_email`,
    `auction_bid`.`bid_price`,
    `auction_bid`.`bid_date`
FROM `anabada`.`auction_bid`;

SELECT `auction_detail`.`aDetail_id`,
    `auction_detail`.`auction_id`,
    `auction_detail`.`user_email`,
    `auction_detail`.`chat_id`,
    `auction_detail`.`aDetail_person`,
    `auction_detail`.`aDetail_phone`,    
	`auction_detail`.`aDetail_post`,
	`auction_detail`.`aDetail_addr1`,
    `auction_detail`.`aDetail_addr2`,
    `auction_detail`.`aDetail_price`,
    `auction_detail`.`aDetail_status`,
	`auction_detail`.`aDetail_Date`
 
FROM `anabada`.`auction_detail`;

SELECT `board_temp`.`bTemp_id`,
    `board_temp`.`user_email`,
    `board_temp`.`category_id`,
    `board_temp`.`bTemp_title`,
    `board_temp`.`bTemp_date`,
    `board_temp`.`bTemp_price`,
    `board_temp`.`bTemp_content`,
    `board_temp`.`bTemp_quality`,
    `board_temp`.`bTemp_sDate`,
    `board_temp`.`bTemp_eDate`,
    `board_temp`.`bTemp_finish`,
    `board_temp`.`uloc_id`,
    `board_temp`.`sloc_id`
FROM `anabada`.`board_temp`;

SELECT `category`.`category_id`,
    `category`.`category_main`,
    `category`.`category_mid`,
    `category`.`category_sub`
FROM `anabada`.`category`;

SELECT `char_temp`.`cTemp_id`,
    `char_temp`.`user_email`,
    `char_temp`.`cTemp_name`,
    `char_temp`.`cTemp_date`,
    `char_temp`.`cTemp_grade`,
    `char_temp`.`cTemp_one`,
    `char_temp`.`cTemp_two`,
    `char_temp`.`cTemp_three`
FROM `anabada`.`char_temp`;

SELECT `character`.`char_id`,
    `character`.`char_name`,
    `character`.`char_date`,
    `character`.`char_grade`,
    `character`.`char_one`,
    `character`.`char_two`,
    `character`.`char_three`
FROM `anabada`.`character`;

SELECT `chat`.`chat_id`,
    `chat`.`chatRoom_id`,
    `chat`.`user_email`,
    `chat`.`chat_contents`,
    `chat`.`chat_date`
FROM `anabada`.`chat`;

SELECT `chatroom`.`chatRoom_id`,
    `chatroom`.`user_email`,
    `chatroom`.`chatRoom_date`
FROM `anabada`.`chatroom`;

SELECT `egg`.`egg_id`,
    `egg`.`egg_file`
FROM `anabada`.`egg`;

SELECT *
FROM `anabada`.`file`;

SELECT `inquiry`.`inq_id`,
    `inquiry`.`user_email`,
    `inquiry`.`inq_category`,
    `inquiry`.`inq_title`,
    `inquiry`.`inq_content`,
    `inquiry`.`inq_answer`,
    `inquiry`.`inq_status`
FROM `anabada`.`inquiry`;

SELECT `location`.`loc_id`,
    `location`.`loc_name`,
    `location`.`loc_lat`,
    `location`.`loc_lon`
FROM `anabada`.`location`;

SELECT `rental`.`rental_id`,
    `rental`.`user_email`,
    `rental`.`category_id`,
    `rental`.`rental_title`,
    `rental`.`rental_date`,
    `rental`.`rental_price`,
    `rental`.`rental_content`,
    `rental`.`rental_quality`,
    `rental`.`rental_sDate`,
    `rental`.`rental_eDate`,
    `rental`.`rental_status`,
    `rental`.`uloc_id`,
    `rental`.`sloc_id`
FROM `anabada`.`rental`;

SELECT `rental_detail`.`rDetail_id`,
    `rental_detail`.`rental_id`,
    `rental_detail`.`user_email`,
    `rental_detail`.`chat_id`,
	`rental_detail`.`rDetail_post`,
	`rental_detail`.`rDetail_addr1`,
    `rental_detail`.`rDetail_addr2`,
    `rental_detail`.`rDetail_price`,
    `rental_detail`.`rDetail_status`,
    `rental_detail`.`rDetail_sDate`,
	`rental_detail`.`rDetail_eDate`,
	`rental_detail`.`rDetail_Date`
FROM `anabada`.`rental_detail`;

SELECT `report`.`report_id`,
    `report`.`user_email`,
    `report`.`report_reported`,
    `report`.`report_category`,
    `report`.`report_url`,
    `report`.`report_comment`,
    `report`.`report_answer`,
    `report`.`report_status`
FROM `anabada`.`report`;

SELECT `review`.`review_id`,
    `review`.`user_email`,
    `review`.`review_person`,
    `review`.`board_status`,
    `review`.`board_no`,
    `review`.`review_moment`,
    `review`.`review_star`,
    `review`.`review_comment`
FROM `anabada`.`review`;

SELECT `rtrade`.`rTrade_id`,
    `rtrade`.`rental_id`,
    `rtrade`.`rDetail_id`
FROM `anabada`.`rtrade`;

SELECT `sale_location`.`sloc_id`,
    `sale_location`.`loc_id`,
    `sale_location`.`user_email`
FROM `anabada`.`sale_location`;

SELECT `used`.`used_id`,
    `used`.`user_email`,
    `used`.`category_id`,
    `used`.`used_title`,
    `used`.`used_date`,
    `used`.`used_price`,
    `used`.`used_content`,
    `used`.`used_quality`,
    `used`.`used_status`,
    `used`.`uloc_id`,
    `used`.`sloc_id`
FROM `anabada`.`used`;

SELECT `used_buy`.`uBuy_id`,
    `used_buy`.`user_email`,
    `used_buy`.`category_id`,
    `used_buy`.`uBuy_title`,
    `used_buy`.`uBuy_content`,
    `used_buy`.`uBuy_status`
FROM `anabada`.`used_buy`;

SELECT `used_detail`.`uDetail_id`,
    `used_detail`.`used_id`,
    `used_detail`.`user_email`,
    `used_detail`.`uDetail_method`,
	`used_detail`.`uDetail_post`,
	`used_detail`.`uDetail_addr1`,
    `used_detail`.`uDetail_addr2`,
	`used_detail`.`uDetail_memo`,
    `used_detail`.`chat_id`,
    `used_detail`.`uDetail_price`,
    `used_detail`.`uDetail_status`,
	`used_detail`.`uDetail_Date`
FROM `anabada`.`used_detail`;

SELECT *
FROM `anabada`.`user`;

SELECT `user_character`.`uChar_id`,
    `user_character`.`user_email`,
    `user_character`.`character_char_id`
FROM `anabada`.`user_character`;

SELECT `user_location`.`uloc_id`,
    `user_location`.`loc_id`,
    `user_location`.`user_email`
FROM `anabada`.`user_location`;

SELECT `utrade`.`uTrade_id`,
    `utrade`.`used_id`,
    `utrade`.`uDetail_id`
FROM `anabada`.`utrade`;

SELECT `wish`.`wish_id`,
    `wish`.`user_email`,
    `wish`.`board_status`,
    `wish`.`board_no`
FROM `anabada`.`wish`;

SET GLOBAL log_bin_trust_function_creators = 1;

SELECT used_id, used_title, user_email, used_date, used_status FROM `anabada`.`used` AS used
UNION
SELECT rental_id, rental_title, user_email, rental_date, rental_status FROM `anabada`.`rental` AS rental
UNION
SELECT auction_id, auction_title, user_email, auction_date, auction_status FROM  `anabada`.`auction` AS auction;
