-- MySQL Workbench Forward Engineering
-- used_id = USED
-- rent_id = RENT
-- auction_id = AUCT
-- file_id = FILE
-- uDetail_id = UDET
-- rDetail_id = RDET
-- aDetail_id = ADET
-- report_id = REPO
-- inquiry_id = INQU
-- review_id = REVI
-- category_id = CATE
-- aBid_id = ABID
-- uTrade_id = UTRA
-- rTrade_id = RTRA
-- aTrade_id = ATRA
-- chat_id = CHAT
-- chatRoom_id = ROOM
-- bTemp_id = BTEM
-- cTemp_id = CTEM
-- char_id = CHAR
-- uChar_id = UCHA
-- loc_id = LOCA
-- uloc_id = ULOC
-- sloc_id = SLOC
-- wish_id = WISH
-- uBuy_id = UBUY

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema anabada
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema anabada
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `anabada` DEFAULT CHARACTER SET utf8 ;
USE `anabada` ;

DROP TABLE if exists anabada_seq;
CREATE TABLE `anabada_seq` (
  `seq_name` varchar(4) NOT NULL COMMENT '시퀀스에 사용될 명칭',
  `seq_no` int COMMENT '시퀀스 no',
  PRIMARY KEY (`seq_name`)
);

DROP TABLE if exists anabada_save_file;
CREATE TABLE `anabada_save_file` (
   `board_status` varchar(4) not null
   , `file_date` datetime default now() COMMENT '파일 저장시간'
   , `file_no` int NOT NULL COMMENT '저장 파일 no'
   , PRIMARY KEY (`board_status`)
);

-- -----------------------------------------------------
-- Table `anabada`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`user` ;

CREATE TABLE IF NOT EXISTS `anabada`.`user` (
  `user_email` VARCHAR(50) NOT NULL COMMENT '유저 이메일',
  `user_nick` VARCHAR(100) NOT NULL COMMENT '닉네임',
  `user_pwd` VARCHAR(100) NOT NULL COMMENT '비밀번호',
  `user_phone` VARCHAR(30) NOT NULL COMMENT '휴대폰 번호',
  `user_addr` VARCHAR(255) NOT NULL COMMENT '주소',
  `user_level` INT NULL DEFAULT 0 COMMENT '활동 레벨',
  `user_trade` INT NULL DEFAULT 0 COMMENT '거래 활동',
  `user_penalty` INT NULL DEFAULT 0,
  `user_account` INT NULL DEFAULT 1,
  `user_role` VARCHAR(15) NULL DEFAULT 'ROLE_USER',
  `user_nation` VARCHAR(10) NOT NULL,
  `user_date` DATETIME NULL DEFAULT now(),
  PRIMARY KEY (`user_email`),
  UNIQUE INDEX `user_nick_UNIQUE` (`user_nick` ASC) VISIBLE,
  UNIQUE INDEX `user_phone_UNIQUE` (`user_phone` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`character`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`character` ;

CREATE TABLE IF NOT EXISTS `anabada`.`character` (
  `char_id` VARCHAR(20) NOT NULL,
  `char_name` VARCHAR(45) NOT NULL,
  `char_date` DATETIME NULL DEFAULT now(),
  `char_grade` VARCHAR(5) NOT NULL,
  `char_one` VARCHAR(100) NOT NULL,
  `char_two` VARCHAR(100) NOT NULL,
  `char_three` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`char_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`user_character`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`user_character` ;

CREATE TABLE IF NOT EXISTS `anabada`.`user_character` (
  `uChar_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `character_char_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`uChar_id`),
  INDEX `fk_user_character_user_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_user_character_character1_idx` (`character_char_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_character_user`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_character_character1`
    FOREIGN KEY (`character_char_id`)
    REFERENCES `anabada`.`character` (`char_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`char_temp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`char_temp` ;

CREATE TABLE IF NOT EXISTS `anabada`.`char_temp` (
  `cTemp_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `cTemp_name` VARCHAR(45) NOT NULL,
  `cTemp_date` DATETIME NULL DEFAULT now(),
  `cTemp_grade` VARCHAR(5) NOT NULL,
  `cTemp_one` VARCHAR(100) NOT NULL,
  `cTemp_two` VARCHAR(100) NOT NULL,
  `cTemp_three` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`cTemp_id`),
  INDEX `fk_char_temp_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_char_temp_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`egg`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`egg` ;

CREATE TABLE IF NOT EXISTS `anabada`.`egg` (
  `egg_id` VARCHAR(20) NOT NULL,
  `egg_file` VARCHAR(100) NULL,
  PRIMARY KEY (`egg_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`inquiry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`inquiry` ;

CREATE TABLE IF NOT EXISTS `anabada`.`inquiry` (
  `inq_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `inq_category` VARCHAR(20) NOT NULL,
  `inq_title` VARCHAR(100) NOT NULL,
  `inq_content` VARCHAR(2000) NOT NULL,
  `inq_answer` VARCHAR(2000) NULL,
  `inq_status` VARCHAR(20) NULL DEFAULT '접수 완료',
  PRIMARY KEY (`inq_id`),
  INDEX `fk_inquiry_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_inquiry_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`report` ;

CREATE TABLE IF NOT EXISTS `anabada`.`report` (
  `report_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `report_reported` VARCHAR(45) NOT NULL,
  `report_category` VARCHAR(45) NOT NULL,
  `report_url` VARCHAR(45) NULL,
  `report_comment` VARCHAR(45) NOT NULL,
  `report_answer` VARCHAR(45) NULL,
  `report_status` VARCHAR(45) NULL DEFAULT '접수 완료',
  PRIMARY KEY (`report_id`),
  INDEX `fk_report_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_report_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`file` ;

CREATE TABLE IF NOT EXISTS `anabada`.`file` (
  `file_id` VARCHAR(20) NOT NULL,
  `board_status` VARCHAR(45) NULL,
  `board_no` VARCHAR(20) NULL,
  `file_origin` VARCHAR(100) NULL,
  `file_saved` VARCHAR(100) NULL,
  PRIMARY KEY (`file_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`chatRoom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`chatRoom` ;

CREATE TABLE IF NOT EXISTS `anabada`.`chatRoom` (
  `chatRoom_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `chatRoom_date` DATETIME NULL DEFAULT now(),
  PRIMARY KEY (`chatRoom_id`),
  INDEX `fk_chatRoom_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_chatRoom_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`chat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`chat` ;

CREATE TABLE IF NOT EXISTS `anabada`.`chat` (
  `chat_id` VARCHAR(20) NOT NULL,
  `chatRoom_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `chat_contents` VARCHAR(255) NOT NULL,
  `chat_date` DATETIME NULL DEFAULT now(),
  PRIMARY KEY (`chat_id`),
  INDEX `fk_chat_chatRoom1_idx` (`chatRoom_id` ASC) VISIBLE,
  INDEX `fk_chat_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_chat_chatRoom1`
    FOREIGN KEY (`chatRoom_id`)
    REFERENCES `anabada`.`chatRoom` (`chatRoom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chat_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`category` ;

CREATE TABLE IF NOT EXISTS `anabada`.`category` (
  `category_id` VARCHAR(20) NOT NULL,
  `category_main` VARCHAR(45) NOT NULL,
  `category_mid` VARCHAR(45) NULL,
  `category_sub` VARCHAR(45) NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`used_buy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`used_buy` ;

CREATE TABLE IF NOT EXISTS `anabada`.`used_buy` (
  `uBuy_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `category_id` VARCHAR(20) NOT NULL,
  `uBuy_title` VARCHAR(100) NOT NULL,
  `uBuy_content` VARCHAR(2000) NOT NULL,
  `uBuy_status` VARCHAR(45) NULL DEFAULT '게시',
  PRIMARY KEY (`uBuy_id`),
  INDEX `fk_used_buy_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_used_buy_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_used_buy_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_buy_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `anabada`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`location` ;

CREATE TABLE IF NOT EXISTS `anabada`.`location` (
  `loc_id` VARCHAR(20) NOT NULL,
  `loc_name` VARCHAR(45) NOT NULL,
  `loc_lat` DOUBLE NOT NULL,
  `loc_lon` DOUBLE NOT NULL,
  PRIMARY KEY (`loc_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`user_location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`user_location` ;

CREATE TABLE IF NOT EXISTS `anabada`.`user_location` (
  `uloc_id` VARCHAR(20) NOT NULL,
  `loc_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`uloc_id`),
  INDEX `fk_user_location_location1_idx` (`loc_id` ASC) VISIBLE,
  INDEX `fk_user_location_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_user_location_location1`
    FOREIGN KEY (`loc_id`)
    REFERENCES `anabada`.`location` (`loc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_location_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`sale_location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`sale_location` ;

CREATE TABLE IF NOT EXISTS `anabada`.`sale_location` (
  `sloc_id` VARCHAR(20) NOT NULL,
  `loc_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`sloc_id`),
  INDEX `fk_sale_location_location1_idx` (`loc_id` ASC) VISIBLE,
  INDEX `fk_sale_location_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_sale_location_location1`
    FOREIGN KEY (`loc_id`)
    REFERENCES `anabada`.`location` (`loc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sale_location_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`used`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`used` ;

CREATE TABLE IF NOT EXISTS `anabada`.`used` (
  `used_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `category_id` VARCHAR(20) NOT NULL,
  `used_title` VARCHAR(100) NOT NULL,
  `used_date` DATETIME NULL DEFAULT now(),
  `used_price` INT NOT NULL,
  `used_content` VARCHAR(2000) NOT NULL,
  `used_quality` VARCHAR(45) NOT NULL,
  `used_status` VARCHAR(45) NULL DEFAULT '게시',
  `uloc_id` VARCHAR(20) NOT NULL,
  `sloc_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`used_id`),
  INDEX `fk_used_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_used_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_used_user_location1_idx` (`uloc_id` ASC) VISIBLE,
  INDEX `fk_used_sale_location1_idx` (`sloc_id` ASC) VISIBLE,
  CONSTRAINT `fk_used_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `anabada`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_user_location1`
    FOREIGN KEY (`uloc_id`)
    REFERENCES `anabada`.`user_location` (`uloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_sale_location1`
    FOREIGN KEY (`sloc_id`)
    REFERENCES `anabada`.`sale_location` (`sloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`rental`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`rental` ;

CREATE TABLE IF NOT EXISTS `anabada`.`rental` (
  `rental_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `category_id` VARCHAR(20) NOT NULL,
  `rental_title` VARCHAR(100) NOT NULL,
  `rental_date` DATETIME NULL DEFAULT now(),
  `rental_price` INT NOT NULL,
  `rental_content` VARCHAR(2000) NOT NULL,
  `rental_quality` VARCHAR(45) NOT NULL,
  `rental_sDate` DATETIME NOT NULL,
  `retal_eDate` DATETIME NOT NULL,
  `rental_status` VARCHAR(45) NULL DEFAULT '게시',
  `uloc_id` VARCHAR(20) NOT NULL,
  `sloc_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`rental_id`),
  INDEX `fk_rental_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_rental_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_rental_user_location1_idx` (`uloc_id` ASC) VISIBLE,
  INDEX `fk_rental_sale_location1_idx` (`sloc_id` ASC) VISIBLE,
  CONSTRAINT `fk_rental_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `anabada`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_user_location1`
    FOREIGN KEY (`uloc_id`)
    REFERENCES `anabada`.`user_location` (`uloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_sale_location1`
    FOREIGN KEY (`sloc_id`)
    REFERENCES `anabada`.`sale_location` (`sloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`auction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`auction` ;

CREATE TABLE IF NOT EXISTS `anabada`.`auction` (
  `auction_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `category_id` VARCHAR(20) NOT NULL,
  `auction_title` VARCHAR(100) NOT NULL,
  `auction_date` DATETIME NULL DEFAULT now(),
  `auction_finish` DATETIME NOT NULL,
  `auction_price` INT NOT NULL,
  `auction_content` VARCHAR(2000) NOT NULL,
  `auction_quality` VARCHAR(45) NOT NULL,
  `auction_status` VARCHAR(45) NULL DEFAULT '게시',
  `uloc_id` VARCHAR(20) NOT NULL,
  `sloc_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`auction_id`),
  INDEX `fk_auction_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_auction_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_auction_user_location1_idx` (`uloc_id` ASC) VISIBLE,
  INDEX `fk_auction_sale_location1_idx` (`sloc_id` ASC) VISIBLE,
  CONSTRAINT `fk_auction_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `anabada`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_user_location1`
    FOREIGN KEY (`uloc_id`)
    REFERENCES `anabada`.`user_location` (`uloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_sale_location1`
    FOREIGN KEY (`sloc_id`)
    REFERENCES `anabada`.`sale_location` (`sloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`review` ;

CREATE TABLE IF NOT EXISTS `anabada`.`review` (
  `review_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `review_person` VARCHAR(50) NOT NULL,
  `board_status` VARCHAR(45) NOT NULL,
  `board_no` VARCHAR(20) NOT NULL,
  `review_moment` DATETIME NULL DEFAULT now(),
  `review_star` INT NULL,
  `review_comment` VARCHAR(2000) NULL,
  PRIMARY KEY (`review_id`),
  INDEX `fk_review_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_review_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`board_temp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`board_temp` ;

CREATE TABLE IF NOT EXISTS `anabada`.`board_temp` (
  `bTemp_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NULL,
  `category_id` VARCHAR(20) NOT NULL,
  `bTemp_title` VARCHAR(100) NULL,
  `bTemp_date` DATETIME NULL DEFAULT now(),
  `bTemp_price` INT NULL,
  `bTemp_content` VARCHAR(2000) NULL,
  `bTemp_quality` VARCHAR(45) NULL,
  `bTemp_sDate` DATETIME NULL,
  `bTemp_eDate` DATETIME NULL,
  `bTemp_finish` DATETIME NULL,
  `uloc_id` VARCHAR(20) NULL,
  `sloc_id` VARCHAR(20) NULL,
  PRIMARY KEY (`bTemp_id`),
  INDEX `fk_board_temp_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_board_temp_user_location1_idx` (`uloc_id` ASC) VISIBLE,
  INDEX `fk_board_temp_sale_location1_idx` (`sloc_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_temp_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
	CONSTRAINT `fk_board_temp_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `anabada`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_temp_user_location1`
    FOREIGN KEY (`uloc_id`)
    REFERENCES `anabada`.`user_location` (`uloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_temp_sale_location1`
    FOREIGN KEY (`sloc_id`)
    REFERENCES `anabada`.`sale_location` (`sloc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`wish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`wish` ;

CREATE TABLE IF NOT EXISTS `anabada`.`wish` (
  `wish_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `board_status` VARCHAR(45) NOT NULL,
  `board_no` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`wish_id`),
  INDEX `fk_wish_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_wish_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`used_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`used_detail` ;

CREATE TABLE IF NOT EXISTS `anabada`.`used_detail` (
  `uDetail_id` VARCHAR(20) NOT NULL,
  `used_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `uDetail_method` VARCHAR(20) NOT NULL,
  `uDetail_addr` VARCHAR(255) NOT NULL,
  `chat_id` VARCHAR(20) NOT NULL,
  `uDetail_price` INT NOT NULL,
  `uDetail_status` VARCHAR(45) NULL DEFAULT '거래 전',
  PRIMARY KEY (`uDetail_id`),
  INDEX `fk_used_detail_used1_idx` (`used_id` ASC) VISIBLE,
  INDEX `fk_used_detail_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_used_detail_chat1_idx` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_used_detail_used1`
    FOREIGN KEY (`used_id`)
    REFERENCES `anabada`.`used` (`used_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_detail_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_used_detail_chat1`
    FOREIGN KEY (`chat_id`)
    REFERENCES `anabada`.`chat` (`chat_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`rental_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`rental_detail` ;

CREATE TABLE IF NOT EXISTS `anabada`.`rental_detail` (
  `rDetail_id` VARCHAR(20) NOT NULL,
  `rental_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `chat_id` VARCHAR(20) NOT NULL,
  `rDetail_addr` VARCHAR(255) NOT NULL,
  `rDetail_price` INT NOT NULL,
  `rDetail_status` VARCHAR(45) NULL DEFAULT '거래 전',
  `rDetail_sDate` DATETIME NOT NULL,
  `rDetail_eDate` DATETIME NOT NULL,
  PRIMARY KEY (`rDetail_id`),
  INDEX `fk_rental_detail_rental1_idx` (`rental_id` ASC) VISIBLE,
  INDEX `fk_rental_detail_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_rental_detail_chat1_idx` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_rental_detail_rental1`
    FOREIGN KEY (`rental_id`)
    REFERENCES `anabada`.`rental` (`rental_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_detail_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_detail_chat1`
    FOREIGN KEY (`chat_id`)
    REFERENCES `anabada`.`chat` (`chat_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`auction_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`auction_detail` ;

CREATE TABLE IF NOT EXISTS `anabada`.`auction_detail` (
  `aDetail_id` VARCHAR(20) NOT NULL,
  `auction_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `chat_id` VARCHAR(20) NOT NULL,
  `aDetail_addr` VARCHAR(255) NOT NULL,
  `aDetail_price` INT NOT NULL,
  `aDetail_status` VARCHAR(45) NULL DEFAULT '거래 전',
  PRIMARY KEY (`aDetail_id`),
  INDEX `fk_auction_detail_auction1_idx` (`auction_id` ASC) VISIBLE,
  INDEX `fk_auction_detail_user1_idx` (`user_email` ASC) VISIBLE,
  INDEX `fk_auction_detail_chat1_idx` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_auction_detail_auction1`
    FOREIGN KEY (`auction_id`)
    REFERENCES `anabada`.`auction` (`auction_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_detail_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_detail_chat1`
    FOREIGN KEY (`chat_id`)
    REFERENCES `anabada`.`chat` (`chat_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`auction_bid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`auction_bid` ;

CREATE TABLE IF NOT EXISTS `anabada`.`auction_bid` (
  `aBid_id` VARCHAR(20) NOT NULL,
  `auction_id` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(50) NOT NULL,
  `bid_price` INT NOT NULL,
  `bid_date` DATETIME NULL DEFAULT now(),
  PRIMARY KEY (`aBid_id`),
  INDEX `fk_auction_bid_auction1_idx` (`auction_id` ASC) VISIBLE,
  INDEX `fk_auction_bid_user1_idx` (`user_email` ASC) VISIBLE,
  CONSTRAINT `fk_auction_bid_auction1`
    FOREIGN KEY (`auction_id`)
    REFERENCES `anabada`.`auction` (`auction_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_bid_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `anabada`.`user` (`user_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`uTrade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`uTrade` ;

CREATE TABLE IF NOT EXISTS `anabada`.`uTrade` (
  `uTrade_id` VARCHAR(20) NOT NULL,
  `used_id` VARCHAR(20) NOT NULL,
  `uDetail_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`uTrade_id`),
  INDEX `fk_uTrade_used1_idx` (`used_id` ASC) VISIBLE,
  INDEX `fk_uTrade_used_detail1_idx` (`uDetail_id` ASC) VISIBLE,
  CONSTRAINT `fk_uTrade_used1`
    FOREIGN KEY (`used_id`)
    REFERENCES `anabada`.`used` (`used_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_uTrade_used_detail1`
    FOREIGN KEY (`uDetail_id`)
    REFERENCES `anabada`.`used_detail` (`uDetail_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`rTrade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`rTrade` ;

CREATE TABLE IF NOT EXISTS `anabada`.`rTrade` (
  `rTrade_id` VARCHAR(20) NOT NULL,
  `rental_id` VARCHAR(20) NOT NULL,
  `rDetail_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`rTrade_id`),
  INDEX `fk_rTrade_rental1_idx` (`rental_id` ASC) VISIBLE,
  INDEX `fk_rTrade_rental_detail1_idx` (`rDetail_id` ASC) VISIBLE,
  CONSTRAINT `fk_rTrade_rental1`
    FOREIGN KEY (`rental_id`)
    REFERENCES `anabada`.`rental` (`rental_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rTrade_rental_detail1`
    FOREIGN KEY (`rDetail_id`)
    REFERENCES `anabada`.`rental_detail` (`rDetail_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `anabada`.`aTrade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `anabada`.`aTrade` ;

CREATE TABLE IF NOT EXISTS `anabada`.`aTrade` (
  `aTrade_id` VARCHAR(20) NOT NULL,
  `auction_id` VARCHAR(20) NOT NULL,
  `aDetail_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`aTrade_id`),
  INDEX `fk_aTrade_auction1_idx` (`auction_id` ASC) VISIBLE,
  INDEX `fk_aTrade_auction_detail1_idx` (`aDetail_id` ASC) VISIBLE,
  CONSTRAINT `fk_aTrade_auction1`
    FOREIGN KEY (`auction_id`)
    REFERENCES `anabada`.`auction` (`auction_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aTrade_auction_detail1`
    FOREIGN KEY (`aDetail_id`)
    REFERENCES `anabada`.`auction_detail` (`aDetail_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
