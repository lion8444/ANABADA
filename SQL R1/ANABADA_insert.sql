-- -----------------------------------------------------
-- Table `anabada`.`user`
-- -----------------------------------------------------
-- DELETE FROM `anabada`.`user`;
update user
set user_account = 100000000
where user_email = 'anabada@gmail.com';
/*
INSERT INTO `anabada`.`user`
(`user_email`,
`user_name`,
`user_nick`,
`user_pwd`,
`user_phone`,
`user_post`,
`user_addr1`,
`user_addr2`,
`user_account`,
`user_nation`)
VALUES
('anabada@gmail.com',
'홍길동',
'anabada',
'1111',
'01012345678',
'06164',
'서울특별시 강남구 삼성동 159 코엑스',
'SCIT사무실',
'100000000',
'한국'
);


INSERT INTO `anabada`.`user`
(`user_email`,
`user_name`,
`user_nick`,
`user_pwd`,
`user_phone`,
`user_post`,
`user_addr1`,
`user_addr2`,
`user_role`,
`user_nation`)
VALUES
('ptsd@gmail.com',
'ptsd',
'ptsd',
'3333',
'008114865342424',
'06164',
'서울특별시 강남구 삼성동 159 코엑스',
'SCIT사무실',
'ROLE_ADMIN',
'일본'
);
*/
-- -----------------------------------------------------
-- Table `anabada`.`character`
-- -----------------------------------------------------
DELETE FROM `anabada`.`character`;

INSERT INTO `anabada`.`character`
(`char_id`,
`char_name`,
`char_grade`,
`char_one`,
`char_two`,
`char_three`)
VALUES
('CHAR0001',
'다마고치',
'S',
'one.jpg',
'two.jpg',
'three.jpg');


-- -----------------------------------------------------
-- Table `anabada`.`user_character`
-- -----------------------------------------------------

DELETE FROM `anabada`.`user_character`;

INSERT INTO `anabada`.`user_character`
(`uChar_id`,
`user_email`,
`character_char_id`)
VALUES
('UCHA0001',
'anabada@gmail.com',
'CHAR0001');


-- -----------------------------------------------------
-- Table `anabada`.`char_temp`
-- -----------------------------------------------------

DELETE FROM `anabada`.`char_temp`;

INSERT INTO `anabada`.`char_temp`
(`cTemp_id`,
`user_email`,
`cTemp_name`,
`cTemp_grade`,
`cTemp_one`,
`cTemp_two`,
`cTemp_three`)
VALUES
('CTEM0001',
'anabada@gmail.com',
'준비중',
'ST',
'oneT.jpg',
'twoT.jpg',
'threeT.jpg');



-- -----------------------------------------------------
-- Table `anabada`.`egg`
-- -----------------------------------------------------

DELETE FROM `anabada`.`egg`;

INSERT INTO `anabada`.`egg`
(`egg_id`,
`egg_file`)
VALUES
('EGGI0001',
'egg.jpg');

-- -----------------------------------------------------
-- Table `anabada`.`inquiry`
-- -----------------------------------------------------

DELETE FROM `anabada`.`inquiry`;

INSERT INTO `anabada`.`inquiry`
(`inq_id`,
`user_email`,
`inq_category`,
`inq_title`,
`inq_content`,
`inq_answer`)
VALUES
('INQU0001',
'anabada@gmail.com',
'배송문의',
'배송 도착이라고 하는데 못받음',
'배송 도착 완료라고 뜨는데 택배를 받지 못했습니다. 결제한지가 언젠데 왜 아직도 안오나요?',
'ㅈㅅ');


-- -----------------------------------------------------
-- Table `anabada`.`report`
-- -----------------------------------------------------

DELETE FROM `anabada`.`report`;

INSERT INTO `anabada`.`report`
(`report_id`,
`user_email`,
`report_reported`,
`report_category`,
`report_url`,
`report_comment`,
`report_answer`)
VALUES
('REPO0001',
'anabada@gmail.com',
'ptsd@gmail.com',
'사기 신고',
'https://www.google.com/',
'사기 당했습니다. 돈을 보내줬는데 제품을 안보내줍니다.',
'안전거래를 이용해주세요.');


-- -----------------------------------------------------
-- Table `anabada`.`file`
-- -----------------------------------------------------

DELETE FROM `anabada`.`file`;

INSERT INTO `anabada`.`file`
(`file_id`,
`board_status`,
`board_no`,
`file_origin`,
`file_saved`)
VALUES
('FILE0001',
'중고거래',
'USED0001',
'origin.jpg',
'saved.jpg');


-- -----------------------------------------------------
-- Table `anabada`.`chatRoom`
-- -----------------------------------------------------
DELETE FROM `anabada`.`chatroom`;

INSERT INTO `anabada`.`chatroom`
(`chatRoom_id`,
`user_email`)
VALUES
('ROOM0001',
'anabada@gmail.com');

-- -----------------------------------------------------
-- Table `anabada`.`chat`
-- -----------------------------------------------------

DELETE FROM `anabada`.`chat`;

INSERT INTO `anabada`.`chat`
(`chat_id`,
`chatRoom_id`,
`user_email`,
`chat_contents`)
VALUES
('CHAT0001',
'ROOM0001',
'anabada@gmail.com',
'사기꾼이신가요?');


-- -----------------------------------------------------
-- Table `anabada`.`category`
-- -----------------------------------------------------

DELETE FROM `anabada`.`category`;

INSERT INTO `anabada`.`category`
(`category_id`,
`category_main`,
`category_mid`,
`category_sub`)
VALUES
('CATE0001',
'전자기기',
'핸드폰',
'아이폰');


-- -----------------------------------------------------
-- Table `anabada`.`used_buy`
-- -----------------------------------------------------

DELETE FROM `anabada`.`used_buy`;

INSERT INTO `anabada`.`used_buy`
(`uBuy_id`,
`user_email`,
`category_id`,
`uBuy_title`,
`uBuy_content`)
VALUES
('UBUY0001',
'anabada@gmail.com',
'cate0001',
'아이폰 12pro 삼',
'아이폰 12pro 3만원에 삼');

-- -----------------------------------------------------
-- Table `anabada`.`location`
-- -----------------------------------------------------

DELETE FROM `anabada`.`location`;

INSERT INTO `anabada`.`location`
(`loc_id`,
`loc_name`,
`loc_lat`,
`loc_lon`) 	
VALUES
('LOCA0001',
'삼성 코엑스',
'37.5117',
'127.0592');

-- -----------------------------------------------------
-- Table `anabada`.`user_location`
-- -----------------------------------------------------

DELETE FROM `anabada`.`user_location`;


INSERT INTO `anabada`.`user_location`
(`uloc_id`,
`loc_id`,
`user_email`)
VALUES
('ULOC0001',
'LOCA0001',
'anabada@gmail.com');



-- -----------------------------------------------------
-- Table `anabada`.`sale_location`
-- -----------------------------------------------------

DELETE FROM `anabada`.`sale_location`;

INSERT INTO `anabada`.`sale_location`
(`sloc_id`,
`loc_id`,
`user_email`)
VALUES
('SLOC0001',
'LOCA0001',
'anabada@gmail.com');


-- -----------------------------------------------------
-- Table `anabada`.`used`
-- -----------------------------------------------------

DELETE FROM `anabada`.`used`;


INSERT INTO `anabada`.`used`
(`used_id`,
`user_email`,
`category_id`,
`used_title`,
`used_price`,
`used_content`,
`used_quality`,
`uloc_id`,
`sloc_id`)
VALUES
('USED0001',
'anabada@gmail.com',
'CATE0001',
'갤럭시 플립 팔아요',
1000000,
'갤럭시 플립 언박스 제품 팔아요. 완전 새재품입니다.',
'S',
'ULOC0001',
'SLOC0001');


-- -----------------------------------------------------
-- Table `anabada`.`rental`
-- -----------------------------------------------------

DELETE FROM `anabada`.`rental`;


INSERT INTO `anabada`.`rental`
(`rental_id`,
`user_email`,
`category_id`,
`rental_title`,
`rental_price`,
`rental_content`,
`rental_quality`,
`rental_sDate`,
`rental_eDate`,
`uloc_id`,
`sloc_id`)
VALUES
('RENT0001',
'anabada@gmail.com',
'CATE0001',
'닌텐도 스위치 렌탈',
20000,
'남동생이 여행가서 닌텐도 스위치 렌탈해드립니다. 슈퍼마리오, 포켓몬스터 게임이 있어 필요하시면 같이 빌려드립니다.',
'B',
'2023-05-01',
'2023-06-30',
'ULOC0001',
'SLOC0001');


-- -----------------------------------------------------
-- Table `anabada`.`auction`
-- -----------------------------------------------------

DELETE FROM `anabada`.`auction`;

INSERT INTO `anabada`.`auction`
(`auction_id`,
`user_email`,
`category_id`,
`auction_title`,
`auction_finish`,
`auction_price`,
`auction_content`,
`auction_quality`,
`uloc_id`,
`sloc_id`)
VALUES
('AUCT0001',
'anabada@gmail.com',
'CATE0001',
'조말론 잉글리쉬 페어 앤 프리지아 센트 써라운드™ 디퓨저',
'2023-03-25',
'80000',
'조말론 디퓨저 오픈하지 않은 제품 팝니다. 정가는 14만 4천원 입니다.',
'S',
'ULOC0001',
'SLOC0001');


-- -----------------------------------------------------
-- Table `anabada`.`review`
-- -----------------------------------------------------

DELETE FROM `anabada`.`review`;


INSERT INTO `anabada`.`review`
(`review_id`,
`user_email`,
`review_person`,
`board_status`,
`board_no`,
`review_star`,
`review_comment`)
VALUES
('REVI0001',
'anabada@gmail.com',
'ptsd@gmail.com',
'렌탈거래',
'RENT0001',
2,
'A급이라고 했는데 완전 C급이에요. 외관 상태도 안좋고 충전기 연결도 잘 안됩니다.');


-- -----------------------------------------------------
-- Table `anabada`.`board_temp`
-- -----------------------------------------------------

DELETE FROM `anabada`.`board_temp`;

INSERT INTO `anabada`.`board_temp`
(`bTemp_id`,
`user_email`,
`category_id`,
`bTemp_title`,
`bTemp_price`,
`bTemp_content`,
`bTemp_finish`,
`sloc_id`)
VALUES
('BTEM0001',
'anabada@gmail.com',
'CATE0001',
'test',
100000,
'테스트 중입니다.',
'2023-11-11',
'SLOC0001');


-- -----------------------------------------------------
-- Table `anabada`.`wish`
-- -----------------------------------------------------

DELETE FROM `anabada`.`wish`;

INSERT INTO `anabada`.`wish`
(`wish_id`,
`user_email`,
`board_status`,
`board_no`)
VALUES
('WISH0001',
'anabada@gmail.com',
'옥션거래',
'AUCT0001');


-- -----------------------------------------------------
-- Table `anabada`.`used_detail`
-- -----------------------------------------------------

DELETE FROM `anabada`.`used_detail`;


INSERT INTO `anabada`.`used_detail`
(`uDetail_id`,
`used_id`,
`user_email`,
`uDetail_person`,
`uDetail_phone`,
`uDetail_post`,
`uDetail_addr1`,
`uDetail_addr2`,
`uDetail_memo`,
`chat_id`,
`uDetail_price`)
VALUES
('UDET0001',
'USED0001',
'anabada@gmail.com',
'홈길동',
'01012345567',
'06164',
'서울특별시 강남구 삼성동 159 코엑스',
'SCIT사무실',
'내일까지 보내주세요.',
'CHAT0001',
900000);


-- -----------------------------------------------------
-- Table `anabada`.`rental_detail`
-- -----------------------------------------------------

DELETE FROM `anabada`.`rental_detail`;

INSERT INTO `anabada`.`rental_detail`
(`rDetail_id`,
`rental_id`,
`user_email`,
`chat_id`,
`rDetail_person`,
`rDetail_phone`,
`rDetail_memo`,
`rDetail_post`,
`rDetail_addr1`,
`rDetail_addr2`,
`rDetail_price`,
`rDetail_sDate`,
`rDetail_eDate`)
VALUES
('RDET0001',
'RENT0001',
'pyo9139@naver.com',
'CHAT0001',
'임꺽정',
'01034456774',
'문 열려있어요',
'06164',
'서울특별시 강남구 삼성동 159 코엑스',
'SCIT사무실',
80000,
'2023-06-01',
'2023-06-29');


-- -----------------------------------------------------
-- Table `anabada`.`auction_detail`
-- -----------------------------------------------------

DELETE FROM `anabada`.`auction_detail`;


INSERT INTO `anabada`.`auction_detail`
(`aDetail_id`,
`auction_id`,
`user_email`,
`chat_id`,
`aDetail_person`,
`aDetail_phone`,
`aDetail_post`,
`aDetail_addr1`,
`aDetail_addr2`,
`aDetail_price`)
VALUES
('ADET0001',
'AUCT0001',
'anabada@gmail.com',
'CHAT0001',
'아무개',
'01022222222',
'06164',
'서울특별시 강남구 삼성동 159 코엑스',
'SCIT사무실',
120000);



-- -----------------------------------------------------
-- Table `anabada`.`auction_bid`
-- -----------------------------------------------------

DELETE FROM `anabada`.`auction_bid`;

INSERT INTO `anabada`.`auction_bid`
(`aBid_id`,
`auction_id`,
`user_email`,
`bid_price`)
VALUES
('ABID0001',
'AUCT0001',
'anabada@gmail.com',
110000);



-- -----------------------------------------------------
-- Table `anabada`.`uTrade`
-- -----------------------------------------------------

DELETE FROM `anabada`.`utrade`;

INSERT INTO `anabada`.`utrade`
(`uTrade_id`,
`used_id`,
`uDetail_id`)
VALUES
('UTRA0002',
'USED0003',
'UDET0003');


-- -----------------------------------------------------
-- Table `anabada`.`rTrade`
-- -----------------------------------------------------

DELETE FROM `anabada`.`rtrade`;

INSERT INTO `anabada`.`rtrade`
(`rTrade_id`,
`rental_id`,
`rDetail_id`)
VALUES
('RTRA0001',
'RENT0001',
'RDET0001');


-- -----------------------------------------------------
-- Table `anabada`.`aTrade`
-- -----------------------------------------------------

DELETE FROM `anabada`.`atrade`;


INSERT INTO `anabada`.`atrade`
(`aTrade_id`,
`auction_id`,
`aDetail_id`)
VALUES
('ATRA0001',
'AUCT0001',
'ADET0001');

commit;